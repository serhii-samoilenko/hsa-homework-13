package com.example.client

import kotlin.random.Random

object MessageProducer {
    fun next(lengthRange: IntRange): ByteArray {
        val length = lengthRange.random()
        val bytes = ByteArray(length)
        Random.nextBytes(bytes)
        print('.')
        return bytes
    }
}
