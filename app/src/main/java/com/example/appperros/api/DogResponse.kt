package com.example.appperros.api

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var imagenesDogs: List<String>
)