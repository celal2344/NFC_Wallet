package com.example.nfcwallet

import com.example.nfcwallet.data.PaymentMethod
import com.example.nfcwallet.data.PaymentMethodList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.time.Year

interface ApiService {
    companion object {
        private var BACKEND_URL = "http://192.168.151.200:4242"
        public val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @FormUrlEncoded
    @POST("/get-payment-method-list") // Replace with actual endpoint path
    suspend fun listPaymentMethods(@Field("customerId") id: String): PaymentMethodList

    @FormUrlEncoded
    @POST("/create-payment-method") // Replace with actual endpoint path
    suspend fun createPaymentMethod(@Field("customerId") customerId: String,
                                    @Field("cardNumber") cardNumber: String,
                                    @Field("expMonth") expMonth: Long,
                                    @Field("expYear") expYear: Long,
                                    @Field("cvc") cvc: String): PaymentMethod
    //cardNumber,expMonth,expYear,cvc

}