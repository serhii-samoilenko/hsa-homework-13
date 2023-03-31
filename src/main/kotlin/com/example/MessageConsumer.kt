package com.example

import java.util.concurrent.atomic.AtomicLong

class MessageConsumer {
    private val counter = AtomicLong(0)

    fun getCount(): Long = counter.get()

    fun consumeMessage(message: ByteArray) {
        if (counter.incrementAndGet() % 100 == 0L) {
            println("- ${counter.get()}")
        }
    }
}