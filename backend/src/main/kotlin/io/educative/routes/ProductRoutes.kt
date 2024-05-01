package io.educative.routes

import io.educative.models.Database
import io.educative.models.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.eq

fun Route.productRoutes(){
    val database = Database()
    val productsCollection = database.db.getCollection<Product>("products")

    route("/products"){

        // Route to add a product
        post {
            val product = call.receive<Product>()
            try {
                val insertResult = productsCollection.insertOne(product)
                if (insertResult.wasAcknowledged()) {
                    call.respond(HttpStatusCode.Created, "Product added successfully.")
                }
                else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to add product.")
                }

            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
            }
        }


        // Route to edit a product
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing product id")
            try {
                val updateProduct = call.receive<Product>()
                val filter = Product::_id eq id
                val updateResult = productsCollection.replaceOne(filter, updateProduct)
                if (updateResult.matchedCount > 0) {
                    call.respond(HttpStatusCode.OK, "Product updated successfully.")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found.")
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
            }
        }

        // Route to get product(s)
        // Define a GET route to retrieve product(s)
        // Check for presence of the id and status query parameters
        // if ID is present, retrieve product by ID
        // if product with ID exists, return it with 200 OK status code and return responce body json format
        // if product with ID does not exist, return 404 Not Found status code and error message
        // if id is not present, retrieve product(s) based on present of status or not
        // if status is present, retrieve all product(s) based on status
        // if status is not present, retrieve all product(s)
        // respond with 200 OK status code and return responce body json format
        get("{id?}") {
            val id = call.parameters["id"]
            val status = call.parameters["status"]
            try {
                if (id != null) {
                    val product = productsCollection.findOneById(id)
                    if (product != null) {
                        call.respond(product)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Product not found.")
                    }
                } else if (status != null) {
                    val products = productsCollection.find(Product::status eq status).toList()
                    call.respond(products)
                } else {
                    val products = productsCollection.find().toList()
                    call.respond(products)
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
            }
        }
    }
}