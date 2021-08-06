package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class TaskForReturn (
    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("habitName")
    val habitName: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("period")
    val period: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("isHide")
    val isHide: Boolean,
    @SerializedName("isInform")
    val isInform: Boolean,
    @SerializedName("informTime")
    val InformTime: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("isClose")
    val isClose: Boolean,
    @SerializedName("isSocailized")
    val isSocailized: Boolean,
    @SerializedName("tags")
    val tags: ArrayList<Tag>,
    @SerializedName("continueDays")
    val continueDays: Int,
    @SerializedName("completeDaysOfMonth")
    val completeDaysOfMonth: Int
        )





