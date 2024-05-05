import android.nfc.cardemulation.HostApduService
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.KITKAT)
class MyHostApduService : HostApduService() {

    companion object {
        private const val TAG = "MyHostApduService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "HCE service created")
    }

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        commandApdu?.let {
            // Process incoming APDU command
            val commandString = commandApdu.toHexString() // Convert byte array to string for logging
            Log.d(TAG, "Received APDU command: $commandString")


        }

        // If no specific response, return empty byte array
        return byteArrayOf()
    }

    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "HCE service deactivated, reason: $reason")
    }
}

// Extension function to convert ByteArray to hexadecimal String
fun ByteArray.toHexString(): String {
    return this.joinToString(separator = "") { byte ->
        String.format("%02X", byte)
    }
}
