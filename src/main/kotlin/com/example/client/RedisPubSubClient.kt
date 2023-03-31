package com.example.client

import com.example.MessageConsumer
import redis.clients.jedis.BinaryJedisPubSub
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

class RedisPubSubClient(private val poolSize: Int, channelName: String) {
    private val channel = channelName.toByteArray()
    private val listeners = mutableListOf<Listener>()
    private val poolConfig = JedisPoolConfig().also {
        it.minIdle = poolSize
        it.maxTotal = poolSize
    }
    private val publishPool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val subscribePool = JedisPool(poolConfig, "redis://localhost:6379/0")

    fun open() {
        publishPool.resource.use { jedis ->
            jedis.ping()
        }
        subscribePool.resource.use { jedis ->
            jedis.ping()
        }
    }

    fun publish(message: ByteArray) {
        publishPool.resource.use { jedis ->
            print('-')
            jedis.publish(channel, message)
        }
    }

    fun subscribe(messageConsumer: MessageConsumer) {
        (1 until poolSize).forEach { _ ->
            val listener = Listener(messageConsumer).also { listeners.add(it) }
            // Run in a separate thread to avoid blocking the main thread:
            Thread { subscribePool.resource.subscribe(listener, channel) }.start()
        }
    }

    fun close() {
        listeners.onEach { it.unsubscribe() }.clear()
        subscribePool.close()
        publishPool.close()
    }

    class Listener(private val messageConsumer: MessageConsumer) : BinaryJedisPubSub() {
        override fun onMessage(channel: ByteArray, message: ByteArray) {
            messageConsumer.consumeMessage(message)
        }
    }
}
