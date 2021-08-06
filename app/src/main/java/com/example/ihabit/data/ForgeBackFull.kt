package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class ForgeBackFull(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ForgeBack
)
