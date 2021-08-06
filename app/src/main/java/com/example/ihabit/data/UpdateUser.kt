package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class UpdateUser (
    @SerializedName("userId")
    val userId:Int,
    @SerializedName("email")
    val email:String,
    @SerializedName("name")
    val name:String?,
    @SerializedName("password")
    val password:String?,
    @SerializedName("title")
    val title:String?,
    @SerializedName("selfie")
    val selfie:String?,
    @SerializedName("actor")
    val actor:String?,
    @SerializedName("career")
    val career:Int?
        )