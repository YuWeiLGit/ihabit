package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class UpgradeBackFull (

    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val newPropId:UpgradeBack
        )