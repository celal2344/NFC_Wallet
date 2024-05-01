import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefMessages
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Build
import androidx.annotation.RequiresApi

object NfcUtils {

    fun getPendingIntent(context: Context, adapter: NfcAdapter): PendingIntent? {
        return PendingIntent.getActivity(
            context, 0,
            Intent(context, context.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun createTextMessage(text: String): NdefRecord {
        val payload = text.toByteArray(Charsets.UTF_8)
        val record = NdefRecord.createMime("text/plain", payload)
        return NdefRecord(arrayOf(record))
    }
}
