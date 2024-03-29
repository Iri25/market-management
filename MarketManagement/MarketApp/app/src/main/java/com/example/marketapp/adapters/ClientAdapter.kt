package com.example.marketapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.marketapp.EntityDetailActivity
import com.example.marketapp.R
import com.example.marketapp.local_db.DbManager
import com.example.marketapp.models.Product
import com.example.marketapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_client_view.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class ClientAdapter(val context: Context) :
    RecyclerView.Adapter<ClientAdapter.ElementViewAdapter>() {

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Product> = ArrayList()
    private val dbManager = DbManager(context)

    init {
        refreshElements()
    }


    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientAdapter.ElementViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client_view, parent, false)

        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        holder.view.title.text = elementsList[position].name

        holder.view.btnFav.setOnClickListener {
            Log.d("element", elementsList[position].toString())
            buyProduct(elementsList[position])


            val aux = Intent(context, EntityDetailActivity::class.java)
            aux.putExtra("description", elementsList[position].description)
            aux.putExtra("album", elementsList[position].status)
            aux.putExtra("genre", elementsList[position].quantity.toString())
            aux.putExtra("year", elementsList[position].price.toString())
            aux.putExtra("title", elementsList[position].name)
            aux.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(aux)
        }
    }

    @SuppressLint("CheckResult")
    fun buyProduct(product: Product)
    {

        client.buyProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ refreshElements() }, { throwable ->
                Toast.makeText(context, "Delete error: ${throwable.message}", Toast.LENGTH_LONG).show()
            }
            )
        val values = ContentValues()
        values.put("title", product.name)
        values.put("quantity", product.quantity.toString().toInt())
        values.put("price", product.price.toString().toInt())
        values.put("status", product.status)
        values.put("description", product.description)

        dbManager.insert(values);
        Toast.makeText(context, "Added to database: ${product.name}", Toast.LENGTH_LONG).show()

    }

    override fun getItemCount() = elementsList.size

    @SuppressLint("CheckResult")
    fun refreshElements() {
        if (checkOnline()) {
            client.getElements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        elementsList.clear()
                        elementsList.addAll(result)
                        notifyDataSetChanged()
                        Log.d("Elements -> ", elementsList.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${JSONObject(body.string()).getString("text")}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false

    }

}