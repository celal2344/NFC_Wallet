package com.example.nfcwallet

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.*

class DataStoreUtil(context: Context) {
    private val ALLOWED_CHARACTERS = "0123456789ABCDEF"
    private val PREFS_FILENAME = "com.lexycon.hostcardemulation.prefs"
    private var prefs: SharedPreferences? = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    public fun getID(): String {
        var uid = prefs!!.getString("uid", null)
        if (uid == null) {
            uid = this.generateID();
            this.saveID(uid);
        }

        return uid;
    }

    private fun saveID(uid: String) {
        val editor = prefs!!.edit()
        editor.putString("uid", uid)
        editor.apply()
    }

    private fun generateID(sizeOfRandomHexString: Int = 12): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomHexString)
        for (i in 0 until sizeOfRandomHexString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

}
class ByteArrayHexUtil {
    companion object {
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
}