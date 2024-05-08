package com.example.nfcreader

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcreader.databinding.ActivityPaymentBinding
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


@RequiresApi(Build.VERSION_CODES.KITKAT)
class PaymentActivity : AppCompatActivity(), NfcAdapter.ReaderCallback{
    private lateinit var nfcAdapter: NfcAdapter
    companion object {
        private var BACKEND_URL = "http://192.168.1.24:4242"
    }
    data class PaymentRequest(val customerId: String, val paymentMethodId: String, val paymentAmount: Int)
    interface ApiService {
        @POST("/pay")
        suspend fun pay(@Body paymentRequest: PaymentRequest): PaymentMethodResponse
    }
    private suspend fun sendPayRequest(customerId: String, pmId: String,paymentAmount: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        try {
            val response = apiService.pay(PaymentRequest(customerId, pmId, paymentAmount))
            println(response)
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
    }
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show()
        }
    }
    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)
    }
    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }
    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        this.runOnUiThread(Runnable {
            Toast.makeText(this, "Please wait", Toast.LENGTH_LONG).show()
        })
        isoDep.timeout = 3000
        val response = isoDep.transceive(Utils.hexStringToByteArray(
            "00A4040007A0000002471011"))
        var responseStr = response.toString(Charsets.UTF_8)
        var custPmIdArr = responseStr.split("+")
        var paymentAmount = intent.getStringExtra("paymentAmount")
        println(custPmIdArr)
        runBlocking {
            try {
                if(paymentAmount != null){
                    val i = Intent(this@PaymentActivity, MainActivity::class.java)
                    startActivity(i)
                    sendPayRequest(custPmIdArr[0], custPmIdArr[1],paymentAmount.toInt())
                }
            }catch(e:Exception){
                val i = Intent(this@PaymentActivity, MainActivity::class.java)
                startActivity(i)
                println(e)
            }
        }
        isoDep.close()
    }
}
