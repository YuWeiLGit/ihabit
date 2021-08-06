package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("selfie")
    val selfie: String,
    @SerializedName("level")
    val level: Int,
    @SerializedName("actor")
    val actor: String,
    @SerializedName("hp")
    val hp: Int,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("atk")
    val atk: Int,
    @SerializedName("def")
    val def: Int,
    @SerializedName("magic")
    val magic: Int,
    @SerializedName("career")
    val career: String?,
    @SerializedName("expPlus")
    val expPlus: Int,
    @SerializedName("moneyPlus")
    val moneyPlus: Int,
    @SerializedName("talentPoint")
    val talentPoint: Int,
    @SerializedName("money")
    val money: Int

)
