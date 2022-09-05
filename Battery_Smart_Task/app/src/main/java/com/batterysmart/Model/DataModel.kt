package com.batterysmart.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataModel {
    @Expose
    private val statusMessage: String? = null

    @SerializedName("data")
    @Expose
    var data: List<Country>? = null
}