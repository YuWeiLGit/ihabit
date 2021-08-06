package com.example.ihabit.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.MainFrameFragment
import com.example.ihabit.R
import com.example.ihabit.activity.AddHabitActivity
import com.example.ihabit.activity.MainActivity
import com.example.ihabit.data.TaskOverView
import com.example.ihabit.viewHolder.TaskViewHolder
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.task.*
import kotlinx.android.synthetic.main.task_overview.view.*
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(activity: MainActivity, userId: Int,email:String,fragment: MainFrameFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tasks: MutableList<TaskOverView> = mutableListOf()
    private val activity = activity
    private val viewModel = MainViewModel()
    private val userId=userId
    private val email=email
    private val fragment=fragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val task = tasks[position]
        holder.itemView.apply {
            val iconPic = findViewById<ImageView>(R.id.iconPic)
            val text = findViewById<TextView>(R.id.showTask)
            val doneButton = findViewById<ImageView>(R.id.doneButton)
            val updateButton = findViewById<ImageView>(R.id.update)
            if (task.isDone) {
                doneButton.setImageResource(R.drawable.icon_star_gold)
            }else{
                doneButton.setImageResource(R.drawable.icon_star_white)
            }

            //完成任務發送已完成或取消完成api
            doneButton.setOnClickListener {
                if (task.isDone) {
                    //目前無法取消已完成
                } else if (!task.isDone) {
                    val sdf = SimpleDateFormat("yyyy/MM/dd")
                    var doneDate = startDateConvert(sdf.format(Date()))
                    doneButton.setImageResource(R.drawable.icon_star_gold)
                    viewModel.postDoneDate(task.habitId, doneDate)
                }
            }


            viewModel.taskResult.observe(activity){_->
                fragment.update()
            }


            //修改內容
            updateButton.setOnClickListener {
                val intent = Intent(activity, AddHabitActivity::class.java)
                intent.putExtra("habitId", task.habitId.toString())
                intent.putExtra("userId", userId.toString())
                activity.startActivity(intent)
            }


            text.text = task.habitName
            val picName =iconConvert(task.icon.toInt())
            val resId = resources.getIdentifier(picName.toString(), "id", " res/ drawable");
            iconPic.setImageResource(resId)
        }


    }




    fun iconConvert(id: Int): Int {
        return when (id) {
            1 -> R.drawable.icon1
            2 -> R.drawable.icon2
            3 -> R.drawable.icon3
            4 -> R.drawable.icon4
            5 -> R.drawable.icon5
            7 -> R.drawable.icon7
            8 -> R.drawable.icon8
            9 -> R.drawable.icon9
            10 -> R.drawable.icon10
            11 -> R.drawable.icon11
            12 -> R.drawable.icon12
            13 -> R.drawable.icon13
            14 -> R.drawable.icon14
            15 -> R.drawable.icon15
            16 -> R.drawable.icon16
            17 -> R.drawable.icon17
            18 -> R.drawable.icon18
            19 -> R.drawable.icon19
            20 -> R.drawable.icon20
            21 -> R.drawable.icon21
            22 -> R.drawable.icon22
            23 -> R.drawable.icon23
            24 -> R.drawable.icon24
            25 -> R.drawable.icon25
            26 -> R.drawable.icon26
            27 -> R.drawable.icon27
            28 -> R.drawable.icon28

            else -> R.drawable.icon6
        }
    }
    //轉換日期格式
    fun startDateConvert(date: String): String {
        val str = date
        val split = str.split('/') //用空格拆
        val year = split[0]
        var month = split[1]
        var date = split[2]
        return "$year-$month-$date"

    }

    override fun getItemCount(): Int {

        return tasks.size
    }


    fun refreshItems(tasks: MutableList<TaskOverView>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}