package com.example.nfcwallet

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

class NFCDialog(var context: Context) {
    private var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public fun showNFCDisabled() {
        dialogBuilder.setMessage(R.string.nfc_disabled)
            .setCancelable(false)

            // positive button text and action
            .setPositiveButton(R.string.dialog_yes, DialogInterface.OnClickListener {
                    dialog, _ -> dialog.dismiss()
                val intent = Intent(Settings.ACTION_NFC_SETTINGS)
                context.startActivity(intent)
            })

            // negative button text and action
            .setNegativeButton(R.string.dialog_no, DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })

        show();
    }


    public fun showNFCUnsupported() {
        dialogBuilder.setMessage(R.string.nfc_unsupported)
            .setCancelable(false)

            // positive button text and action
            .setPositiveButton(R.string.dialog_yes, DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })

        show()
    }

    private fun show() {
        val alert = dialogBuilder.create()
        alert.setTitle("NFC")
        alert.show()
    }
}