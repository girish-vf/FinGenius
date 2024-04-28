package io.educative.modules

import io.educative.routes.partnerRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.partnerModule() {
    routing {
        // Include the consolidated partner routes
        partnerRoutes()
    }
}