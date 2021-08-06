package com.example.ihabit.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R

import com.example.ihabit.adapter.AllTagsAdapter
import com.example.ihabit.data.*
import com.example.ihabit.viewModel.MainViewModel


class AddTagActivity : AppCompatActivity() {
    val userId = SingletonUserId.getInstance()?.getId()!!
    val selectedTag: MutableList<Tag> = mutableListOf()
    val allTagsAdapter = AllTagsAdapter(this)
    val viewModel=MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)

        viewModel.getUserTags(userId)

        val tagsRecyclerView = findViewById<RecyclerView>(R.id.tagsRecycle)

        tagsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tagsRecyclerView.adapter = allTagsAdapter

        viewModel.tags.observe(this){it->
            Log.i("okhttp" ,"刷新inside",null)
            allTagsAdapter.refreshItems(it)
        }



        val confirmButton = findViewById<ImageView>(R.id.confirmButton)
        val addButton = findViewById<ImageView>(R.id.addButton)

        //新增tag
        addButton.setOnClickListener {
            if(viewModel.allTasks.value!=null){
                if (viewModel.allTasks.value!!.size >= 9) {
                    Toast.makeText(
                        this, "僅可存有十個標籤 !",
                        Toast.LENGTH_SHORT
                    ).show();
                } else {
                    val intent = Intent(this, AddOneTagActivity::class.java)
                   startActivity(intent)
                }
            }else {
                val intent = Intent(this, AddOneTagActivity::class.java)
                startActivity(intent)
            }
        }

        //選好所有的tag
        confirmButton.setOnClickListener {
            val returnIntent = Intent()
            val tagName:ArrayList<String> = arrayListOf()
            val tagId:ArrayList<String> = arrayListOf()
            for(i in 0 until selectedTag.size){
                tagName.add(selectedTag[i].tagName)
            }
            for(i in 0 until selectedTag.size){
                tagId.add(selectedTag[i].tagId.toString())
            }

            returnIntent.putStringArrayListExtra("tagName",tagName)
            returnIntent.putStringArrayListExtra("tagId",tagId)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i("okhttp","onresume",null)
        viewModel.getUserTags(userId)
    }

    fun update(){
        viewModel.getUserTags(userId)
    }

    fun removeSelectTags(tag: Tag) {
        selectedTag.remove(tag)
    }

    fun addSelectTags(tag: Tag) {
       selectedTag.add(tag)

    }
}