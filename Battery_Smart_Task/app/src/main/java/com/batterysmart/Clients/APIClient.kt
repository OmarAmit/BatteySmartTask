package com.batterysmart.Clients

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {


    companion object {

        val baseURL: String = "https://countriesnow.space/api/v0.1/"
        var retofit: Retrofit? = null
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val client: Retrofit
            get() {
                if (retofit == null) {
                    retofit = Retrofit.Builder()
                        .baseUrl(baseURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retofit!!
            }
    }
}