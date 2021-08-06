package com.example.ihabit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.data.Task
import com.example.ihabit.data.TaskForReturn
import com.example.ihabit.data.TaskForUpdate
import com.example.ihabit.viewHolder.AllTasksViewHolder
import com.example.ihabit.viewHolder.InsideTaskContent
import com.example.ihabit.viewModel.MainViewModel
import org.w3c.dom.Text

class InsideTaskAdapter(task: TaskForReturn, taskOverViewAdapter: TaskOverViewAdapter,userid:Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var task = task
    val outside = taskOverViewAdapter
    val userid=userid
    val viewModel=MainViewModel()
    var isOpen=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inside_task, parent, false)
        return InsideTaskContent(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            val positive = findViewById<TextView>(R.id.positiveContent)
            positive.text = task.message
            isOpen=task.isInform

            val noti = findViewById<ImageView>(R.id.noti)

            val monthNum = findViewById<TextView>(R.id.thisMonthNum)
            monthNum.text = task.completeDaysOfMonth.toString()
            val comboNum = findViewById<TextView>(R.id.comboNum)
            comboNum.text = task.continueDays.toString()
            val since = findViewById<TextView>(R.id.since)
            since.text = "since ${task.startDate}"

            if(task.isInform){
                noti.setImageResource(R.drawable.ic_baseline_notifications_active_24)
                noti.setBackgroundResource(R.color.Theme_Hickory)
            }else{
                noti.setImageResource(R.drawable.ic_baseline_notifications_off_24)
                noti.setBackgroundResource(R.color.Theme_Linen)
            }

            noti.setOnClickListener {
                if(isOpen){
                    val update = TaskForUpdate(
                        task.habitId,
                        userid,
                        null,
                        null,
                        null,
                        null,
                        null,
                        false,
                        null,
                        null,
                        null,
                        null

                    )
                    isOpen=false
                    noti.setImageResource(R.drawable.ic_baseline_notifications_off_24)
                    noti.setBackgroundResource(R.color.Theme_Linen)
                    viewModel.updateHabit(update)
                }else{
                    val update = TaskForUpdate(
                        task.habitId,
                        userid,
                        null,
                        null,
                        null,
                        null,
                        null,
                        true,
                        null,
                        null,
                        null,
                        null

                    )
                    isOpen=true
                    noti.setImageResource(R.drawable.ic_baseline_notifications_active_24)
                    noti.setBackgroundResource(R.color.Theme_Hickory)
                    viewModel.updateHabit(update)

                }


            }


        }


    }

    override fun getItemCount(): Int {
        return 1
    }

    fun refreshItems(task: TaskForReturn) {
        this.task = task
        notifyDataSetChanged()
    }

}