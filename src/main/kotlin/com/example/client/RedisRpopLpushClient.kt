package com.example.client

import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.args.ListDirection.LEFT
import redis.clients.jedis.args.ListDirection.RIGHT
import java.io.Closeable
import java.util.concurrent.atomic.AtomicLong

class RedisRpopLpushClient(private val poolSize: Int) : Closeable {
    private val poolConfig = JedisPoolConfig().also {
        it.minIdle = poolSize
        it.maxTotal = poolSize
    }
    private val publishPool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val subscribePool = JedisPool(poolConfig, "redis://localhost:6379/0")
    private val clientId = number++
    private val nextChannel = (0 until poolSize)
        .map { "rpush-lpop-$clientId-$it" }
        .map(String::toByteArray)
        .toCircularIterator()
    private val workingChannel = "rpush-lpop-working".toByteArray()

    private val pushes = AtomicLong(0)
    private val pops = AtomicLong(0)

    fun getPushesCount() = pushes.get()

    fun getPopsCount() = pops.get()

    fun push(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.rpush(nextChannel(), message)
            pushes.incrementAndGet()
            print('+')
        }
    }

    fun pop() {
        publishPool.resource.use { jedis ->
            val bytes = jedis.lmove(nextChannel(), workingChannel, LEFT, RIGHT)
            if (bytes != null) {
                pops.incrementAndGet()
                print('-')
            }
            val count = jedis.lrem(workingChannel, 1, bytes)
            for (i in 0 until count) {
                print('\'')
            }
        }
    }

    override fun close() {
        subscribePool.close()
        publishPool.close()
    }

    companion object {
        private var number = 0
    }
}
