package com.example.ihabit.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize


data class Task(
    @SerializedName("userId")
    val userId: Int,
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
    val tags: ArrayList<Int>,
    @SerializedName("continueDays")
    val continueDays: Int,
    @SerializedName("completeDaysOfMonth")
    val completeDaysOfMonth: Int



)



