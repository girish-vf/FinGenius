package io.educative.routes

import io.educative.models.Database
import io.educative.models.Partner
import io.educative.models.Transaction
import io.educative.models.TransactionJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.eq
import org.litote.kmongo.or
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.transactionRoutes(){
    val database = Database()
    val transactionsCollection = database.db.getCollection<Transaction>("transactions")
    val partnersCollection = database.db.getCollection<Partner>("partners")

    route("/transactions") {
        // Route to add a transaction
        post{
            try {
                val transaction = call.receive<Transaction>()
                val insertResult = transactionsCollection.insertOne(transaction)
                if(insertResult.wasAcknowledged()){
                    call.respond(HttpStatusCode.Created, "Transaction added successfully.")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to add transaction.")
                }
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format")

            }
        }

        // Route to get all transactions
        get {
            val type = call.request.queryParameters["type"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing Type")
            try {
                val transactions: List<Transaction> = when(type){
                    "payment" -> {
                        transactionsCollection.find(
                            or(
                                Transaction::type eq "BPV",
                                Transaction::type eq "CPV"
                            )
                        ).toList()
                    }
                    "receipt" -> {
                        transactionsCollection.find(
                            or(
                                Transaction::type eq "BRV",
                                Transaction::type eq "CRV"
                            )
                        ).toList()
                    }
                    else -> {
                        return@get call.respond(HttpStatusCode.BadRequest, "Missing Type")
                    }
                }
                val responseList = mutableListOf<TransactionJson>()

                for (transaction in transactions){
                    val partner = when (type){
                        "payment" -> {
                            partnersCollection.findOneById(transaction.paymentTo)
                        }
                        "receipt" -> {
                            partnersCollection.findOneById(transaction.receiptFrom)
                        }
                        else -> {
                            return@get call.respond(HttpStatusCode.BadRequest, "Missing Type")
                        }
                    }
                    val jsonResponse = TransactionJson(partner, transaction)
                    responseList.add(jsonResponse)
                }
                val jsonResponse = Json.encodeToString(responseList)
                call.respond(HttpStatusCode.OK, jsonResponse)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to retrieve ${type}s.")
            }
        }
    }
}