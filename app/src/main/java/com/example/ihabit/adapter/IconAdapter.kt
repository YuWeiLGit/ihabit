package com.example.ihabit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.activity.AddHabitActivity
import com.example.ihabit.AllIconList
import com.example.ihabit.R
import com.example.ihabit.viewHolder.TaskViewHolder

class IconAdapter(activity: AddHabitActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val allIconList = AllIconList
    val act: AddHabitActivity = activity
    var isClicked=false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val icon = allIconList[position]

        holder.itemView.apply {
            val image = findViewById<ImageView>(R.id.item)
            val back:ConstraintLayout=findViewById(R.id.iconback)
            image.setImageResource(icon)

            image.setOnClickListener {
                act.sendIcon(icon)
                Log.i("AAA","$icon",null)
            }


        }

    }


    override fun getItemCount(): Int {
        return allIconList.size
    }


}