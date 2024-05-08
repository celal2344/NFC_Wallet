package com.example.nfcwallet

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/get-payment-method-list") // Replace with actual endpoint path
    suspend fun listPaymentMethods(@Field("customerId") id: String): PaymentMethodList

}