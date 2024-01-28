package com.example.marketapp.models

import com.google.gson.annotations.SerializedName

data class Product (
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name")
    var name: String = "",
    @field:SerializedName("description")
    var description: String = "",
    @field:SerializedName("status")
    var status: String = "new",
    @field:SerializedName("price")
    var price: Int = 0,
    @field:SerializedName("quantity")
    var quantity :Int = 0)