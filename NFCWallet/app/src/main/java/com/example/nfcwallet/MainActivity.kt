package com.example.nfcwallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardsArrayList : ArrayList<Card>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val cardsList = binding.cardsList
        cardsArrayList = ArrayList()
        cardsList.adapter = CardListAdapter(this@MainActivity, cardsArrayList)
        cardsList.setOnItemClickListener {parent, view, position, id ->

            val cardID = cardsArrayList[position]
            val cardName = cardsArrayList[position]
            val cardNumber = cardsArrayList[position]
            val expirationDate = cardsArrayList[position]
            val balance = cardsArrayList[position]

//            var i = Intent(this, CardActivity::class.java)
//            i.putExtra("cardID",cardID)
//            i.putExtra("cardName", cardName)
//            i.putExtra("cardNumber", cardNumber)
//            i.putExtra("expirationDate",expirationDate)
//            i.putExtra("balance", balance)
//            startActivity(i)
        }
        val addCardButton = binding.addCardBtn
        addCardButton.setOnClickListener{
            var i2 = Intent(this, AddCardActivity::class.java)
            startActivity(i2)
        }
        
    }

}
