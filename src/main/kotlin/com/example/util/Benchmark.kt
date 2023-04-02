package com.example.util

import com.example.client.BeanstalkClient
import com.example.client.MessageProducer
import com.example.client.RedisPubSubClient
import com.example.client.RedisRpopLpushClient
import org.apache.commons.collections4.queue.CircularFifoQueue
import java.util.concurrent.Future
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

object Benchmark {

    data class Result(
        val count: Long,
        val duration: Duration,
        val failCount: Long,
    ) {
        fun opsPerSecond(): Double {
            val ops = count * 1000.0 / duration.inWholeMilliseconds
            return when {
                ops > 10 -> round(10.0 * ops) / 10.0
                ops > 1 -> round(100.0 * ops) / 100.0
                else -> round(1000.0 * count * 1000.0 / duration.inWholeMilliseconds) / 1000.0
            }
        }

        override fun toString() = "$count ops in $duration - ${opsPerSecond()} ops/sec, $failCount fails"
    }

    fun benchmarkPubSub(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val counter = MetricsCounter()
        val redisPubSubClient = RedisPubSubClient(poolSize, counter)
        return redisPubSubClient.use { client ->
            client.subscribe()
            benchmark(
                duration,
                poolSize,
                { client.publish(MessageProducer.next(messageSizes)) },
                { Thread.sleep(100) },
                counter,
            )
        }
    }

    fun benchmarkRpushLpop(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val counter = MetricsCounter()
        val redisRpopLpushClient = RedisRpopLpushClient(poolSize, counter)
        return redisRpopLpushClient.use { client ->
            benchmark(
                duration,
                poolSize,
                { client.push(MessageProducer.next(messageSizes)) },
                { client.pull() },
                counter,
            )
        }
    }

    fun benchmarkBeanstalk(
        duration: Duration,
        poolSize: Int,
        messageSizes: IntRange,
    ): Result {
        val counter = MetricsCounter()
        val beanstalkClient = BeanstalkClient(poolSize, counter)
        return beanstalkClient.use { client ->
            benchmark(
                duration,
                poolSize,
                { client.push(MessageProducer.next(messageSizes)) },
                { client.pull() },
                counter,
            )
        }
    }

    private fun benchmark(
        duration: Duration,
        poolSize: Int,
        pushOperation: () -> Unit,
        pullOperation: () -> Unit,
        counter: MetricsCounter,
    ): Result {
        val tryExecute = { operation: () -> Unit ->
            try {
                operation()
            } catch (e: Exception) {
                println("Error: $e")
                counter.recordFail()
                Thread.sleep(1000)
            }
        }
        val pushExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pullExecutor = ThreadPoolExecutor(poolSize, poolSize, 100, MILLISECONDS, SynchronousQueue(), CallerRunsPolicy())
        val pushTaskQueue = CircularFifoQueue<Future<*>>(poolSize)
        val pullTaskQueue = CircularFifoQueue<Future<*>>(poolSize)
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            for (i in 0 until poolSize - pushExecutor.activeCount) {
                pushExecutor.submit {
                    tryExecute { pushOperation() }
                }.also { pushTaskQueue.add(it) }
            }
            for (i in 0 until poolSize - pullExecutor.activeCount) {
                pullExecutor.submit {
                    tryExecute { pullOperation() }
                }.also { pullTaskQueue.add(it) }
            }
        }
        pushExecutor.shutdown()
        pushTaskQueue.forEach { it.get() }
        val publishTime = System.currentTimeMillis() - startTime
        println("\nPublish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        val waitTime = System.currentTimeMillis() + 1.minutes.inWholeMilliseconds
        while (counter.pullCount() < counter.pushCount() && System.currentTimeMillis() < waitTime) {
            pullExecutor.submit {
                tryExecute { pullOperation() }
            }.also { pullTaskQueue.add(it) }
        }
        pullExecutor.shutdown()
        pullTaskQueue.forEach { it.get() }
        println()
        if (counter.pullCount() < counter.pushCount()) {
            println("Missing messages: ${counter.pushCount() - counter.pullCount()}")
        }
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(counter.pullCount(), consumeTime.milliseconds, counter.failCount())
    }
}
