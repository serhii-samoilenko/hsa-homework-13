package com.example.util

import com.example.MessageConsumer
import com.example.MessageProducer
import com.example.client.RedisPubSubClient
import com.example.client.RedisRpopLpushClient
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Benchmark {

    data class Result(
        val count: Long,
        val duration: Duration,
    ) {
        fun opsPerSecond(): Double {
            val ops = count * 1000.0 / duration.inWholeMilliseconds
            return when {
                ops > 100 -> round(ops)
                ops > 10 -> round(10.0 * ops) / 10.0
                ops > 1 -> round(100.0 * ops) / 100.0
                else -> round(1000.0 * count * 1000.0 / duration.inWholeMilliseconds) / 1000.0
            }
        }

        override fun toString() = "$count ops in $duration - ${opsPerSecond()} ops/sec"
    }

    fun benchmarkPubSub(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val producer = MessageProducer(messageSizes)
        val consumer = MessageConsumer()
        val client = RedisPubSubClient(poolSize)
        client.subscribe(consumer)
        val executor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            executor.submit {
                client.publish(producer.nextMessage())
            }
        }
        executor.shutdown()
        executor.awaitTermination(10, MINUTES)
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        var attempts = 0
        while (attempts++ < 10 && consumer.getCount() < producer.getCount()) {
            Thread.sleep(1000)
        }
        println()
        if (consumer.getCount() < producer.getCount()) {
            println("Missing messages: ${producer.getCount() - consumer.getCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(producer.getCount(), consumeTime.milliseconds)
    }

    fun benchmarkRpushLpop(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val producer = MessageProducer(messageSizes)
        val consumer = MessageConsumer()
        val client = RedisRpopLpushClient(poolSize)
        val executor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            executor.submit {
                client.push(producer.nextMessage())
                client.pop(consumer)
            }
        }
        executor.shutdown()
        executor.awaitTermination(10, MINUTES)
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        var attempts = 0
        while (attempts++ < 10 && consumer.getCount() < producer.getCount()) {
            Thread.sleep(1000)
        }
        println()
        if (consumer.getCount() < producer.getCount()) {
            println("Missing messages: ${producer.getCount() - consumer.getCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(producer.getCount(), consumeTime.milliseconds)
    }
}
