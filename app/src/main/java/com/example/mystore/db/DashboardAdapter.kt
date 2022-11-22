import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mystore.R
import com.example.mystore.db.Product
import com.example.mystore.db.Quantity
import com.example.mystore.db.Users
import com.example.mystore.ownerPage.ProductsActivity

class DashboardAdapter(var mCtx : Context, var resource:Int, var items:List<Quantity>) : ArrayAdapter<Quantity>(mCtx,resource,items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource,null)

        val product_name: TextView = view.findViewById(R.id.name_product)
        val quantity : TextView = view.findViewById(R.id.product_quantity)
        val InOut : ImageView = view.findViewById(R.id.pic)
        val upDown : ImageView = view.findViewById(R.id.updown)

        val mItem:Quantity = getItem(position)!!

        product_name.text = mItem.name
        quantity.text = mItem.quan_in_out.toString()
        InOut.setImageResource(R.drawable.img)
        if(mItem.in_out.equals("IN")){
            upDown.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }
        else{
            upDown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }

        return view
    }

    override fun getCount(): Int {
        return items.count()
    }


}