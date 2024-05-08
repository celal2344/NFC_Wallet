import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.nfcwallet.R
import com.example.nfcwallet.data.PaymentMethod

class CardListAdapter(private val context: Context, private val paymentMethods: List<PaymentMethod>) : BaseAdapter() {

    override fun getCount(): Int {
        return paymentMethods.size
    }

    override fun getItem(position: Int): PaymentMethod {
        return paymentMethods[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val paymentMethod = getItem(position)

        // Bind data to views
        val last4DigitsTextView = listItemView?.findViewById<TextView>(R.id.last_4_digits)
        val expMonthTextView = listItemView?.findViewById<TextView>(R.id.exp_month)
        val expYearTextView = listItemView?.findViewById<TextView>(R.id.exp_year)
        val cardImageView = listItemView?.findViewById<ImageView>(R.id.card_image)

        val imageResource = when (paymentMethod.card.brand) {
            "visa" -> R.mipmap.visa
            "mastercard" -> R.mipmap.master
            else -> R.drawable.wallet
        }

        cardImageView?.setImageResource(imageResource)
        last4DigitsTextView?.text = "Last 4 Digits: ${paymentMethod.card.last4}"
        expMonthTextView?.text = "Exp Month: ${paymentMethod.card.exp_month}"
        expYearTextView?.text = "Exp Year: ${paymentMethod.card.exp_year}"

        return listItemView!!
    }
}
