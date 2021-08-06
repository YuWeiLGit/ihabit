package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("money")
    val money:Int,
    @SerializedName("userProps")
    val allItems:MutableList<gear>
)
