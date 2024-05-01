package io.educative

import com.typesafe.config.ConfigFactory
import io.educative.modules.*
import io.educative.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val host = config.property("ktor.deployment.host").getString()
    val port = config.property("ktor.deployment.port").getString()

    println("Host: $host, Port: $port")

    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    partnerModule()
    productModule()
    invoiceModule()
    transactionModule()
    ledgerModule()

    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}