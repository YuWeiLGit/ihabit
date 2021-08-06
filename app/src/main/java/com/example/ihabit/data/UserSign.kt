package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class UserSign(
    @SerializedName("name")
    val name:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("career")
    val career:Int

)
