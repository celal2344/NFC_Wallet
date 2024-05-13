package com.example.nfcwallet

import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.nfcwallet.data.PaymentMethod
import com.example.nfcwallet.data.PaymentMethodList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Enumeration
import java.util.Formatter


interface ApiService {
    data class CreatePaymentMethodRequest(val customerId: String,val encryptedData: String)
    @FormUrlEncoded
    @POST("/get-payment-method-list") // Replace with actual endpoint path
    suspend fun listPaymentMethods(@Field("customerId") id: String): PaymentMethodList

    @POST("/create-payment-method") // Replace with actual endpoint path
    suspend fun createPaymentMethod(@Body createPaymentMethodRequest: CreatePaymentMethodRequest): PaymentMethod
    //cardNumber,expMonth,expYear,cvc

}