package com.example.nfcwallet

import android.content.Context
import android.content.Intent
import android.nfc.cardemulation.HostApduService
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.adapters.CardViewBindingAdapter

@RequiresApi(Build.VERSION_CODES.KITKAT)
class HostCardEmulatorService: HostApduService() {

    companion object {
        val TAG = "Host Card Emulator"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "A0000002471001"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }


    override fun onDeactivated(reason: Int) {
    }
    private var inputData: String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("input_data")) {
            inputData = intent.getStringExtra("input_data")
            println(inputData)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun processCommandApdu(commandApdu: ByteArray?,
                                    extras: Bundle?): ByteArray {

        Log.d(TAG, "APDU process command")

        if (commandApdu == null) {
            return ByteArrayHexUtil.hexStringToByteArray(STATUS_FAILED)
        }

        val hexCommandApdu = commandApdu.toHexString()
        println(hexCommandApdu)
        var response = inputData
        if (response != null){
            return response.toByteArray()
        }else{
            return "No input data received".toByteArray()
        }

    }
}