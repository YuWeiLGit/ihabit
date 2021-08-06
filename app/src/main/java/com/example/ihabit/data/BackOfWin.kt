package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class BackOfWin (

    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:Int
        )