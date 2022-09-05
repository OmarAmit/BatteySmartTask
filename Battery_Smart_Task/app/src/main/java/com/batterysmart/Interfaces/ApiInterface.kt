package com.batterysmart.Interfaces

import com.batterysmart.Model.DataModel
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("countries")
    fun getProjectList(): Call<DataModel>


}