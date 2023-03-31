package com.example

import java.util.concurrent.atomic.AtomicLong

class MessageProducer(
    private val minLength: Int,
    private val maxLength: Int,
) {
    private val counter = AtomicLong(0)

    fun getCount(): Long = counter.get()

    fun nextMessage(): ByteArray {
        val length = minLength + (Math.random() * (maxLength - minLength)).toInt()
        val bytes = ByteArray(length)
        for (i in 0 until length) {
            bytes[i] = (Math.random() * 26 + 'a'.code).toInt().toByte()
        }
        counter.incrementAndGet()
        return bytes
    }
}
