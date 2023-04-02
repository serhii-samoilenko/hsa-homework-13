package com.example.client

import com.example.util.MetricsCounter
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.args.ListDirection.LEFT
import redis.clients.jedis.args.ListDirection.RIGHT
import java.io.Closeable

class RedisRpopLpushClient(private val poolSize: Int, private val counter: MetricsCounter) : Closeable {
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

    fun push(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.rpush(nextChannel(), message)
            counter.recordPush()
        }
    }

    fun pull() {
        publishPool.resource.use { jedis ->
            val bytes = jedis.lmove(nextChannel(), workingChannel, LEFT, RIGHT)
            if (bytes != null) {
                counter.recordPull()
                val count = jedis.lrem(workingChannel, 1, bytes)
                for (i in 0 until count) {
                    print('\'')
                }
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
