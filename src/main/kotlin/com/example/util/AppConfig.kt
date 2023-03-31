package com.example.util

import io.smallrye.config.ConfigMapping
import io.smallrye.config.WithName

@ConfigMapping(prefix = "app")
interface AppConfig {
    fun redis(): RedisConfig
}

interface RedisConfig {
    @WithName("pool-size")
    fun poolSize(): Int

    @WithName("pub-sub-channel")
    fun pubSubChannel(): String
}
