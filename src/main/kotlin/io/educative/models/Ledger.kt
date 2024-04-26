package io.educative.models

//In the Ledger.kt file, create the following data classes:
//
//LedgerItem class, comprising the following fields: date, instrumentNo, reference, description, quantity, debit, credit, and balance.
//
//LedgerJson class, comprising the following fields: partner (nullable Partner), ledgerItems (List<LedgerItem>).

@Serializable
data class LedgerItem(
    val date: String,
    val instrumentNo: String,
    val reference: String,
    val description: String,
    val quantity: Int,
    val debit: Double,
    val credit: Double,
    val balance: Double
)

@Serializable
data class LedgerJson(
    val partner: Partner?,
    val ledgerItems: List<LedgerItem>
)