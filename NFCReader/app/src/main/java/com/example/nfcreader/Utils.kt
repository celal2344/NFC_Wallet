package com.example.nfcreader

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef





object Utils {
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
    private val HEX_CHARS = "0123456789ABCDEF"
    fun hexStringToByteArray(data: String) : ByteArray {

        val result = ByteArray(data.length / 2)

        for (i in 0 until data.length step 2) {
            val firstIndex = HEX_CHARS.indexOf(data[i]);
            val secondIndex = HEX_CHARS.indexOf(data[i + 1]);

            val octet = firstIndex.shl(4).or(secondIndex)
            result.set(i.shr(1), octet.toByte())
        }

        return result
    }

    private val HEX_CHARS_ARRAY = "0123456789ABCDEF".toCharArray()
    fun toHex(byteArray: ByteArray) : String {
        val result = StringBuffer()

        byteArray.forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(HEX_CHARS_ARRAY[firstIndex])
            result.append(HEX_CHARS_ARRAY[secondIndex])
        }

        return result.toString()
    }
}
