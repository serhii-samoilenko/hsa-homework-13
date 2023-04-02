package com.example.util

import java.util.concurrent.atomic.AtomicLong

class MetricsCounter {
    private val pushCount = AtomicLong(0)
    private val pullCount = AtomicLong(0)
    private val failCount = AtomicLong(0)

    fun recordPush() = pushCount.incrementAndGet().also { print('+') }
    fun recordPull() = pullCount.incrementAndGet().also { print('-') }
    fun recordFail() = failCount.incrementAndGet()
    fun pushCount() = pushCount.get()
    fun pullCount() = pullCount.get()
    fun failCount() = failCount.get()
}
