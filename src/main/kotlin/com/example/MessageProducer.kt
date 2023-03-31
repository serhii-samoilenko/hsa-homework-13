package com.example

import java.util.concurrent.atomic.AtomicLong

class MessageProducer(
    private val lengthRange: IntRange,
) {
    private val counter = AtomicLong(0)

    fun getCount(): Long = counter.get()

    fun nextMessage(): ByteArray {
        val length = lengthRange.first + (Math.random() * lengthRange.count()).toInt()
        val bytes = ByteArray(length)
        for (i in 0 until length) {
            bytes[i] = (Math.random() * 26 + 'a'.code).toInt().toByte()
        }
//        counter.incrementAndGet()
        if (counter.incrementAndGet() % 100 == 0L) {
            println("+ ${counter.get()}")
        }
        return bytes
    }
}
