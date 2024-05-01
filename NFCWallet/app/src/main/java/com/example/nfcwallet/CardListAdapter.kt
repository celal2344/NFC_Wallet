package com.example.nfcwallet

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CardListAdapter(private val context: Activity, private val arrayList: ArrayList<Card>): ArrayAdapter<Card>(context, R.layout.list_item, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item,parent,false)
        val cardImage: ImageView = view.findViewById(R.id.card_image)
        val cardName : TextView = view.findViewById(R.id.card_name)
        val cardNumber: TextView = view.findViewById(R.id.card_number)
        val cardBalance: TextView = view.findViewById(R.id.card_balance)



        return view
    }
}