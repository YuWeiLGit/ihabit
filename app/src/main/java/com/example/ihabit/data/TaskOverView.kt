package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class TaskOverView(

    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("habitName")
    val habitName: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("tags")
    val tags: ArrayList<Tag>,
    @SerializedName("isInform")
    val isInform: Boolean,
    @SerializedName("informTime")
    val infromTime: String,
    @SerializedName("period")
    val period: String,
    @SerializedName("isDone")
    val isDone: Boolean,
    @SerializedName("isClose")
    val isClose: Boolean

)
