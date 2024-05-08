package com.example.nfcwallet

data class PaymentMethodList (
    val paymentMethodListObject: String,
    val url: String,
    val hasMore: Boolean,
    val data: List<PaymentMethod>
)