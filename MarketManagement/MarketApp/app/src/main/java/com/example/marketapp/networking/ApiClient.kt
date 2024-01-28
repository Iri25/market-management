package com.example.marketapp.networking

import com.example.marketapp.models.Product
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {
    @GET("all") fun getElements(): Observable<List<Product>> // Observable<ProductEmbedded>
    @POST("product") fun addElement(@Body product: Product): Completable
    @DELETE("product/{id}") fun deleteElement(@Path("id") id: Int) : Completable
//    @PUT("product/{id}") fun updateProduct(@Path("id")id: Int, @Body product: Product) : Completable
    @POST("buyProduct") fun buyProduct(@Body product: Product) : Completable
    companion object {

        fun create(): ApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:2024/")
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}
