package io.educative.modules

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.educative.routes.invoiceRoutes

fun Application.invoiceModule() {
    routing {
        // Include the consolidated customer routes
        invoiceRoutes()
    }
}
