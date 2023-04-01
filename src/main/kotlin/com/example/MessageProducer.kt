package com.example

import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

class MessageProducer(
    private val lengthRange: IntRange,
) {
    private val messagesProduced = AtomicLong(0)

    fun getCount(): Long = messagesProduced.get()

    fun nextMessage(): ByteArray {
        val length = lengthRange.first + (Math.random() * lengthRange.count()).toInt()
        val bytes = ByteArray(length)
        Random.nextBytes(bytes)
        messagesProduced.incrementAndGet()
        print('.')
        return bytes
    }
}
