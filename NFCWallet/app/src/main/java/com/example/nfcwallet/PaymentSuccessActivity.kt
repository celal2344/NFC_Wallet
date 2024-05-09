package com.example.nfcwallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityPaymentSuccessBinding

class PaymentSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueButton.setOnClickListener{
            startActivity(Intent(this@PaymentSuccessActivity, MainActivity::class.java))
        }

    }
}
