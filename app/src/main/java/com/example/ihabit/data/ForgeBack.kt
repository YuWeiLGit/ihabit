package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class ForgeBack (
    @SerializedName("propData")
    val propData:Int,
    @SerializedName("buildData")
    val buildData:Int,
    @SerializedName("money")
    val money:Int

        )