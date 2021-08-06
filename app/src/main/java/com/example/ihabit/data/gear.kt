package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class gear(
    @SerializedName("propId")
    val prodId:Int,
    @SerializedName("propName")
    val propName:String?,
    @SerializedName("type")
    val type:Int,
    @SerializedName("icon")
    val icon:String,
    @SerializedName("buildPrice")
    val buildPrice:Int,
    @SerializedName("upLevelPrice")
    val upLevelPrice:Int,
    @SerializedName("multipleType")
    val multipleType:Int,
    @SerializedName("level")
    val level:Int,
    @SerializedName("propData")
    val propData:Int
)
