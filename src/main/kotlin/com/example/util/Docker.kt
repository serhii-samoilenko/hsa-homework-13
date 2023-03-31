package com.example.util

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class Docker(private val r: Report) {
    fun start(service: Service) {
        r.text(
            "Starting Docker container: `${service.container.dockerImageName}`, " +
                "command: `${service.container.commandParts.toList()}`",
        )
        service.container.start()
    }

    fun stop(service: Service) {
        service.container.stop()
        r.text("Stopped Docker container: `${service.container.dockerImageName}`")
    }

    enum class Service(val container: GenericContainer<*>) {
        REDIS_NOP(
            GenericContainer(DockerImageName.parse("redis:7-alpine"))
                .withCommand("redis-server", "--appendonly", "no")
                .withExposedPorts(6379)
                .also { it.setPortBindings(listOf("6379:6379")) },
        ),
        REDIS_AOF(
            GenericContainer(DockerImageName.parse("redis:7-alpine"))
                .withCommand("redis-server", "--appendonly", "yes")
                .withExposedPorts(6379),
        ),
        REDIS_RDB(
            GenericContainer(DockerImageName.parse("redis:7-alpine"))
                .withCommand("redis-server", "--save", "5 1000", "--save", "1 100")
                .withExposedPorts(6379),
        ),
    }
}
