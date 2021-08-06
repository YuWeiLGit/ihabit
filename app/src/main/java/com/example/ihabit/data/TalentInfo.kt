package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class TalentInfo(
    @SerializedName("talentPoint")
    val talentPoint: Int,
    @SerializedName("nodes")
    val nodes: MutableList<NodeObj>
)