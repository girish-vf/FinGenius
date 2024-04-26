package io.educative.models

//In the Product.kt file, create a data class Product, comprising the following attributes: _id (nullable String and initialize it with a null value), name, coreCompany, rate, status, taxExempted, salesTax, and notes.
data class Product(
    val _id: String? = null,
    val name: String,
    val coreCompany: String,
    val rate: Double,
    val status: String,
    val taxExempted: Boolean,
    val salesTax: Double,
    val notes: String
)
