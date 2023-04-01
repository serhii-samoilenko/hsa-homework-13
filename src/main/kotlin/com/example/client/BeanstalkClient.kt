package com.example.client

import com.dinstone.beanstalkc.BeanstalkClientFactory
import com.dinstone.beanstalkc.Configuration
import java.io.Closeable
import java.util.concurrent.atomic.AtomicLong

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
        .map { factory.createJobProducer(it) }
        .toCircularIterator()
    private val nextConsumer = (0 until poolSize)
        .map { "rpush-lpop-$clientId-$it" }
        .map { factory.createJobConsumer(it) }
        .toCircularIterator()

    private val pushes = AtomicLong(0)
    private val pops = AtomicLong(0)

    fun getPushesCount() = pushes.get()
    fun getPopsCount() = pops.get()

    fun push(message: ByteArray) {
        val job = nextProducer().putJob(0, 0, 60 * 10, message)
        job.inc()
        pushes.incrementAndGet()
        print('+')
    }

    fun pop() {
        val consumer = nextConsumer()
        val job = consumer.reserveJob(1)
        if (job != null) {
            pops.incrementAndGet()
            print('-')
            consumer.deleteJob(job.id)
            print('\'')
        }
    }

    override fun close() {
        for (i in 0 until poolSize) {
            nextProducer().close()
            nextConsumer().close()
        }
    }

    companion object {
        private var number = 0
    }
}
