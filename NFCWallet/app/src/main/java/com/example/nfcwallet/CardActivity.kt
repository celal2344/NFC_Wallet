package com.example.nfcwallet

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityCardBinding
import com.example.nfcwallet.ByteArrayHexUtil

class CardActivity : AppCompatActivity(){
    private lateinit var binding: ActivityCardBinding
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_card)

//        var customerId = "cus_Pn4cWVLydHJXj2"//celal özdemir
//        var pmId = "pm_1PAA5eAk8ppQVwDwmewCFN2v"

        val dataStore = DataStoreUtil(this)
        val uid = dataStore.getID()
        binding.textView.text = uid

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

