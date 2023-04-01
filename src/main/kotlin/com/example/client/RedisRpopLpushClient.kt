package com.example.client

import com.dinstone.beanstalkc.Configuration
import com.example.MessageConsumer
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.args.ListDirection.LEFT
import redis.clients.jedis.args.ListDirection.RIGHT
import java.io.Closeable

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

    fun push(message: ByteArray) {
        publishPool.resource.use { jedis ->
            jedis.rpush(nextChannel(), message)
            print('+')
        }
    }

    fun pop(messageConsumer: MessageConsumer) {
        publishPool.resource.use { jedis ->
            val bytes = jedis.lmove(nextChannel(), workingChannel, LEFT, RIGHT)
            if (bytes != null) {
                messageConsumer.consumeMessage(bytes)
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
