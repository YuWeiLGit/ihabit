package com.example.ihabit.data

import com.google.gson.annotations.SerializedName

data class Monster(
    @SerializedName("monsterId")
    val monsterId:Int,
    @SerializedName("monsterName")
    val monsterName:String,
    @SerializedName("level")
    val level:Int,
    @SerializedName("icon")
    val icon:String,
    @SerializedName("lightAttack")
    val lightAttack:Int,
    @SerializedName("hp")
    val hp:Int,
    @SerializedName("normalAttack")
    val normalAttack:Int,
    @SerializedName("criticalStrike")
    val criticalStrike:Int

)
