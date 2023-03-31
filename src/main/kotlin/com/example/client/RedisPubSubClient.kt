package com.example.client

import com.example.MessageConsumer
import redis.clients.jedis.BinaryJedisPubSub
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.io.Closeable

class RedisPubSubClient(private val poolSize: Int) : Closeable {
    private val listeners = mutableListOf<Listener>()
    private val poolConfig = JedisPoolConfig().also {
        it.minIdle = poolSize
        it.maxTotal = poolSize
    }
    private val publishPool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val subscribePool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val nextChannel = (1 until poolSize)
        .map { "channel-$it" }
        .map(String::toByteArray)
        .toCircularIterator()

    fun publish(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.publish(nextChannel(), message)
        }
    }

    fun subscribe(messageConsumer: MessageConsumer) {
        (1 until poolSize).forEach { _ ->
            val listener = Listener(messageConsumer).also { listeners.add(it) }
            // Run in a separate thread to avoid blocking the main thread:
            Thread { subscribePool.resource.subscribe(listener, nextChannel()) }.start()
        }
    }

    override fun close() {
        listeners.onEach { it.unsubscribe() }.clear()
        subscribePool.close()
        publishPool.close()
        Thread.sleep(1000)
    }

    class Listener(private val messageConsumer: MessageConsumer) : BinaryJedisPubSub() {
        override fun onMessage(channel: ByteArray, message: ByteArray) {
            messageConsumer.consumeMessage(message)
        }
    }
}

private fun <E> List<E>.toCircularIterator(): () -> E {
    var index = 0
    return {
        val result = this[index]
        index = (index + 1) % this.size
        result
    }
}
