package com.example.client

import com.dinstone.beanstalkc.BeanstalkClientFactory
import com.dinstone.beanstalkc.Configuration
import com.example.util.MetricsCounter
import java.io.Closeable

class BeanstalkClient(private val poolSize: Int, private val counter: MetricsCounter) : Closeable {
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

    fun push(message: ByteArray) {
        nextProducer().putJob(0, 0, 60 * 10, message)
        counter.recordPush()
    }

    fun pull() {
        val consumer = nextConsumer()
        val job = consumer.reserveJob(1)
        if (job != null) {
            counter.recordPull()
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
