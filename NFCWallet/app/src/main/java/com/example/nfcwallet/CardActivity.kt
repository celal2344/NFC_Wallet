package com.example.nfcwallet

import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityCardBinding
import com.example.nfcwallet.databinding.ActivityMainBinding

class CardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardBinding
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_card)

        val dataStore = DataStoreUtil(this)
        val uid = dataStore.getID()
        binding.editText.text = uid

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

