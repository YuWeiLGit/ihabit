package com.example.ihabit.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ihabit.R
import com.example.ihabit.adapter.TaskOverViewAdapter
import com.example.ihabit.data.TaskForReturn
import com.example.ihabit.data.TaskOverView
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.task_overview.*


class AllTasksActivity : AppCompatActivity() {

    val viewModel = MainViewModel()
    //單純拿ID用
    var tasksOverView: MutableList<TaskOverView> = mutableListOf()
//    var taskForLayOut:MutableList<TaskForReturn> = mutableListOf()
    lateinit var notaskImg:ImageView
    lateinit var notaskText:TextView
    lateinit var addButton:TextView
    var tmp=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)
        val usId = intent.getStringExtra("id")!!.toInt()
        val userId=usId
        tmp=userId
        val adapter = TaskOverViewAdapter(usId,this)
         notaskImg=findViewById<ImageView>(R.id.notaskImg)
         notaskText=findViewById<TextView>(R.id.notask)
         addButton=findViewById<TextView>(R.id.notask2)

        val allTasksRecyclerView = findViewById<RecyclerView>(R.id.allTaskRecycler)
        allTasksRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //call全部的task拿id 再用id call詳細資料
        viewModel.allTaskWisClose.observe(this) {
            tasksOverView = it
            if(it.size==0){
                notaskImg.visibility= View.VISIBLE
                notaskText.visibility= View.VISIBLE
                addButton.visibility= View.VISIBLE
            }else{
                notaskImg.visibility= View.INVISIBLE
                notaskText.visibility= View.INVISIBLE
                addButton.visibility= View.INVISIBLE
            }
            Log.i("CCC",tasksOverView.size.toString(),null)
            val allTaskId : MutableList<Int> = mutableListOf()
            Log.i("CCC",allTaskId.size.toString(),null)
            for(i in 0 until tasksOverView.size){
                allTaskId.add(tasksOverView[i].habitId)
            }
            viewModel.getAllTaskDetail(allTaskId,usId)
        }

        viewModel.allTaskDetail.observe(this){it->
            Log.i("CCC","刷新",null)
            adapter.refreshItems(it)
        }


        addButton.setOnClickListener {
            val intent = Intent(baseContext, AddHabitActivity::class.java)
            intent.putExtra("userid",userId.toString())
            startActivity(intent)
        }

        viewModel.taskResult.observe(this) {
            viewModel.getAllTask(usId)
        }


        //左滑刪除
//        val itemTouchHelper =
//            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: ViewHolder,
//                    target: ViewHolder
//                ): Boolean {
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//                    val tmp: Int = viewHolder.adapterPosition
//                    val task=taskForLayOut[tmp]
//
//
//                    taskForLayOut.removeAt(tmp)
//                    adapter.deleteTask(usId,task)
//                    adapter.refreshItems(taskForLayOut)
//                }
//            }
//            )
//        itemTouchHelper.attachToRecyclerView(allTasksRecyclerView)
        allTasksRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTask(tmp)


    }

    fun toSee(boolean: Boolean){
        if(boolean){
            notaskImg.visibility= View.VISIBLE
            notaskText.visibility= View.VISIBLE
            addButton.visibility= View.VISIBLE
        }else{
            notaskImg.visibility= View.INVISIBLE
            notaskText.visibility= View.INVISIBLE
            addButton.visibility= View.INVISIBLE
        }

    }




}