package com.example.nfcwallet

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityAddcardBinding
import java.time.LocalDate

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val monthsAd: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, (1..12).toList().toTypedArray())
        monthsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addCardMonth.adapter = monthsAd
        val yearsAd: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, (LocalDate.now().year ..LocalDate.now().year+15).toList().toTypedArray())
        yearsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addCardYear.adapter = yearsAd
    }
}