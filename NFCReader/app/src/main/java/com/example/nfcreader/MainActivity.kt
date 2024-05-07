package com.example.nfcreader

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcreader.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.testPayButton.setOnClickListener{
            if(binding.editPayAmount.text != null){
                val intent = Intent(this, PaymentActivity::class.java)
                var paymentAmount = binding.editPayAmount.text.toString()
                intent.putExtra("paymentAmount", paymentAmount)
                startActivity(intent)
            }

        }
    }
}