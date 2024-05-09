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
                    val response = createPaymentMethodRequest(customerId,cardNumber,expMonth,expYear,cvc)
                    println(response.toString())
                }catch (e: Exception){
                    println(e.message)
                }
            }
        }
    }

    private suspend fun createPaymentMethodRequest(customerId:String, cardNumber:String,expMonth:Long,expYear:Long,cvc:String){
        val apiService = ApiService.retrofit.create(ApiService::class.java)
        try {
            val response = apiService.createPaymentMethod(ApiService.CreatePaymentMethodRequest(customerId,cardNumber,expMonth,expYear,cvc))
            Toast.makeText(this, "Card added", Toast.LENGTH_SHORT).show()
            println(response.toString())
        } catch (e: Exception) {
            Toast.makeText(this, "Error while adding card", Toast.LENGTH_SHORT).show()
            println("Error occurred: ${e.message}")
        }
        val i = Intent(this@AddCardActivity, MainActivity::class.java)
        startActivity(i)
    }

}