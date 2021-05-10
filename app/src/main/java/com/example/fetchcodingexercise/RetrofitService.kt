package com.example.fetchcodingexercise

import io.reactivex.Observable
import retrofit2.http.GET

/*
    I used Retrofit for pulling the data from the internet, this speci
 */
interface RetrofitService {
    @GET("hiring.json")
    fun getItems() : Observable<List<ItemModel>>
}