package io.educative.routes

import io.educative.models.Partner
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.educative.models.Database
import io.ktor.server.plugins.contentnegotiation.ContentTransformationException

fun Route.partnerRoutes(){
    val database = Database()
    val partnersCollection = database.db.getCollection<Partner>("partners")

    route("/partners"){
 //In the /usercode/FinGenius/backend/src/main/kotlin/io/educative/routes/PartnerRoutes.kt file, do the following:
        //
        //Define a route to handle an HTTP POST request to add partner information.
        //
        //Receive the partner data, a Partner object, from the incoming request body.
        //
        //Insert the received partner data into the partners collection (partnersCollection).
        //
        //Check the result of the insertion operation:
        //
        //If the insertion is successful, respond with an HTTP status code 201 (Created) and a success message that includes the added partner's details.
        //
        //If the insertion fails due to a server error, respond with an HTTP status code 500 (Internal Server Error) and an appropriate error message.       // Route to add a partner
        post{
            try{
                val partner = call.receive<Partner>()
                val result = partnersCollection.insertOne(partner)
                if(result?.wasAcknowledged()){
                    call.respond(HttpStatusCode.Created, "${partner.type.replaceFirstChar { it.uppercase() }} added successfully: ${partner.firstName}  ${partner.lastName}.")
            }else{
                call.respond(HttpStatusCode.InternalServerError, "Failed to add ${partner.type}.")
            }
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest, "Invalid input data format.")
            }


        }
    }
}