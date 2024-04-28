package io.educative.modules

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.educative.routes.transactionRoutes

fun Application.transactionModule() {
    routing {
        // Include the consolidated transaction routes
        transactionRoutes()
    }
}