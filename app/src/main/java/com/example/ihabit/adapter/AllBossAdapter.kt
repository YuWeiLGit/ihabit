package com.example.ihabit.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.activity.FightActivity
import com.example.ihabit.data.BossOverView
import com.example.ihabit.viewHolder.AllTagsViewHolder
import com.example.ihabit.viewHolder.BossHolder
import kotlinx.android.synthetic.main.task_overview.view.*

class AllBossAdapter(activity:Activity,uerid:Int,email:String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val activity=activity
    var allBoss:MutableList<BossOverView> = mutableListOf()
    val userId = uerid
    val email=email
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.boss_scene, parent, false)
        return BossHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val  boss=allBoss[position]

        holder.itemView.apply {
            val bossName=findViewById<TextView>(R.id.monsterNameOverView)
            val bossPic=findViewById<ImageView>(R.id.bossPic)
            bossName.text=boss.name
            bossPic.setImageResource(iconConverter(boss.icon))
            bossPic.setOnClickListener {

                val intent = Intent(context, FightActivity::class.java)
                val monsterNum:String=boss.id.toString()

                intent.putExtra("monsterNum",monsterNum)
                intent.putExtra("ID",userId.toString())
                intent.putExtra("mail",email.toString())


                context.startActivity(intent)

            }


        }
    }

    override fun getItemCount(): Int {
       return  allBoss.size
    }



    fun refresh(allBoss:MutableList<BossOverView>){
        this.allBoss=allBoss
    }


    fun iconConverter(name:String):Int{
        return when(name){
            "monster1"-> R.drawable.monster1
            "monster2"-> R.drawable.monster2
            "monster3"-> R.drawable.monster3
            "monster4"-> R.drawable.monster4
            "monster5"-> R.drawable.monster5
            "monster6"-> R.drawable.monster6
            "monster7"-> R.drawable.monster7
            "monster8"-> R.drawable.monster8
            "monster9"-> R.drawable.monster9
            else -> R.drawable.monster10
        }


    }



}