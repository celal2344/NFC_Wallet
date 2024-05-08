package com.example.nfcwallet

import CardListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardsList : List<PaymentMethod>
    companion object {
        private var BACKEND_URL = "http://192.168.1.24:4242"
    }

    private suspend fun sendPaymentMethodListRequest(customerId: String):PaymentMethodList {
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        try {
            val response = apiService.listPaymentMethods(customerId)
            println(response)
            return response
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
        return PaymentMethodList("","",false, emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val cardsListView = binding.cardsList
        val customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
        runBlocking {
            cardsList = sendPaymentMethodListRequest(customerId).data
            println(cardsList.toString())
        }
        cardsListView.adapter = CardListAdapter(this@MainActivity, cardsList)
//        cardsListView.setOnItemClickListener {parent, view, position, id ->

//            val cardID = cardsList[position]
//            val cardName = cardsList[position]
//            val cardNumber = cardsList[position]
//            val expirationDate = cardsList[position]
//            val balance = cardsList[position]

//            var i = Intent(this, CardActivity::class.java)
//            i.putExtra("cardID",cardID)
//            i.putExtra("cardName", cardName)
//            i.putExtra("cardNumber", cardNumber)
//            i.putExtra("expirationDate",expirationDate)
//            i.putExtra("balance", balance)
//            startActivity(i)
//        }
//        val addCardButton = binding.addCardBtn
//        addCardButton.setOnClickListener{
//            var i2 = Intent(this, AddCardActivity::class.java)
//            startActivity(i2)
//        }

    }

}
