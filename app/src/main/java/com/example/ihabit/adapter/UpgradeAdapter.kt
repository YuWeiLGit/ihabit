package com.example.ihabit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.ForgeFragment
import com.example.ihabit.R
import com.example.ihabit.UpgradeFragment
import com.example.ihabit.activity.BlackSmithActivity
import com.example.ihabit.data.UserInfo
import com.example.ihabit.data.gear
import com.example.ihabit.viewHolder.UpgradeHolder
import com.example.ihabit.viewModel.MainViewModel

class UpgradeAdapter(
    usid: Int,
    email: String,
    activity: BlackSmithActivity,
    viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<gear> = mutableListOf()
    val userId = usid
    val email = email
    val activity = activity
    val viewModel = viewModel
    lateinit var userInfo: UserInfo
    var nowpos = -1
    var coin = 0
    var isMoving = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upgrade, parent, false)
        return UpgradeHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        viewModel.getUserInfo(userId, email)
        viewModel.loginUser.observe(activity) { it ->
            userInfo = it
        }

        holder.itemView.apply {
            //裝備圖片
            val itemPic = findViewById<ImageView>(R.id.itemPicUpgrade)
            val itemPicNum = iconConverter(item.icon)
            itemPic.setImageResource(itemPicNum)
            //舊階級
            val oldUpgradeNum = findViewById<TextView>(R.id.oldGradeNum)
            oldUpgradeNum.text = item.level.toString()
            //新階級
            val newUpgradeNum = findViewById<TextView>(R.id.newGradeNum)
            if(item.level==10){
                newUpgradeNum.text = "10"
            }else{
                newUpgradeNum.text = (item.level + 1).toString()
            }
            //價格
            val price = findViewById<TextView>(R.id.priceToUpgrade)
            price.text = item.upLevelPrice.toString()

            //進度條
            val progressBar = findViewById<ProgressBar>(R.id.progessUp)
            progressBar.max = 100

            val result = findViewById<ImageView>(R.id.resultUp)

            viewModel.forProgress2.observe(activity) {
                if (position == nowpos) {
                    if(it==0){
                        activity.finishWork()
                    }
                }
            }
            viewModel.upgrageresult.observe(activity) {
                if (position == nowpos) {
                    if (it != 0) {
                        oldUpgradeNum.text = it.toString()
                        if(it==10){
                            newUpgradeNum.text = "10"
                        }else{
                            newUpgradeNum.text = (it + 1).toString()
                        }

                    }

                }
            }

            viewModel.resultForUpgrade.observe(activity) {

                if (position == nowpos) {
                    if (it) {
                        result.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                    } else {
                        Toast.makeText(activity, "升級失敗 !", Toast.LENGTH_LONG).show()
                    }
                    isMoving = false
                }
            }


            //升級按鍵
            val upgradeButton = findViewById<ImageView>(R.id.upgradeButton)
            upgradeButton.setOnClickListener {
                //等級低於要求不發API
                if ((item.level + 1) > userInfo.level) {
                    Toast.makeText(activity, "請先提升角色等級 !", Toast.LENGTH_LONG).show()
                } else {
                    if (coin < item.upLevelPrice) {
                        Toast.makeText(activity, "金錢不足 !", Toast.LENGTH_LONG).show()
                    } else {
                        if (isMoving) {
                            Toast.makeText(activity, "請先等待結果 !", Toast.LENGTH_LONG).show()
                        } else {
                            isMoving = true
                            nowpos = position
                            activity.startToWork(true)
                            result.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                            viewModel.postUpgrade(userId, item.prodId)
                        }

                    }
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun coinUpdate(coin: Int) {
        this.coin = coin
    }

    fun refresh(item: MutableList<gear>) {
        this.items = item
        notifyDataSetChanged()
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
}