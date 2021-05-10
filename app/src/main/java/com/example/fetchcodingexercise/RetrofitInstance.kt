package com.example.fetchcodingexercise

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
    This class handles converting the data from JSON to an instance of the ItemModel data class.
 */
class RetrofitInstance {
    companion object {
        val URL = "https://fetch-hiring.s3.amazonaws.com/"

        fun getInstance () =
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}