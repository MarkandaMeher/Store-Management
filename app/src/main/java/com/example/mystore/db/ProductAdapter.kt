import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mystore.R
import com.example.mystore.db.Product
import com.example.mystore.db.Users
import com.example.mystore.ownerPage.ProductsActivity

class ProductAdapter(var mCtx : Context, var resource:Int, var items:List<Product>) : ArrayAdapter<Product>(mCtx,resource,items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource,null)

        val product_name: TextView = view.findViewById(R.id.product_name)
        val product_type: TextView = view.findViewById(R.id.product_type)
        val mrp : TextView = view.findViewById(R.id.mrp)
        val id : TextView = view.findViewById(R.id.id)

        val mItem:Product = getItem(position)!!

        product_name.text = mItem.product_name
        product_type.text = mItem.product_type
        mrp.text = mItem.mrp.toString()
        id.text = mItem.id.toString()

        return view
    }

    override fun getCount(): Int {
        return items.count()
    }


}