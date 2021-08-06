package com.example.ihabit.adapter

import android.animation.ObjectAnimator
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.ForgeFragment
import com.example.ihabit.R
import com.example.ihabit.activity.BlackSmithActivity
import com.example.ihabit.activity.MainActivity
import com.example.ihabit.data.gear
import com.example.ihabit.viewHolder.ForgeHolder
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_black_smith.view.*
import kotlinx.android.synthetic.main.activity_fight.view.*


class ForgeAdapter(
    usid: Int,
    activity: BlackSmithActivity,
    forgeFragment: ForgeFragment,
    viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<gear> = mutableListOf()
    var tmpitems: MutableList<gear> = mutableListOf()
    val usid = usid
    val viewModel = viewModel
    val activity = activity
    var nowPot = 0
    val forgeFragment = forgeFragment
    var isMoving = false
    var coin = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forge_item, parent, false)
        return ForgeHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.apply {

            val progress = findViewById<ProgressBar>(R.id.forgeProgess)
            progress.max = 100

            val itemPic = findViewById<ImageView>(R.id.itemPic)
            val oldLvNum = findViewById<TextView>(R.id.oldLvNum)
            val oldLvNum2 = findViewById<TextView>(R.id.oldLvNum2)
            val price = findViewById<TextView>(R.id.price)
            val forgeDes = findViewById<TextView>(R.id.forgeDes)
            progress.progress = 0


            viewModel.forProgress.observe(activity) {
                if (position == nowPot) {
                    activity.startToWork(true)
                }
                if(it==0){
                    isMoving=false
                    activity.finishWork()
                }
            }
            viewModel.forgeNum.observe(activity) {
                if (position == nowPot) {
                    oldLvNum.text = it.toString()
                    isMoving = false
                }
            }


            //設定裝備圖片
            val itemName: String = item.icon

            //點擊打鐵
            val process = findViewById<ImageView>(R.id.process)
            process.setOnClickListener {
                if (coin > item.buildPrice) {

                    if (isMoving) {
                        Toast.makeText(activity, "請先等待結果 !", Toast.LENGTH_LONG).show()
                    } else {
                        nowPot = position
                        isMoving=true
                        viewModel.postForgeItem(usid, item.prodId)
                    }
                } else {
                    Toast.makeText(activity, "金錢不夠", Toast.LENGTH_LONG).show()
                }
            }



            itemPic.setImageResource(iconConverter(itemName))

            //舊裝備屬性
            oldLvNum.text = item.propData.toString()

            //價錢
            price.text = item.buildPrice.toString()
            //屬性提升

            oldLvNum2.text = when (item.type) {
                1 -> "血量"
                2 -> "魔力"
                3 -> "防禦"
                4 -> "防禦"
                5 -> "血量"
                else -> "力量"
            }

            forgeDes.text = when (item.type) {
                1 -> "血量"
                2 -> "魔力"
                3 -> "防禦"
                4 -> "防禦"
                5 -> "血量"
                else -> "力量"
            }


        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun coinUpdate(coin: Int) {
        this.coin = coin
    }

    private fun iconConverter(icon: String): Int {
        return when (icon) {
            //一階
            "head_1_2" -> R.drawable.head_1_2
            "acc_1_2" -> R.drawable.acc_1_2
            "armor_1_2" -> R.drawable.armor_1_2
            "shield_1_2" -> R.drawable.shield_1_2
            "pant_1_1" -> R.drawable.pant_1_1
            "warriorWp_1_1" -> R.drawable.warriorwp_1_1
            "mageWp_1_1" -> R.drawable.magewp_1_1
            "archer_1_1" -> R.drawable.archer_1_1

            //二階
            "head_2_1" -> R.drawable.head_2_1
            "acc_2_2" -> R.drawable.acc_2_2
            "armor_2_1" -> R.drawable.armor_2_1
            "shield_2_1" -> R.drawable.shield_2_1
            "pant_2_2" -> R.drawable.pant_2_2
            "warriorWp_2_1" -> R.drawable.warriorwp_2_1
            "mageWp_2_1" -> R.drawable.magewp_2_1
            "archer_2_1" -> R.drawable.archer_2_1
            //三階
            "head_3" -> R.drawable.head_3
            "acc_3" -> R.drawable.acc_3
            "armor_3" -> R.drawable.armor_3
            "shield_3" -> R.drawable.shield_3
            "pant_3" -> R.drawable.pant_3
            "warriorwp_3" -> R.drawable.warriorwp_3
            "magewp_3" -> R.drawable.magewp_3
            "archer_3" -> R.drawable.archer_3

            //四階
            "head_4" -> R.drawable.head_4
            "acc_4" -> R.drawable.acc_4
            "armor_4" -> R.drawable.armor_4
            "shield_4" -> R.drawable.shield_4
            "pant_4" -> R.drawable.pant_4
            "warriorwp_4" -> R.drawable.warriorwp_4
            "magewp_4" -> R.drawable.magewp_4
            "archer_4" -> R.drawable.archer_4

            //五階
            "head_5" -> R.drawable.head_5
            "acc_5" -> R.drawable.acc_5
            "armor_5" -> R.drawable.armor_5
            "shield_5" -> R.drawable.shield_5
            "pant_5" -> R.drawable.pant_5
            "warriorwp_5" -> R.drawable.warriorwp_5
            "magewp_5" -> R.drawable.magewp_5
            "archer_5" -> R.drawable.archer_5

            //六階
            "head_6" -> R.drawable.head_6
            "acc_6" -> R.drawable.acc_6
            "armor_6" -> R.drawable.armor_6
            "shield_6" -> R.drawable.shield_6
            "pant_6" -> R.drawable.pant_6
            "warriorwp_6" -> R.drawable.warriorwp_6
            "magewp_6" -> R.drawable.magewp_6
            "archer_6" -> R.drawable.archer_6
            //七階
            "head_7" -> R.drawable.head_7
            "acc_7" -> R.drawable.acc_7
            "armor_7" -> R.drawable.armor_7
            "shield_7" -> R.drawable.shield_7
            "pant_7" -> R.drawable.pant_7
            "warriorwp_7" -> R.drawable.warriorwp_7
            "magewp_7" -> R.drawable.magewp_7
            "archer_7" -> R.drawable.archer_7

            //八階
            "head_8" -> R.drawable.head_8
            "acc_8" -> R.drawable.acc_8
            "armor_8" -> R.drawable.armor_8
            "shield_8" -> R.drawable.shield_8
            "pant_8" -> R.drawable.pant_8
            "warriorwp_8" -> R.drawable.warriorwp_8
            "magewp_8" -> R.drawable.magewp_8
            "archer_8" -> R.drawable.archer_8

            //九階
            "head_9" -> R.drawable.head_9
            "acc_9" -> R.drawable.acc_9
            "armor_9" -> R.drawable.armor_9
            "shield_9" -> R.drawable.shield_9
            "pant_9" -> R.drawable.pant_9
            "warriorwp_9" -> R.drawable.warriorwp_9
            "magewp_9" -> R.drawable.magewp_9
            "archer_9" -> R.drawable.archer_9

            //十階
            "head_10" -> R.drawable.head_10
            "acc_10" -> R.drawable.acc_10
            "armor_10" -> R.drawable.armor_10
            "shield_10" -> R.drawable.shield_10
            "pant_10" -> R.drawable.pant_10
            "warriorwp_10" -> R.drawable.warriorwp_10
            "magewp_10" -> R.drawable.magewp_10
            "archer_10" -> R.drawable.archer_10


            else -> R.drawable.archer_1_1
        }

    }

    fun refresh(item: MutableList<gear>) {
        Log.i("BBB", "排序4", null)
        tmpitems = item
        if (!isMoving) {
            this.items = item
            notifyDataSetChanged()
        }
    }

    fun rerun() {
        items = tmpitems
        notifyDataSetChanged()
    }


}