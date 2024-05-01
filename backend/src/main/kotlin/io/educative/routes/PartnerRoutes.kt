package io.educative.routes

import io.educative.models.Database
import io.educative.models.Partner
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.eq
import org.litote.kmongo.and

fun Route.partnerRoutes(){
    val database = Database()
    val partnersCollection = database.db.getCollection<Partner>("partners")

    route("/partners"){
        // Route to add a partner
        post {
            try {
                val partner = call.receive<Partner>()
                val insertResult = partnersCollection.insertOne(partner)
                if (insertResult.wasAcknowledged()) {
                    call.respond(HttpStatusCode.Created, "${partner.type.replaceFirstChar { it.uppercase() }} added successfully: ${partner.firstName}  ${partner.lastName}.")
                }
                else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to add ${partner.type}.")
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
            }
        }
        // Route to edit a partner
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing id")
            try {
                val updatePartner = call.receive<Partner>()
                val filter = Partner::_id eq id
                val updateResult = partnersCollection.replaceOne(filter, updatePartner)
                if (updateResult.matchedCount > 0) {
                    call.respond(HttpStatusCode.OK, "${updatePartner.type.replaceFirstChar { it.uppercase() }} updated successfully.")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Partner not found.")
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")
            }
        }




        // Route to get partner(s)
        // Define a GET route to retrieve pertner(s)
        // Check for presence ot type, id and status query parameters
        // if ID is present, retrieve partner by ID
        // if partner with ID exists, return it with 200 OK status code and return responce body json format
        // if partner with ID does not exist, return 404 Not Found status code and error message
        // if id is not present but type is, retrieve partner(s) based on present of status or not
        // if status is present, retrieve partner(s) based on status and type
        // if status is not present, retrieve partner(s) based on type
        // respond with 200 OK status code and return responce body json format
        // if neither id nor type is present, retrieve all partner(s) and repospond with 200 OK status code and return responce body json format
        // Route to get partner(s)
        get {
            val type = call.request.queryParameters["type"]
            val id = call.request.queryParameters["id"]
            val statusParam = call.request.queryParameters["status"]
            try {
                if (id != null) {
                    // Retrieve a partner by ID (ignoring the 'type' parameter)
                    val partner = partnersCollection.findOneById(id)
                    if (partner != null) {
                        call.respond(partner)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Partner not found.")
                    }
                } else if (type != null) {
                    // No ID parameter, retrieve partners based on type and status
                    val partners: List<Partner> =
                        if (statusParam != null) {
                            partnersCollection.find(and(Partner::type eq type, Partner::status eq statusParam)).toList()
                        } else {
                            partnersCollection.find(Partner::type eq type).toList()
                        }
                    call.respond(HttpStatusCode.OK, partners)
                } else {
                    // Retrieve all partners
                    val partners = partnersCollection.find().toList()
                    call.respond(HttpStatusCode.OK, partners)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve partners.")
            }
        }
    }
}