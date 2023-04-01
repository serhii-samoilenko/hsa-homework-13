package com.example.client

import com.example.MessageConsumer
import redis.clients.jedis.BinaryJedisPubSub
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.io.Closeable
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class RedisPubSubClient(private val poolSize: Int) : Closeable {
    private val listeners = mutableListOf<Listener>()
    private val poolConfig = JedisPoolConfig().also {
        it.minIdle = poolSize
        it.maxTotal = poolSize
    }
    private val chunkSize = 128
    private val bytesPublished = AtomicLong(0)
    private val bytesConsumed = AtomicLong(0)

    private val publishPool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val subscribePool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val nextChannel = (0 until poolSize)
        .map { "channel-$it" }
        .map(String::toByteArray)
        .toCircularIterator()

    fun publish(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.publish(nextChannel(), message)
            if (bytesPublished.addAndGet(message.size.toLong()) % chunkSize == 0L) {
                print('+')
            }
        }
    }

    fun subscribe(messageConsumer: MessageConsumer) {
        (0 until poolSize).forEach { _ ->
            val listener = Listener(messageConsumer).also { listeners.add(it) }
            // Run in a separate thread to avoid blocking the main thread:
            Thread { subscribePool.resource.subscribe(listener, nextChannel()) }.start()
        }
        // There is no other way to wait for the subscription to be established:
        Thread.sleep(1000)
    }

    override fun close() {
        listeners.forEach { it.unsubscribe() }
        listeners.clear()
        subscribePool.close()
        publishPool.close()
    }

    inner class Listener(private val messageConsumer: MessageConsumer) : BinaryJedisPubSub() {
        override fun onMessage(channel: ByteArray, message: ByteArray) {
            messageConsumer.consumeMessage(message)
            if (bytesConsumed.addAndGet(message.size.toLong()) % chunkSize == 0L) {
                print('-')
            }
        }
    }
}

private fun <E> List<E>.toCircularIterator(): () -> E {
    val index = AtomicInteger(0)
    return {
        val currentIndex = index.getAndIncrement() % this.size
        val result = this[currentIndex]
        index.compareAndSet(this.size, 0)
        result
    }
}
