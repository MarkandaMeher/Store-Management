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

class LimitAdapter(var mCtx : Context, var resource:Int, var items:List<Quantity>) : ArrayAdapter<Quantity>(mCtx,resource,items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource,null)

        val product_name: TextView = view.findViewById(R.id.name_product)
        val product_quan: TextView = view.findViewById(R.id.product_quantity)


        val mItem:Quantity = getItem(position)!!

        product_name.text = mItem.name
        product_quan.text = mItem.total_quan.toString()

        return view
    }

    override fun getCount(): Int {
        return items.count()
    }


}