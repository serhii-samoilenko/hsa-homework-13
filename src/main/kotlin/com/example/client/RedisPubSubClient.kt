package com.example.client

import com.example.util.MetricsCounter
import redis.clients.jedis.BinaryJedisPubSub
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.io.Closeable

class RedisPubSubClient(private val poolSize: Int, private val counter: MetricsCounter) : Closeable {
    private val listeners = mutableListOf<Listener>()
    private val poolConfig = JedisPoolConfig().also {
        it.minIdle = poolSize
        it.maxTotal = poolSize
    }
    private val publishPool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val subscribePool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val clientId = number++
    private val nextChannel = (0 until poolSize)
        .map { "pub-sub-$clientId-$it" }
        .map(String::toByteArray)
        .toCircularIterator()

    fun publish(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.publish(nextChannel(), message)
            counter.recordPush()
        }
    }

    fun subscribe() {
        (0 until poolSize).forEach { _ ->
            val listener = Listener().also { listeners.add(it) }
            // Run in a separate thread to avoid blocking the main thread:
            Thread { subscribePool.resource.subscribe(listener, nextChannel()) }.start()
        }
        // There is no other way to wait for the subscription to be established:
        Thread.sleep(1000)
    }

    override fun close() {
        listeners.forEach {
            try {
                it.unsubscribe()
            } catch (e: Exception) {
                println("Failed to unsubscribe: ${e.message}")
            }
        }
        listeners.clear()
        subscribePool.close()
        publishPool.close()
    }

    inner class Listener() : BinaryJedisPubSub() {
        override fun onMessage(channel: ByteArray, message: ByteArray) {
            counter.recordPull()
        }
    }

    companion object {
        private var number = 0
    }
}
