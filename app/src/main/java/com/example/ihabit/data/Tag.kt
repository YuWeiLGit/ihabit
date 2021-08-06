package com.example.ihabit.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Tag (

    @SerializedName("tagName")
    val tagName:String,
    @SerializedName("tagId")
    val tagId:Int

)