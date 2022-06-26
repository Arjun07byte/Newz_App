package com.example.newzapp.api

import com.example.newzapp.utils.Constants.Companion.NEWS_BASE_URL
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofitInst by lazy {
            val loggingVar = HttpLoggingInterceptor()
            loggingVar.setLevel(HttpLoggingInterceptor.Level.BODY)
            val clientVar = okhttp3.OkHttpClient.Builder()
                .addInterceptor(loggingVar)
                .build()

            Retrofit.Builder()
                .baseUrl(NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientVar)
                .build()
        }

        val apiVar: NewsAPI by lazy {
            retrofitInst.create(NewsAPI::class.java)
        }
    }
}