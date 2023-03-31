package com.example.util

import com.example.MessageConsumer
import com.example.MessageProducer
import com.example.client.RedisPubSubClient
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.math.round
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Benchmark {

    data class Result(
        val count: Long,
        val duration: Duration,
    ) {
        private fun opsPerSecond() = round(1000.0 * count * 1000.0 / duration.inWholeMilliseconds) / 1000.0
        override fun toString() = "$count ops in $duration - ${opsPerSecond()} ops/sec"
    }

    fun benchmarkPubSub(
        duration: Duration,
        poolSize: Int,
        producer: MessageProducer,
        consumer: MessageConsumer,
    ): Result {
        val client = RedisPubSubClient(poolSize)
        client.subscribe(consumer)
        val threads = poolSize
        val executor = Executors.newFixedThreadPool(threads) as ThreadPoolExecutor
        val startTime = System.currentTimeMillis()
        val endTime = startTime + duration.inWholeMilliseconds
        while (System.currentTimeMillis() < endTime) {
            val totalCount = executor.activeCount + executor.queue.size
            val capacity = threads - totalCount - 1
            for (i in 0 until capacity) {
                executor.submit {
                    client.publish(producer.nextMessage())
                }
            }
        }
        executor.shutdown()
        executor.awaitTermination(10, MINUTES)
        val publishTime = System.currentTimeMillis() - startTime
//        println("Publish time: $publishTime")
        // wait for all messages to be consumed by the consumer
        if (consumer.getCount() < producer.getCount()) {
            Thread.sleep(1000)
        }
        if (consumer.getCount() < producer.getCount()) {
            println("Missing messages: ${producer.getCount() - consumer.getCount()}")
        }
        client.close()
        val consumeTime = System.currentTimeMillis() - startTime
        return Result(producer.getCount(), consumeTime.milliseconds)
    }
}
