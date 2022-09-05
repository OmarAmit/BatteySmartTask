package com.batterysmart.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Country {
    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("cities")
    @Expose
    lateinit var cities: List<String>
}