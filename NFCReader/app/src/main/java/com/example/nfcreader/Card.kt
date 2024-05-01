package com.example.nfcreader

data class Card (
    val id: String,
    val cardObject: String,
    val addressCity: Any? = null,
    val addressCountry: Any? = null,
    val addressLine1: Any? = null,
    val addressLine1Check: Any? = null,
    val addressLine2: Any? = null,
    val addressState: Any? = null,
    val addressZip: Any? = null,
    val addressZipCheck: Any? = null,
    val brand: String,
    val country: String,
    val customer: String,
    val cvcCheck: Any? = null,
    val dynamicLast4: Any? = null,
    val expMonth: Long,
    val expYear: Long,
    val fingerprint: String,
    val funding: String,
    val last4: String,
    val metadata: Metadata,
    val name: Any? = null,
    val tokenizationMethod: Any? = null,
    val wallet: Any? = null
)

