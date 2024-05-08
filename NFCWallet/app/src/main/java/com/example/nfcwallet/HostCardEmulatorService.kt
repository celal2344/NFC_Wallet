package com.example.nfcwallet

import android.content.Intent
import android.nfc.cardemulation.HostApduService
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.KITKAT)
class HostCardEmulatorService: HostApduService() {
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
        if (commandApdu == null) {
            return ("STATUS_FAILED").toByteArray()
        }
        val hexCommandApdu = commandApdu.toHexString()
        println(hexCommandApdu)
        var response = inputData
        if (response != null){
            val i = Intent(this@HostCardEmulatorService, MainActivity::class.java)
            startActivity(i)
            return response.toByteArray()
        }else{
            return "No input data received".toByteArray()
        }
    }
}