package com.example.client

import com.dinstone.beanstalkc.BeanstalkClientFactory
import com.dinstone.beanstalkc.Configuration
import com.example.MessageConsumer
import java.io.Closeable
import java.util.Timer
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.schedule

class BeanstalkClient(private val poolSize: Int) : Closeable {
    private val config = Configuration().also {
        it.serviceHost = "127.0.0.1"
        it.servicePort = 11300
        it.connectTimeout = 5000
        it.readTimeout = 60000
        it.operationTimeout = 60000
        it.jobMaxSize = 1024 * 1024 * 10
    }
    private val factory = BeanstalkClientFactory(config)
    private val clientId = number++
    private val nextProducer = (0 until poolSize)
        .map { "rpush-lpop-$clientId-$it" }
        .map { it to factory.createJobProducer(it) }
        .toCircularIterator()
    private val nextConsumer = (0 until poolSize)
        .map { "rpush-lpop-$clientId-$it" }
        .map { it to factory.createJobConsumer(it) }
        .toCircularIterator()
    private val pushesCount = ConcurrentHashMap<String, Int>()
    private val popsCount = ConcurrentHashMap<String, Int>()
    private val deletesCount = ConcurrentHashMap<String, Int>()

    init {
        Timer().schedule(1000, 1000) {
            println("\n\nPushes: \n${pushesCount.entries.sortedBy { it.key }.joinToString("\n") { "${it.key}: ${it.value}" }}")
            println("\nPops: \n${popsCount.entries.sortedBy { it.key }.joinToString("\n") { "${it.key}: ${it.value}" }}")
            println("\nDeletes: \n${deletesCount.entries.sortedBy { it.key }.joinToString("\n") { "${it.key}: ${it.value}" }}")
        }
    }

    fun push(message: ByteArray) {
        val (pipe, producer) = nextProducer()
        producer.putJob(0, 0, 60 * 10, message)
        pushesCount.compute(pipe) { _, count -> (count ?: 0) + 1 }
//        println("> $pipe")
        print('+')
    }

    fun pop(messageConsumer: MessageConsumer) {
        val (pipe, consumer) = nextConsumer()
        val job = consumer.reserveJob(1)
//        println("< $pipe")
        if (job != null) {
            popsCount.compute(pipe) { _, count -> (count ?: 0) + 1 }
//            println("$ $pipe")
            messageConsumer.consumeMessage(job.data)
            print('-')
            consumer.deleteJob(job.id)
            deletesCount.compute(pipe) { _, count -> (count ?: 0) + 1 }
            print('\'')
//            println("- $pipe")
        }
    }

    override fun close() {
        for (i in 0 until poolSize) {
            nextProducer().second.close()
            nextConsumer().second.close()
        }
    }

    companion object {
        private var number = 0
    }
}
