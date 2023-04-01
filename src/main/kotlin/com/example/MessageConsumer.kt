package com.example

import java.util.concurrent.atomic.AtomicLong

class MessageConsumer {
    private val counter = AtomicLong(0)

    fun getCount(): Long = counter.get()

    fun consumeMessage(message: ByteArray) {
        counter.incrementAndGet()
    }
}
