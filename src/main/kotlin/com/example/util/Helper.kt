package com.example.util

import io.quarkus.runtime.Startup
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
class Helper(
    val config: AppConfig,
)
