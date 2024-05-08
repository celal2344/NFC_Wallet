import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nfcwallet.PaymentMethod
import com.example.nfcwallet.R

class CardListAdapter(private val context: Context, private val paymentMethodList: List<PaymentMethod>) :
    RecyclerView.Adapter<CardListAdapter.CardViewHolder>(), ListAdapter {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImageView: ImageView = itemView.findViewById(R.id.card_image)
        val last4DigitsTextView: TextView = itemView.findViewById(R.id.last_4_digits)
        val expMonthTextView: TextView = itemView.findViewById(R.id.exp_month)
        val expYearTextView: TextView = itemView.findViewById(R.id.exp_year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val paymentMethod = paymentMethodList[position]
        val card = paymentMethod.card

        // Set card image based on card brand
        when (card.brand) {
            "VISA" -> holder.cardImageView.setImageResource(R.mipmap.visa)
            "MasterCard" -> holder.cardImageView.setImageResource(R.mipmap.master)
            // Add cases for other card brands if needed
            else -> holder.cardImageView.setImageResource(R.drawable.wallet)
        }

        holder.last4DigitsTextView.text = "**** **** **** ${card.last4}"
        holder.expMonthTextView.text = String.format("%02d", card.expMonth)
        holder.expYearTextView.text = card.expYear.toString().takeLast(2)
    }

    override fun getItemCount(): Int {
        return paymentMethodList.size
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(position: Int): Boolean {
        TODO("Not yet implemented")
    }
}
