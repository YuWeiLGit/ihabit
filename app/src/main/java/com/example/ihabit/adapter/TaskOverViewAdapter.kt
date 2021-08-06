package com.example.ihabit.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.activity.AllTasksActivity
import com.example.ihabit.data.Task
import com.example.ihabit.data.TaskForReturn
import com.example.ihabit.data.TaskForUpdate
import com.example.ihabit.data.TaskOverView
import com.example.ihabit.viewHolder.AllTasksViewHolder
import com.example.ihabit.viewModel.MainViewModel

class TaskOverViewAdapter(userId: Int, activity: AllTasksActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var allTask: MutableList<TaskForReturn> = mutableListOf()
    var isOpen = false
    val viewModel = MainViewModel()
    val uId = userId
    val act = activity
    val overViewAdapter:TaskOverViewAdapter=this

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_overview, parent, false)
        return AllTasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = allTask[position]

        holder.itemView.apply {
            val allTasksRecyclerView = findViewById<RecyclerView>(R.id.taskRecycler)
            val adapter = InsideTaskAdapter(task,overViewAdapter,uId )
            val switch = findViewById<Switch>(R.id.switchContent)
            val show=findViewById<TextView>(R.id.show)

            val comboDay = findViewById<TextView>(R.id.ousideComboDay)
            val taskContent = findViewById<TextView>(R.id.taskContent)
            val icon = findViewById<ImageView>(R.id.TaskIcon)
            val comboNum = task.continueDays.toString()
            comboDay.text = comboNum + "天"

            //點刪除任務後跳確認視窗
            val deleteTask = findViewById<TextView>(R.id.deleteTask)
            deleteTask.setOnClickListener {
                val builder=
                    AlertDialog.Builder(act)
                        .setMessage("確認是否刪除")
                        .setTitle("刪除任務")
                        .setPositiveButton("確認") { dialog, _ ->
                            deleteTask(uId, task)
                            allTask.remove(task)
                            if(allTask.size==0){
                                act.toSee(true)
                            }
                            notifyDataSetChanged()
                            dialog.cancel()
                        }
                        .setNegativeButton("取消"){dialog,_->
                            dialog.cancel()
                        }
                val alter=builder.create()
                alter.show()

            }


            taskContent.text = task.habitName
            icon.setImageResource(idConverter(task.icon.toInt()))
            allTasksRecyclerView.adapter = adapter

            //點即開啟內部
            switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val update = TaskForUpdate(
                        task.habitId,
                        uId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        true,
                        null

                    )
                    show.setBackgroundResource(R.drawable.top_task_close)
                    viewModel.updateHabit(update)
                    allTasksRecyclerView.adapter = null
                } else {
                    val update = TaskForUpdate(
                        task.habitId,
                        uId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        false,
                        null
                    )
                    show.setBackgroundResource(R.drawable.top_task_open)
                    viewModel.updateHabit(update)
                    allTasksRecyclerView.adapter = adapter
                }
            }
            if (task.isClose) {
                switch.isChecked = true
                allTasksRecyclerView.adapter = null
            }
        }
    }

    fun idConverter(id: Int): Int {
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

    fun deleteTask(userId: Int, task: TaskForReturn) {
        viewModel.deleteTask(userId, task.habitId)
    }

    override fun getItemCount(): Int {
        return allTask.size
    }

    fun changeNotify(open: Boolean,id:Int){

        if(open){
            val update = TaskForUpdate(
                id,
                uId,
                null,
                null,
                null,
                null,
                null,
                true,
                null,
                null,
                true,
                null)
            viewModel.updateHabit(update)
        }else{
            val update = TaskForUpdate(
                id,
                uId,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                true,
                null)
            viewModel.updateHabit(update)
        }
    }



    fun refreshItems(tasks: MutableList<TaskForReturn>) {
        Log.i("AAA", tasks.size.toString(), null)
        this.allTask = tasks
        notifyDataSetChanged()
    }

}