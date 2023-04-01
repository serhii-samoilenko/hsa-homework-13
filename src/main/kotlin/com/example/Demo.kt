package com.example

import com.example.util.Benchmark.Result
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
        "1-2Kb" to (1024..1024 * 2),
        "10-20Kb" to (1024 * 10..1024 * 20),
        "50-100Kb" to (1024 * 50..1024 * 100),
        "500Kb-1Mb" to (1024 * 500..1024 * 1024),
        "2-10Mb" to (1024 * 1024 * 2..1024 * 1024 * 10),
    )
    val duration = 15.seconds
    val results = LinkedHashMap<String, LinkedHashMap<String, Result>>()

    r.h2("Redis Pub-Sub")
    r.text("For each Redis persistence mode, another Redis Docker container will be started.")
    r.line()

    val redisConfigurations = listOf(
        "Redis Pub-Sub NoP" to Docker.Service.REDIS_NOP,
        "Redis Pub-Sub AOF" to Docker.Service.REDIS_AOF,
        "Redis Pub-Sub RDB" to Docker.Service.REDIS_RDB,
    )

    for ((messageBusConfig, service) in redisConfigurations) {
        r.h3(messageBusConfig)
        val containerHandle = docker.start(service)
        for (poolSize in poolSizes) {
            for ((size, bytes) in messageSizes) {
                val loadType = "Pool size: `$poolSize`, message size: `$size`"
                r.h4(loadType)
                val result = benchmarkPubSub(
                    duration = duration,
                    poolSize = poolSize,
                    producer = MessageProducer(bytes),
                    consumer = MessageConsumer(),
                )
                r.text("Result: `$result`")
                results.getOrPut(loadType) { LinkedHashMap() }[messageBusConfig] = result
            }
        }
        containerHandle.close()
    }
//    r.table(results)

    r.writeToFile()
}

@ApplicationScoped
class Demo(private val helper: Helper) {
    fun start() {
        runDemo(helper)
    }
}
