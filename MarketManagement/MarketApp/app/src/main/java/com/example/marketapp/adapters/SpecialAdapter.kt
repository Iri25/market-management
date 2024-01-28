package com.example.marketapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marketapp.R
import com.example.marketapp.local_db.DbManager
import com.example.marketapp.models.Product
import com.example.marketapp.networking.ApiClient
import kotlinx.android.synthetic.main.special_view.view.*

class SpecialAdapter(val context: Context) :
    RecyclerView.Adapter<SpecialAdapter.SpecialViewAdapter>() {

    private val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Product> = ArrayList()
    private var dbManager: DbManager

    init {
        dbManager = DbManager(context)
        loadQueryAll()
    }

    private fun loadQueryAll() {
        val cursor = dbManager.queryAll()
        elementsList.clear()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val status = cursor.getString(cursor.getColumnIndex("status"))
                val quantity = cursor.getString(cursor.getColumnIndex("quantity"))
                val price = cursor.getString(cursor.getColumnIndex("price"))
                elementsList.add(Product(id = id, name = title, description = description, status = status, quantity = quantity.toInt(), price = price.toInt()))
            } while (cursor.moveToNext())
        }
    }



    class SpecialViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialAdapter.SpecialViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.special_view, parent, false)

        return SpecialViewAdapter(view)
    }

    override fun onBindViewHolder(holder: SpecialViewAdapter, position: Int) {

        holder.view.title.text = elementsList[position].name
        holder.view.status.text = elementsList[position].status
        holder.view.quantity.text = elementsList[position].quantity.toString()
        holder.view.price.text = elementsList[position].price.toString()
    }

    override fun getItemCount() = elementsList.size

}