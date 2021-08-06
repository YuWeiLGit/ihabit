package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class NodeObj(
    @SerializedName("nodeId")
    val nodeId:Int,
    @SerializedName("hasNode")
    val hasNode:Boolean,
    @SerializedName("lastNode")
    val lastNode:Int?
)
