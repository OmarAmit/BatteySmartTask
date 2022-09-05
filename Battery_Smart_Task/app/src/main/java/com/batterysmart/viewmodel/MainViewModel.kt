package com.batterysmart.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batterysmart.Clients.APIClient
import com.batterysmart.Interfaces.ApiInterface
import com.batterysmart.Model.DataModel
import com.batterysmart.Model.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mutablePostList: MutableLiveData<ArrayList<Country>> = MutableLiveData()
    var dList: ArrayList<Country> = ArrayList()

    fun getProjectList(): LiveData<ArrayList<Country>>? {

        //TODO:  DO this in repository

        var apiServices = APIClient.client.create(ApiInterface::class.java)
        val call = apiServices.getProjectList()

        call.enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                val jsonResponse = response.body()
//                dataList = jsonResponse?.data!!.get(0).news as ArrayList<News>
                dList = jsonResponse?.data!! as  ArrayList<Country>
                mutablePostList.postValue(dList)
            }

            override fun onFailure(call: Call<DataModel>?, t: Throwable?) {
                Log.d("ERROR : ", t.toString())

            }
        })

        return mutablePostList


    }

}