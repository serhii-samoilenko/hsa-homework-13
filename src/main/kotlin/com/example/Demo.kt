package com.example

import com.example.client.RedisPubSubClient
import com.example.util.Docker
import com.example.util.Helper
import com.example.util.Report
import java.util.concurrent.Executors
import javax.enterprise.context.ApplicationScoped
import kotlin.system.measureTimeMillis

/**
 * Queues demo
 *
 * This demo will compare performance of Redis and Beanstalkd queues in different configurations.
 */
fun runDemo(helper: Helper) = with(helper) {
    val r = Report("REPORT.md")
    val docker = Docker(r)
    r.h1("Queues demo demo report")
    r.h2("Redis Pub-Sub")
    r.text("For each Redis persistence mode, another Redis Docker container will be started.")
    r.h3("Redis persistence mode: `no`")

    val redisPubSubClient = RedisPubSubClient(config.redis().poolSize(), config.redis().pubSubChannel())

    docker.start(Docker.Service.REDIS_NOP)
    redisPubSubClient.open()

    val executor = Executors.newFixedThreadPool(10)
    r.text("Publishing 10 messages under 1Kb")

    val consumer = MessageConsumer()
    redisPubSubClient.subscribe(consumer)

    val producer = MessageProducer(256, 1024)

    measureTimeMillis {
        for (i in 1..10) {
            executor.submit {
                redisPubSubClient.publish(producer.nextMessage())
            }
        }
        // wait for all messages to be consumed by the consumer
        while (consumer.getCount() < 10) {
            Thread.sleep(100)
        }
    }.also {
        r.text("Publishing 10 messages took $it ms")
    }

    redisPubSubClient.close()
    docker.stop(Docker.Service.REDIS_NOP)
    r.writeToFile()
}

@ApplicationScoped
class Demo(private val helper: Helper) {
    fun start() {
        runDemo(helper)
    }
}
