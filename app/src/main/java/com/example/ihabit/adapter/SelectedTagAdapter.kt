package com.example.ihabit.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.activity.AddHabitActivity
import com.example.ihabit.activity.AddTagActivity
import com.example.ihabit.data.Tag
import com.example.ihabit.viewHolder.WeeklyViewHolder

class SelectedTagAdapter(addHabitActivity: AddHabitActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val activity = addHabitActivity
    val tmp = Tag("+", 0)
    var seletTag: MutableList<Tag> = mutableListOf(tmp)
    val requestCodeForAddTag = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag, parent, false)
        return WeeklyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tag = seletTag[position]
        holder.itemView.apply {
            val text = findViewById<TextView>(R.id.showTag)
            text.text = tag.tagName
            text.setOnClickListener {
                if (position == seletTag.size - 1) {
                    val intent = Intent(activity, AddTagActivity::class.java)
                    activity.startActivityForResult(intent, requestCodeForAddTag)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return seletTag.size
    }

    fun refreshItems(tags: MutableList<Tag>) {
        val tmp = Tag("+", -1)
        tags.add(tmp)
        this.seletTag = tags
        notifyDataSetChanged()
    }
}