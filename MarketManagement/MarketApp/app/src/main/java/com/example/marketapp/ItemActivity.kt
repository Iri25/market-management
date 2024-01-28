package com.example.marketapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.marketapp.adapters.EntityAdapter
import com.example.marketapp.models.Product
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity() {
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            id = bundle.getInt("MainActId", 0)
            if (id != 0) {
                titleText.setText(bundle.getString("MainActTitle"))
            }
        }

        sendButton.setOnClickListener {
            //val dbManager = DbManager(this)
            val elementAdapter = EntityAdapter(this)

            val values = ContentValues()


            if (id == 0) {
                val item = Product(1,"","")
                item.name = titleText.text.toString()
                item.description = descriptionText.text.toString()
                item.price = priceText.text.toString().toInt()
                item.quantity = quantityText.text.toString().toInt()
                progressBar.visibility = View.VISIBLE


                val handler = Handler()
                handler.postDelayed( {
                    elementAdapter.addElement(item)
                    elementAdapter.refreshElements()
                    progressBar.visibility = View.GONE
                    finish()
                }, 1000)


            } else {
//                val selectionArs = arrayOf(id.toString())
//                val mID = dbManager.update(values, "Id=?", selectionArs)
//                var item = ItemTodo(id,"","now")
//                item.title = editTitle.text.toString()
//                item.description =  editTask.text.toString()
//                val mId = itemTodoAdapter.updateItemTodo(item)
//                itemTodoAdapter.refreshItemTodo()

//                if (mID > 0) {
//                    toast("Added task!")
//                    finish()
//                } else {
//                    toast("Error at add!")
//                }
            }
        }
    }
}
