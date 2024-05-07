package com.example.nfcwallet

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityCardBinding


class CardActivity : AppCompatActivity(){
    private lateinit var binding: ActivityCardBinding
    private var nfcAdapter: NfcAdapter? = null
    public var myEditText: EditText? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
        var pmId = "pm_1PAA5eAk8ppQVwDwmewCFN2v"

        val intent = Intent(this@CardActivity, HostCardEmulatorService::class.java)
        val inputData = customerId + "+" + pmId
        binding.textView.text = "Data to send: " + inputData
        intent.putExtra("input_data", inputData)
        startService(intent)


        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled) {
                NFCDialog(this).showNFCDisabled()
            }
        } else {
            NFCDialog(this).showNFCUnsupported()
        }

    }

}

