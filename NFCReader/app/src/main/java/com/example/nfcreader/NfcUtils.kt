package com.example.nfcreader

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast

object NfcUtils {

    fun getPendingIntent(context: Context, adapter: NfcAdapter): PendingIntent? {
        return PendingIntent.getActivity(
            context, 0,
            Intent(context, context.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )
    }

    fun readNfcTag(ndef: Ndef?): String {
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return "NDEF is not supported by this Tag"
        }

        // Read NDEF message from the tag
        try {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            if (ndefMessage != null && ndefMessage.records.isNotEmpty()) {
                // Assuming only one NDEF record in the message
                val record = ndefMessage.records[0]
                val payload = record.payload
                // Convert payload to string
                return String(payload, Charsets.UTF_8)
            } else {
                return "No NDEF records found on the tag"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error reading NFC tag: ${e.message}"
        } finally {
            ndef.close()
        }
    }
}
