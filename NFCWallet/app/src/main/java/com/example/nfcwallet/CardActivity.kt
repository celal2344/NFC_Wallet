package com.example.nfcwallet

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityCardBinding

class CardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardBinding
    private var nfcAdapter: NfcAdapter? = null
    private var writeButton: Button? = null
    private var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            // NFC is not available on this device
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize UI components
        writeButton = findViewById(R.id.writeButton)
        editText = findViewById(R.id.editText)

        // Set click listener for write button
        writeButton?.setOnClickListener {
            val textToWrite = editText?.text.toString()
            if (textToWrite.isNotEmpty()) {
                writeNfcTag(textToWrite)
            } else {
                Toast.makeText(this, "Please enter text to write", Toast.LENGTH_SHORT).show()
            }
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
        // No need to handle NFC intent in writer app
    }

    private fun writeNfcTag(text: String) {
        // Get the Tag object from the intent
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            // Write the text to the NFC tag
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                try {
                    ndef.connect()
                    val ndefMessage = NdefMessage.createText(text)
                    ndef.writeNdefMessage(ndefMessage)
                    Toast.makeText(this, "Text written to NFC tag successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error writing to NFC tag: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    ndef.close()
                }
            } else {
                Toast.makeText(this, "NDEF is not supported by this Tag", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to retrieve NFC tag", Toast.LENGTH_SHORT).show()
        }
    }
}
