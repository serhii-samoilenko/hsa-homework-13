package com.example.util

import com.example.MessageProducer
import com.example.client.BeanstalkClient
import com.example.client.RedisPubSubClient
import com.example.client.RedisRpopLpushClient
import org.apache.commons.collections4.queue.CircularFifoQueue
import org.apache.mina.util.CircularQueue
import java.util.ArrayDeque
import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

object Benchmark {

    data class Result(
        val count: Long,
        val duration: Duration,
    ) {
        fun opsPerSecond(): Double {
            val ops = count * 1000.0 / duration.inWholeMilliseconds
            return when {
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
        val client = RedisPubSubClient(poolSize)
        client.subscribe()
        val executor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pushFutures = mutableListOf<Future<*>>()
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            executor.submit {
                client.publish(MessageProducer.next(messageSizes))
            }.also {
                pushFutures.add(it)
            }
        }
        executor.shutdown()
        executor.awaitTermination(1, MINUTES)
        pushFutures.forEach { it.get() }
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        val waitTime = System.currentTimeMillis() + 1.minutes.inWholeMilliseconds
        while (client.getPopsCount() < client.getPushesCount() && System.currentTimeMillis() < waitTime) {
            Thread.sleep(100)
        }
        println()
        if (client.getPopsCount() < client.getPushesCount()) {
            println("Missing messages: ${client.getPushesCount() - client.getPopsCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(client.getPopsCount(), consumeTime.milliseconds)
    }

    fun benchmarkRpushLpop(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val client = RedisRpopLpushClient(poolSize)
        val pushExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val popExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pushFutures = mutableListOf<Future<*>>()
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            pushExecutor.submit {
                client.push(MessageProducer.next(messageSizes))
            }.also {
                pushFutures.add(it)
            }
            popExecutor.submit {
                client.pop()
            }
        }
        pushExecutor.shutdown()
        pushExecutor.awaitTermination(1, MINUTES)
        pushFutures.forEach { it.get() }
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        val waitTime = System.currentTimeMillis() + 1.minutes.inWholeMilliseconds
        while (client.getPopsCount() < client.getPushesCount() && System.currentTimeMillis() < waitTime) {
            popExecutor.submit {
                client.pop()
            }
        }
        popExecutor.shutdown()
        popExecutor.awaitTermination(1, MINUTES)
        println()
        if (client.getPopsCount() < client.getPushesCount()) {
            println("Missing messages: ${client.getPushesCount() - client.getPopsCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(client.getPopsCount(), consumeTime.milliseconds)
    }

    fun benchmarkBeanstalk(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val client = BeanstalkClient(poolSize)
        val pushExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pullExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pushTaskQueue = CircularFifoQueue<Future<*>>(poolSize)
        val pullTaskQueue = CircularFifoQueue<Future<*>>(poolSize)
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            for (i in 0 until poolSize - pushExecutor.activeCount) {
                pushExecutor.submit {
                    client.push(MessageProducer.next(messageSizes))
                }.also { pushTaskQueue.add(it) }
            }
            for (i in 0 until poolSize - pullExecutor.activeCount) {
                pullExecutor.submit {
                    client.pop()
                }.also { pullTaskQueue.add(it) }
            }
        }
        pushExecutor.shutdown()
        pushTaskQueue.forEach { it.get() }
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        val waitTime = System.currentTimeMillis() + 1.minutes.inWholeMilliseconds
        while (client.getPopsCount() < client.getPushesCount() && System.currentTimeMillis() < waitTime) {
            pullExecutor.submit {
                client.pop()
            }.also { pullTaskQueue.add(it) }
        }
        pullExecutor.shutdown()
        pullTaskQueue.forEach { it.get() }
        println()
        if (client.getPopsCount() < client.getPushesCount()) {
            println("Missing messages: ${client.getPushesCount() - client.getPopsCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(client.getPopsCount(), consumeTime.milliseconds)
    }
}
