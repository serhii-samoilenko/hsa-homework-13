package com.example

import com.example.util.Benchmark.benchmarkPubSub
import com.example.util.Docker
import com.example.util.Helper
import com.example.util.Report
import javax.enterprise.context.ApplicationScoped
import kotlin.time.Duration.Companion.seconds

/**
 * Queues demo
 *
 * This demo will compare performance of Redis and Beanstalkd queues in different configurations.
 */
fun runDemo(helper: Helper) = with(helper) {
    val r = Report("REPORT.md")
    r.h1("Queues demo demo report")

    val docker = Docker(r)
    val poolSizes = listOf(10, 80, 500)
    val messageSizes = listOf(
//        "1-2Kb" to (1024..2048),
//        "10-20Kb" to (1024 * 2..2048 * 2),
        "50-100Kb" to (1024 * 50..2048 * 100),
        "500Kb-1Mb" to (1024 * 500..1024 * 1024),
        "1-10Mb" to (1024 * 1024..1024 * 1024 * 10),
    )
    val duration = 15.seconds
    val results = LinkedHashMap<String, MutableMap<String, String>>()

    r.h2("Redis Pub-Sub")
    r.text("For each Redis persistence mode, another Redis Docker container will be started.")
    r.line()

    r.h3("Redis persistence mode: `no`")
    val messageBus = "Redis Pub-Sub No persistence"
    val containerHandle = docker.start(Docker.Service.REDIS_NOP)

    for (poolSize in poolSizes) {
        for (messageSize in messageSizes) {
            val loadType = "Pool size: `$poolSize`, message size: `${messageSize.first}`"
            r.h4(loadType)
            val result = benchmarkPubSub(
                duration = duration,
                poolSize = poolSize,
                producer = MessageProducer(messageSize.second),
                consumer = MessageConsumer(),
            )
            r.text("Result: `$result`")
            results.getOrPut(loadType) { LinkedHashMap() }[messageBus] = result.toString()
        }
    }
    containerHandle.close()

//    r.table(results)

    r.writeToFile()
}

@ApplicationScoped
class Demo(private val helper: Helper) {
    fun start() {
        runDemo(helper)
    }
}
