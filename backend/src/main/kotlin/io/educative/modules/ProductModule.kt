package io.educative.modules

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.educative.routes.productRoutes

fun Application.productModule() {
    routing {
        // Include the consolidated product routes
        productRoutes()
    }
}
