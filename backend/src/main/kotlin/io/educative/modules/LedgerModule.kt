package io.educative.modules

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.educative.routes.ledgerRoutes

fun Application.ledgerModule() {
    routing {
        // Include the consolidated customer routes
        ledgerRoutes()
    }
}
