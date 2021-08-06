package com.example.ihabit.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.activity.AddTagActivity
import com.example.ihabit.R
import com.example.ihabit.activity.MainActivity
import com.example.ihabit.data.SingletonUserId
import com.example.ihabit.data.Tag
import com.example.ihabit.viewHolder.AllTagsViewHolder
import com.example.ihabit.viewModel.MainViewModel

class AllTagsAdapter(addTagActivity: AddTagActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val alreadySelectTags: MutableList<Tag> = mutableListOf()
    var allTags:MutableList<Tag> = mutableListOf()
    val activity = addTagActivity
    val viewModel=MainViewModel()
    val userId = SingletonUserId.getInstance()?.getId()!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_tags, parent, false)
        return AllTagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tag = allTags[position]


        holder.itemView.apply {

            val text = findViewById<TextView>(R.id.tagContent)
            text.text = tag.tagName
            val seletButton = findViewById<TextView>(R.id.selectButton)
            val background = findViewById<ConstraintLayout>(R.id.tagBackground)
            seletButton.setOnClickListener {
                activity.addSelectTags(tag)
                if (addSelectTags(tag)) {
                    background.setBackgroundColor(Color.YELLOW)
                }else{
                    background.setBackgroundResource(R.color.brown)
                }
            }
            val deleteButton = findViewById<TextView>(R.id.deleteButton)
            deleteButton.setOnClickListener {
                removeTag(tag)
            }
        }
    }

    //如果重複就移除
    private fun addSelectTags(tag: Tag): Boolean {
        if (!alreadySelectTags.contains(tag)) {
            if (alreadySelectTags.size < 3) {
                alreadySelectTags.add(tag)
                return true
            } else {
//                Toast.makeText(
//                    activity, "僅可使用三個標籤 !",
//                    Toast.LENGTH_SHORT
//                ).show();
                return false
            }
        }else{
            activity.removeSelectTags(tag)
            alreadySelectTags.remove(tag)
            return false
        }
    }

    fun removeTag(tag: Tag){
        activity.removeSelectTags(tag)
        if(tag.tagId==1 ||tag.tagId==2 ||tag.tagId==3 ||tag.tagId==5){
//            Toast.makeText(activity, "無法移除預設標籤 ! !", Toast.LENGTH_LONG).show()
        }else{
            allTags.remove(tag)
        viewModel.deleteTag(userId,tag)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return allTags.size
    }

    fun refreshItems(tags: MutableList<Tag>) {
        this.allTags=tags
        notifyDataSetChanged()
    }

}