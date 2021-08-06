package com.example.ihabit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.activity.FightActivity
import com.example.ihabit.viewHolder.SkillHolder
import com.example.ihabit.viewHolder.TaskViewHolder

class SkillAdapter(career:String,fightActivity: FightActivity):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val career=career
    val activity=fightActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ability, parent, false)
        return SkillHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply{
            val ability1=findViewById<ImageView>(R.id.ability1)
            val ability2=findViewById<ImageView>(R.id.ability2)
            val ability3=findViewById<ImageView>(R.id.ability3)

            //換職業圖片
            when(career){
                "法師"->{
                    ability1.setImageResource(R.drawable.mageskill1)
                    ability2.setImageResource(R.drawable.mageskill2)
                    ability3.setImageResource(R.drawable.mageskill3)
                }
                "弓箭手"->{
                    ability1.setImageResource(R.drawable.archerskill1)
                    ability2.setImageResource(R.drawable.archerskill2)
                    ability3.setImageResource(R.drawable.archerskill3)
                }
            }

            ability1.setOnClickListener {
                activity.useSkill(1)
            }

            ability2.setOnClickListener {
                activity.useSkill(2)
            }
            ability3.setOnClickListener {
                activity.useSkill(3)
            }

        }
    }

    override fun getItemCount(): Int {
        return 1
    }

}