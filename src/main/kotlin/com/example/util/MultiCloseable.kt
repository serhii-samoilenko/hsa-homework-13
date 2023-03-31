package com.example.util

import java.io.Closeable

class MultiCloseable(vararg members: Closeable) : Closeable {
    private val members = members.toList()
    override fun close() {
        members.asReversed().forEach { it.close() }
    }
}
