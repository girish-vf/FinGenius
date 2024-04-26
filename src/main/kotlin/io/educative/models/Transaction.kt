package io.educative.models
import kotlinx.serialization.Serializable

//In the Transaction.kt file, create the following data classes:
//
//Transaction class, comprising the following fields: _id (nullable String and initialize it with a null value), voucherNo, type, date, paymentTo, receiptFrom, amount, and reference.
//
//TransactionJson class, comprising the following fields: partner (nullable Partner), transaction (Transaction).

@Serializable
data class Transaction(
    val _id: String? = null,
    val voucherNo: String,
    val type: String,
    val date: String,
    val paymentTo: String,
    val receiptFrom: String,
    val amount: Double,
    val reference: String
)

@Serializable
data class TransactionJson(
    val partner: Partner?,
    val transaction: Transaction
)