package com.example.marketapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_entity_detail.*

class EntityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entity_detail)
        titleDetail.text = intent.getStringExtra("title")
        descriptionDetail.text = intent.getStringExtra("description")
        genreDetail.text = intent.getStringExtra("genre")
        albumDetail.text = intent.getStringExtra("album")
        yearDetail.text = intent.getStringExtra("year")


        favBtn.setOnClickListener { finish() }
    }
}
