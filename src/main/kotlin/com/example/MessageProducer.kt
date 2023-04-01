package com.example

import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

class MessageProducer(
    private val lengthRange: IntRange,
) {
    private val chunkSize = 128
    private val messagesProduced = AtomicLong(0)
    private val bytesGenerated = AtomicLong(0)

    fun getCount(): Long = messagesProduced.get()

    fun nextMessage(): ByteArray {
        val length = lengthRange.first + (Math.random() * lengthRange.count()).toInt()
        val bytes = ByteArray(length)
        Random.nextBytes(bytes)
        messagesProduced.incrementAndGet()
        if (bytesGenerated.addAndGet(length.toLong()) % chunkSize == 0L) {
            print('.')
        }
        return bytes
    }
}
