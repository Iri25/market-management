package com.example.marketapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.marketapp.adapters.EntityAdapter
import kotlinx.android.synthetic.main.activity_clerk.*
import kotlinx.android.synthetic.main.content_clerk.*

class ClerkActivity : AppCompatActivity() {
    lateinit var adapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clerk)
        progressBarClerk.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = EntityAdapter(this@ClerkActivity)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            listView1.layoutManager = layoutManager
            listView1.adapter = adapter
            progressBarClerk.visibility = View.GONE
        }, 1000)


        fab.setOnClickListener {
            startActivityForResult(Intent(this@ClerkActivity, ItemActivity::class.java), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        progressBarClerk.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = EntityAdapter(this)
            adapter.refreshElements()
            listView1.adapter = adapter

            if (listView1.adapter != null) {
                listView1.adapter!!.notifyDataSetChanged()
            }
            progressBarClerk.visibility = View.GONE
        }, 1000)
    }

}
