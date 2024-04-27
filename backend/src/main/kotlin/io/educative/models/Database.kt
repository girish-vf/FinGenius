package io.educative.models

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class Database {
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://girishguptavf:gordon123@appcluster.aznzaz3.mongodb.net/?retryWrites=true&w=majority&appName=AppCluster"
    ).coroutine
        .getDatabase("fingenius")
}