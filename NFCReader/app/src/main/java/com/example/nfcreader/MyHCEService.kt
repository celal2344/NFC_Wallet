package com.example.nfcreader

import android.nfc.cardemulation.HostApduService
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.KITKAT)
class MyHCEService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu != null) {
            val command = String(commandApdu)
            Log.d(TAG, "Received APDU Command: $command")

            // Extract data from APDU command and process it
            val data = extractDataFromAPDU(commandApdu)
            processData(data)
        }
        // Return response if needed
        return byteArrayOf()
    }

    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "HCE Deactivated: $reason")
    }

    private fun extractDataFromAPDU(apdu: ByteArray): String {
        // Implement logic to extract data from APDU command
        // For example, parse APDU header and extract data bytes
        return "Extracted data"
    }

    private fun processData(data: String) {
        // Implement logic to process the extracted data
        Log.d(TAG, "Processed Data: $data")
    }

    companion object {
        private const val TAG = "MyHCEService"
    }
}