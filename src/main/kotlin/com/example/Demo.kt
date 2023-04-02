package com.example

import com.example.util.Benchmark.Result
import com.example.util.Benchmark.benchmarkBeanstalk
import com.example.util.Benchmark.benchmarkPubSub
import com.example.util.Benchmark.benchmarkRpushLpop
import com.example.util.Docker
import com.example.util.Report
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Queues demo
 *
 * This demo will compare performance of Redis and Beanstalkd queues in different configurations.
 */
fun runDemo() {
    val r = Report("REPORT.md")
    r.h1("Queues demo demo report")

    val docker = Docker(r)
    val poolSizes = listOf(
//        1,
        10,
        50,
        100,
    )
    val messageSizes = listOf(
        "1-2Kb" to (1024..1024 * 2),
        "10-20Kb" to (1024 * 10..1024 * 20),
        "50-100Kb" to (1024 * 50..1024 * 100),
        "500Kb-1Mb" to (1024 * 500..1024 * 1024),
//        "5-10Mb" to (1024 * 1024 * 5..1024 * 1024 * 10),
    )
    val runDuration = 120.seconds
    val cooldownDuration = 15.seconds
    val results = LinkedHashMap<String, LinkedHashMap<String, Result>>()
    r.text("For each Redis persistence mode, another Redis Docker container will be started.")
    r.line()

    fun runBenchmarks(
        messageBusTypes: List<Pair<String, Docker.Service>>,
        poolSizes: List<Int>,
        messageSizes: List<Pair<String, IntRange>>,
        benchmarkRun: (duration: Duration, poolSize: Int, messageSize: IntRange) -> Result,
    ) {
        for ((messageBusConfig, service) in messageBusTypes) {
            r.h3(messageBusConfig)
            val containerHandle = docker.start(service)
            for (poolSize in poolSizes) {
                for ((size, bytes) in messageSizes) {
                    println("Waiting for $cooldownDuration before starting next test")
                    Thread.sleep(cooldownDuration.inWholeMilliseconds)

                    println("Container: $messageBusConfig")
                    val loadType = "Pool: $poolSize, payload: $size"
                    r.h4("Pool size: `$poolSize`, message size: `$size`")

                    val result = benchmarkRun(runDuration, poolSize, bytes)
                    r.text("Result: `$result`")
                    results.getOrPut(loadType) { LinkedHashMap() }[messageBusConfig] = result
                }
            }
            containerHandle.close()
        }
    }

    r.h2("Redis Pub-Sub in different persistence modes")
    val redisPubSubContainers = listOf(
        "Redis Pub-Sub NoP" to Docker.Service.REDIS_NOP,
        "Redis Pub-Sub AOF" to Docker.Service.REDIS_AOF,
        "Redis Pub-Sub RDB" to Docker.Service.REDIS_RDB,
    )
    runBenchmarks(redisPubSubContainers, poolSizes, messageSizes) { duration, poolSize, messageSize ->
        benchmarkPubSub(duration, poolSize, messageSize)
    }

    r.h2("Redis Rpush-Lpop in different persistence modes")
    val redisRpushLpopContainers = listOf(
        "Redis Rpush-Lpop NoP" to Docker.Service.REDIS_NOP,
        "Redis Rpush-Lpop AOF" to Docker.Service.REDIS_AOF,
        "Redis Rpush-Lpop RDB" to Docker.Service.REDIS_RDB,
    )
    runBenchmarks(redisRpushLpopContainers, poolSizes, messageSizes) { duration, poolSize, messageSize ->
        benchmarkRpushLpop(duration, poolSize, messageSize)
    }

    r.h2("BeansTalk without persistence, with immediate flush, and with 1s flush interval")
    val beanstalkdContainers = listOf(
        "Beanstalk NoP" to Docker.Service.BEANSTALKD_NOP,
        "Beanstalk 0s" to Docker.Service.BEANSTALKD_0S,
        "Beanstalk 1s" to Docker.Service.BEANSTALKD_1S,
    )
    runBenchmarks(beanstalkdContainers, poolSizes, messageSizes) { duration, poolSize, messageSize ->
        benchmarkBeanstalk(duration, poolSize, messageSize)
    }

    r.htmlTable("Ops/sec", results)

    r.writeToFile()
}
