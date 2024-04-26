package io.educative.models
import kotlinx.serialization.Serializable

//In the Invoice.kt file, create the following data classes:
//
//InvoiceItem class, comprising the following fields: productName, quantity, rate, valueOfSupplies, salesTax, and netAmount.
//
//Invoice class, comprising the following fields: _id (nullable String and initialize it with a null value), type, invoiceDate, dueDate, invoiceNo, partnerId, creditTerm, reference, invoiceTotal, and invoiceItems (List<InvoiceItem>).
//
//InvoiceJson class, comprising the following fields: partner (nullable Partner), invoice (Invoice).

@Serializable
data class InvoiceItem(
    val productName: String,
    val quantity: Int,
    val rate: Double,
    val valueOfSupplies: Double,
    val salesTax: Double,
    val netAmount: Double
)

@Serializable
data class Invoice(
    val _id: String? = null,
    val type: String,
    val invoiceDate: String,
    val dueDate: String,
    val invoiceNo: String,
    val partnerId: String,
    val creditTerm: String,
    val reference: String,
    val invoiceTotal: Double,
    val invoiceItems: List<InvoiceItem>
)

@Serializable
data class InvoiceJson(
    val partner: Partner?,
    val invoice: Invoice
)