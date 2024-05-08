package com.example.nfcwallet.data

data class PaymentMethodList(
    val `data`: List<PaymentMethod>,
    val has_more: Boolean,
    val `object`: String,
    val url: String
)