package com.example.nfcwallet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.nfcwallet.data.PaymentMethod
import com.example.nfcwallet.data.PaymentMethodList
import com.example.nfcwallet.databinding.ActivityAddcardBinding
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddcardBinding
    private lateinit var newPaymentMethod :PaymentMethod

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monthsAd: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, (1..12).toList().toTypedArray())
        monthsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addCardMonth.adapter = monthsAd

        val yearsAd: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, (LocalDate.now().year ..LocalDate.now().year+15).toList().toTypedArray())
        yearsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addCardYear.adapter = yearsAd

        binding.saveBtn.setOnClickListener {
            val cardNumber = binding.addCardNumber.text.toString()
            val expMonth = (binding.addCardMonth.adapter.getItem(binding.addCardMonth.selectedItemPosition) as Int).toLong()
            val expYear = (binding.addCardYear.adapter.getItem(binding.addCardYear.selectedItemPosition) as Int).toLong()
            val cvc = binding.addCardCvv.text.toString()

            val customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
            runBlocking {
                try {
                    newPaymentMethod = createPaymentMethodRequest(customerId,cardNumber,expMonth,expYear,cvc)!!
                    println(newPaymentMethod.toString())
                }catch (e: Exception){
                    println(e.message)
                }
            }
            if(newPaymentMethod != null){
                var mainIntent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "Card successfully added", Toast.LENGTH_SHORT).show()
                startActivity(mainIntent)
            }else{
                println("error")
            }
        }
    }

    private suspend fun createPaymentMethodRequest(customerId:String, cardNumber:String,expMonth:Long,expYear:Long,cvc:String): PaymentMethod? {
        val apiService = ApiService.retrofit.create(ApiService::class.java)
        try {
            val response = apiService.createPaymentMethod(customerId,cardNumber,expMonth,expYear,cvc)
            println(response.toString())
            return response
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        var mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}