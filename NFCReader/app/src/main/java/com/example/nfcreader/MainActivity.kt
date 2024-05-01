package com.example.nfcreader

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
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
import android.nfc.tech.Ndef
import android.widget.TextView
import android.widget.Toast


@RequiresApi(Build.VERSION_CODES.KITKAT)
class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback  {
    private lateinit var nfcAdapter: NfcAdapter
    private var textView: TextView? = null
    private val TAG = "MainActivity"
    companion object {
        private const val BACKEND_URL = "http://192.168.1.24:4242"
    }

    interface ApiService {
        @POST("/pay") // Replace with actual endpoint path
        suspend fun pay(@Body paymentRequest: PaymentRequest): PaymentMethodResponse
//        FOR WALLET APP
//        @POST("/create-payment-method") // Replace with actual endpoint path
//        suspend fun createPaymentMethod(@Body customerId: CustomerId): PaymentMethodResponse
    }

    private suspend fun sendRequest(customerId: String,pmId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        try {
            val response = apiService.pay(PaymentRequest(customerId, pmId))
            println(response)
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
        }
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

//        var customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
//        var pmId = "pm_1PAA5eAk8ppQVwDwmewCFN2v"
//        binding.button.setOnClickListener {
//            println("sa")
//            runBlocking { sendRequest(customerId, pmId) }
//        }
        if (nfcAdapter == null) {
            // NFC is not available on this device
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize TextView
        textView = findViewById(R.id.textView)

        // Check if NFC is enabled
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Enable NFC foreground dispatch
        nfcAdapter?.enableForegroundDispatch(this, nfcAdapter?.let { NfcUtils.getPendingIntent(this, it) }, null, null)
    }

    override fun onPause() {
        super.onPause()
        // Disable NFC foreground dispatch
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TECH_DISCOVERED == action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(tag)
            val text = NfcUtils.readNfcTag(ndef)
            textView?.text = text
        }
    }
    override fun onTagDiscovered(tag: Tag?) {
        //WHEN NFC DATA RECEIVED SEND THE DATA THROUGH PAYMENTMETHOD PARAMS
//        runCatching {
//            stripe.createPaymentMethod(paymentMethodParams).id
//        }.fold(
//            onSuccess = { // paymentMethodId
//                // Send the ID of the PaymentMethod to your server.
//            },
//            onFailure = {
//                // Display the error to the customer.
//            }
//        )
//    }
//        println("onTagDiscovered")
//        val isoDep = IsoDep.get(tag)
//        isoDep.connect()
//
//        // Create Parser
//        val parser = EmvTemplate.Builder()
//            .setProvider(IsoDepProvider(isoDep)) // Define provider
//            .setConfig(config) // Define config
//            .build()
//
//        // Read card
//        val emvCard = parser.readEmvCard()
//
//        isoDep.close()
//        println("card detected $emvCard")

    }
}

data class PaymentRequest(val customerId: String, val paymentMethodId: String)