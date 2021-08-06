package com.example.ihabit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.activity.AddHabitActivity
import com.example.ihabit.R
import com.example.ihabit.WeekDay
import com.example.ihabit.viewHolder.WeeklyViewHolder

class WeeklyAdapter(addHabitActivity: AddHabitActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val week0 = WeekDay("0","日")
    private val week1 = WeekDay("1","一")
    private val week2 = WeekDay("2","二")
    private val week3 = WeekDay("3","三")
    private val week4 = WeekDay("4","四")
    private val week5 = WeekDay("5","五")
    private val week6 = WeekDay("6","六")
    private val activity = addHabitActivity
    private var week = listOf<WeekDay>(week0, week1, week2, week3, week4, week5, week6)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weekly, parent, false)
        return WeeklyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val day = week[position]
        holder.itemView.apply {
            val text = findViewById<TextView>(R.id.showWeeklyText)
            if(day.isChoice){
                text.setBackgroundResource(R.drawable.weekly_choice)
            }
            text.text = day.show
            text.setOnClickListener {
                if (day.isChoice) {
                    activity.removePeriod(day.weekDay)
                    text.setBackgroundResource(R.drawable.weekly)
                    day.reverseChoice()
                }
               else if(!day.isChoice){
                    activity.addPeriod(day.weekDay)
                    text.setBackgroundResource(R.drawable.weekly_choice)
                    day.reverseChoice()
                }
            }
        }

    }


    fun choice(update:ArrayList<String>){
        for( i in week.indices){
            for(j in update.indices){
                if(week[i].weekDay==update[j]){
                    week[i].isChoice=true
                }
            }
        }
        refreshItems()
    }

    override fun getItemCount(): Int {
        return week.size
    }

    fun refreshItems() {
        notifyDataSetChanged()
    }
}