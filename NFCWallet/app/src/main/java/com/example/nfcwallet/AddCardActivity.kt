package com.example.nfcwallet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.Utils
import com.example.nfcwallet.databinding.ActivityAddcardBinding
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


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

        val yearsAd: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, (LocalDate.now().year + 1 ..LocalDate.now().year+15).toList().toTypedArray())
        yearsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addCardYear.adapter = yearsAd

        binding.saveBtn.setOnClickListener {
            val cardNumber = binding.addCardNumber.text.toString()
            val expMonth = (binding.addCardMonth.adapter.getItem(binding.addCardMonth.selectedItemPosition) as Int).toLong()
            val expYear = (binding.addCardYear.adapter.getItem(binding.addCardYear.selectedItemPosition) as Int).toLong()
            val cvc = binding.addCardCvv.text.toString()
            val customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
            if(cardNumber != "" && cvc != ""){
                runBlocking {
                    try {
                        val response = createPaymentMethodRequest(customerId,cardNumber,expMonth,expYear,cvc)
                        println(response.toString())
                    }catch (e: Exception){
                        println(e.message)
                    }
                }
            }else{
                Toast.makeText(this,"Missing information",Toast.LENGTH_SHORT).show()
            }
        }
    }
    val key = "eb505f2b2b1968889e44fa342fd6c912".toByteArray()
    @SuppressLint("NewApi")
    fun encryptCardDetails(inputList: List<String>): String{
        val inputText = inputList.joinToString(" ")
        val algorithm = "AES/CBC/PKCS5Padding"
        val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
        val iv = IvParameterSpec(ByteArray(16))
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }


    private suspend fun createPaymentMethodRequest(customerId:String, cardNumber:String,expMonth:Long,expYear:Long,cvc:String){
        val apiService = Utils.retrofit.create(ApiService::class.java)
        try {
            val response = apiService.createPaymentMethod(ApiService.CreatePaymentMethodRequest(customerId,encryptCardDetails(listOf<String>(cardNumber,expMonth.toString(),expYear.toString(),cvc))))
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