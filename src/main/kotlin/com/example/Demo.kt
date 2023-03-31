package com.example

import com.example.client.RedisPubSubClient
import com.example.util.Docker
import com.example.util.Helper
import com.example.util.Report
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.enterprise.context.ApplicationScoped
import kotlin.system.measureTimeMillis
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Queues demo
 *
 * This demo will compare performance of Redis and Beanstalkd queues in different configurations.
 */
fun runDemo(helper: Helper) = with(helper) {
    val r = Report("REPORT.md")
    r.h1("Queues demo demo report")

    val docker = Docker(r)
    val poolSizes = listOf(10, 100, 1000)
    val messageSizes = listOf(
        "1-2Kb" to (1024..2048),
        "10-20Kb" to (1024 * 2..2048 * 2),
        "50-100Kb" to (1024 * 50..2048 * 100),
        "500Kb-1Mb" to (1024 * 500..1024 * 1024),
        "1-10Mb" to (1024 * 1024..1024 * 1024 * 10),
    )
    val messageCount = 10000L
    val repeats = 1

    r.h2("Redis Pub-Sub")
    r.text("For each Redis persistence mode, another Redis Docker container will be started.")
    r.h3("Redis persistence mode: `no`")
    val containerHandle = docker.start(Docker.Service.REDIS_NOP)

    for (poolSize in poolSizes) {
        for (messageSize in messageSizes) {
            r.h4("Pool size: `$poolSize`, message count: $messageCount, message size: `${messageSize.first}`")
            val duration = runPubSubScenario(
                messageCount = messageCount,
                repeats = repeats,
                client = RedisPubSubClient(poolSize),
                executor = Executors.newFixedThreadPool(poolSize),
                producer = MessageProducer(messageSize.second),
                consumer = MessageConsumer(),
            )
            r.text("Duration: `$duration`")
        }
    }
    containerHandle.close()

    r.writeToFile()
}

fun runPubSubScenario(
    messageCount: Long,
    repeats: Int,
    client: RedisPubSubClient,
    executor: ExecutorService,
    producer: MessageProducer,
    consumer: MessageConsumer,
): Duration {
    client.subscribe(consumer)
    val duration = try {
        (1..repeats).map { _ ->
            measurePubSub(messageCount, client, executor, producer, consumer)
                .also { Thread.sleep(100) }
        }.average().milliseconds
    } finally {
        executor.shutdown()
        client.close()
    }
    return duration
}

fun measurePubSub(
    messageCount: Long,
    client: RedisPubSubClient,
    executor: ExecutorService,
    producer: MessageProducer,
    consumer: MessageConsumer,
): Long = measureTimeMillis {
    for (i in 1..messageCount) {
        executor.submit {
            client.publish(producer.nextMessage())
        }
    }
    // wait for all messages to be consumed by the consumer
    while (consumer.getCount() < messageCount) {
        Thread.sleep(100)
    }
}

@ApplicationScoped
class Demo(private val helper: Helper) {
    fun start() {
        runDemo(helper)
    }
}
