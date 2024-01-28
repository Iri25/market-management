package com.example.marketapp.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.marketapp.R
import com.example.marketapp.models.Product
import com.example.marketapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_clerk_view.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException



class EntityAdapter(val context: Context) :
    RecyclerView.Adapter<EntityAdapter.ElementViewAdapter>() {

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Product> = ArrayList()

    init {
        refreshElements()
    }


    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityAdapter.ElementViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clerk_view, parent, false)

        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {

        holder.view.title.text = elementsList[position].name
        holder.view.status.text = elementsList[position].status
        holder.view.price.text = elementsList[position].price.toString()
        holder.view.quantity.text = elementsList[position].quantity.toString()
        holder.view.btnDelete.setOnClickListener { showDeleteDialog(holder, elementsList[position]) }
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
                        elementsList.sortWith(compareBy {it.quantity})
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

    @SuppressLint("CheckResult")
    fun addElement(element: Product) {
        if (checkOnline()) {
            client.addElement(element)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element added -> ", element.toString())
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

    @SuppressLint("CheckResult")
    private fun deleteElement(element: Product) {
        if (checkOnline()) {
            client.deleteElement(element.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element deleted -> ", element.toString())
                    },
                    { throwable ->
                        Toast.makeText(context, "Delete error: ${throwable.message}", Toast.LENGTH_LONG).show()
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }

    }

    private fun showDeleteDialog(holder: ElementViewAdapter, element: Product) {
        val dialogBuilder = AlertDialog.Builder(holder.view.context)
        dialogBuilder.setTitle("Delete")
        dialogBuilder.setMessage("Confirm delete?")
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            deleteElement(element)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val b = dialogBuilder.create()
        b.show()
    }
}