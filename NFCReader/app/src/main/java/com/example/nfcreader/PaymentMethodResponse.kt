package com.example.nfcreader

data class PaymentMethodResponse (
    val id: String,
    val paymentMethodObject: String,
    val billingDetails: BillingDetails,
    val card: Card,
    val created: Long,
    val customer: Any? = null,
    val livemode: Boolean,
    val metadata: Metadata,
    val type: String
)

data class BillingDetails (
    val address: Address,
    val email: Any? = null,
    val name: Any? = null,
    val phone: Any? = null
)

data class Address (
    val city: Any? = null,
    val country: Any? = null,
    val line1: Any? = null,
    val line2: Any? = null,
    val postalCode: Any? = null,
    val state: Any? = null
)

data class Checks (
    val addressLine1Check: Any? = null,
    val addressPostalCodeCheck: Any? = null,
    val cvcCheck: String
)

data class Networks (
    val available: List<String>,
    val preferred: Any? = null
)

data class ThreeDSecureUsage (
    val supported: Boolean
)

class Metadata()
