package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class TaskForUpdate (
    @SerializedName("habitId")
    val habit: Int,
    @SerializedName("userId")
    val user: Int,
    @SerializedName("habitName")
    val habitName: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("period")
    val period: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("isHide")
    val isHide: Boolean?,
    @SerializedName("isInform")
    val isInform: Boolean?,
    @SerializedName("informTime")
    val InformTime: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("isClose")
    val isClose: Boolean?,
    @SerializedName("isSocailized")
    val isSocailized: Boolean?

        )
