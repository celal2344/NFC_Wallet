package com.example.nfcwallet

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/get-payment-method-list") // Replace with actual endpoint path
    suspend fun listPaymentMethods(@Body customerId:String): PaymentMethodList

}