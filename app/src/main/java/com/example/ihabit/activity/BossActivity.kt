package com.example.ihabit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.adapter.AllBossAdapter
import com.example.ihabit.data.BossOverView
import com.example.ihabit.viewModel.MainViewModel

class BossActivity : AppCompatActivity() {

    val viewModel = MainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss)

        //intent拿值
        val id=intent.getStringExtra("id")
        val mail=intent.getStringExtra("mail")




        val recyclerView = findViewById<RecyclerView>(R.id.allBossRecycler)
        val allBossAdapter = AllBossAdapter(this,id!!.toInt(),mail!!)
        recyclerView.adapter = allBossAdapter
        val monster1 = BossOverView(1, "菜雞迅猛龍", "monster1")
        val monster2 = BossOverView(2, "煞氣蝙蝠龍", "monster2")
        val monster3 = BossOverView(3, "火焰龍", "monster3")
        val monster4 = BossOverView(4, "黑甲龍", "monster4")
        val monster5 = BossOverView(5, "獅頭尖牙龍", "monster5")
        val monster6 = BossOverView(6, "黑甲龍飛行型態", "monster6")
        val monster7 = BossOverView(7, "巨螯古底蟹", "monster7")
        val monster8 = BossOverView(8, "史前烏賊王", "monster8")
        val monster9 = BossOverView(9, "三角恐暴龍", "monster9")
        val monster10 = BossOverView(10, "鋼爪鐵骨龍", "monster10")
        val tmp = mutableListOf<BossOverView>(
            monster1,
            monster2,
            monster3,
            monster4,
            monster5,
            monster6,
            monster7,
            monster8,
            monster9,
            monster10
        )

        allBossAdapter.refresh(tmp)


    }


}