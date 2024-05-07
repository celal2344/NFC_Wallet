package com.example.nfcreader

import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcreader.databinding.ActivityMainBinding
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import android.widget.TextView
import android.widget.Toast
import com.example.nfcreader.databinding.ActivityPaymentBinding
import kotlinx.coroutines.runBlocking


@RequiresApi(Build.VERSION_CODES.KITKAT)
class PaymentActivity : AppCompatActivity()/*, NfcAdapter.ReaderCallback*/{
    private lateinit var nfcAdapter: NfcAdapter
    private var textView: TextView? = null
    private val TAG = "MainActivity"
    companion object {
        private var BACKEND_URL = "http://192.168.1.24:4242"
    }
    data class PaymentRequest(val customerId: String, val paymentMethodId: String, val paymentAmount: Int)

    interface ApiService {
        @POST("/pay") // Replace with actual endpoint path
        suspend fun pay(@Body paymentRequest: PaymentRequest): PaymentMethodResponse
//        FOR WALLET APP
//        @POST("/create-payment-method") // Replace with actual endpoint path
//        suspend fun createPaymentMethod(@Body customerId: CustomerId): PaymentMethodResponse
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
            // NFC is not available on this device
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        // Check if NFC is enabled
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show()
        }
        //FOR TESTING
        var customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
        var pmId = "pm_1PAA5eAk8ppQVwDwmewCFN2v"
        var paymentAmount = intent.getStringExtra("paymentAmount")
        binding.payAmountText.text = "Payment Amount: $paymentAmount$"
        binding.testButtonPayment.setOnClickListener{//BUTTON YERINE ONTAGDISCOVERED OLCAK
            if (paymentAmount != null) {
                runBlocking { sendPayRequest("cus_Pn4cWVLydHJXj2", "pm_1PAA5eAk8ppQVwDwmewCFN2v",paymentAmount.toInt()) }
            }
        }

    }
//    public override fun onResume() {
//        super.onResume()
//        nfcAdapter?.enableReaderMode(this, this,
//            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
//            null)
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        nfcAdapter?.disableReaderMode(this)
//    }
//    var customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
//    var pmId = "pm_1PAA5eAk8ppQVwDwmewCFN2v"

//    override fun onTagDiscovered(tag: Tag?) {
//        val isoDep = IsoDep.get(tag)
//        isoDep.connect()
//        val response = isoDep.transceive(Utils.hexStringToByteArray(
//            "00A4040007A0000002471011"))
//        var responseStr = response.toString(Charsets.UTF_8)
//        runOnUiThread { binding.textView.append("\nCard Response: "
//                + responseStr) }
//        var custPmIdArr = responseStr.split("+")
//        runBlocking { sendPayRequest(custPmIdArr[0], custPmIdArr[1]) }
//        isoDep.close()
//    }
}
