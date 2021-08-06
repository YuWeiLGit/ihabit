package com.example.ihabit.activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.observe
import com.example.ihabit.R
import com.example.ihabit.data.UpdateUser
import com.example.ihabit.viewModel.MainViewModel

class CharacterActivity : AppCompatActivity() {
    var currentTitle:String?=null
    var allTitle = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charater)
        val userId = intent?.getStringExtra("UID")
        val email=  intent?.getStringExtra("EMAIL")

        //文字
        val name=findViewById<TextView>(R.id.nameText)
        val level=findViewById<TextView>(R.id.levelText)
        val career=findViewById<TextView>(R.id.careerText)
        val title=findViewById<TextView>(R.id.titleContext)
        val hp=findViewById<TextView>(R.id.hpText)
        val mp=findViewById<TextView>(R.id.manaText)
        val atk=findViewById<TextView>(R.id.atkText)
        val def=findViewById<TextView>(R.id.defText)
        val exp=findViewById<TextView>(R.id.expText)
        val money=findViewById<TextView>(R.id.moneyText)
        //圖片
        val userIcon=findViewById<ImageView>(R.id.userIco)
        val headIcon=findViewById<ImageView>(R.id.headIcon)
        val armorIcon=findViewById<ImageView>(R.id.armorIcon)
        val weaponIcon=findViewById<ImageView>(R.id.weponIcon)
        val sheldIcon=findViewById<ImageView>(R.id.sheldIcon)
        val pantIcon=findViewById<ImageView>(R.id.pantIcon)
        val accIcon=findViewById<ImageView>(R.id.accIcon)


        val choiceTitle=findViewById<ImageView>(R.id.choiceTitle)
        val viewModel=MainViewModel()
        //拿title
        viewModel.getTitle(userId!!.toInt())
        viewModel.userTitle.observe(this){
            allTitle=it
        }
        //訊息類
        viewModel.getUserInfo(userId!!.toInt(),email.toString())
        viewModel.loginUser.observe(this){it->
            name.text=it.name
            level.text=it.level.toString()
            career.text=it.career
            currentTitle=it.title
            if(it.title==null ||it.title==""){
                title.text="初出茅廬"
            }else{
                title.text=it.title
            }
            val tmpHp=it.hp
            hp.text="最大生命值: $tmpHp"
            val tmpMp=it.magic
            mp.text="最大魔力值: $tmpMp"
            val tmpAtk=it.atk
            atk.text="總傷害: $tmpAtk"
            val tmpDef=it.def
            def.text="總防禦: $tmpDef"
            val tmpExp=it.expPlus
            exp.text="經驗獲取加成: $tmpExp"
            val tmpMoney=it.moneyPlus
            money.text="金錢獲取加成: $tmpMoney"
            val tmpCar =it.career
            if(tmpCar=="戰士"){
                userIcon.setImageResource(R.drawable.warrior_walk0_)
            }else if(tmpCar=="法師"){
                userIcon.setImageResource(R.drawable.healer_walk0)
            }
        }
        //裝備圖示
        viewModel.getItem(userId.toString())
        viewModel.userItem.observe(this){it->
            val item=it.allItems
            for(i in 0  until item.size){
                if(item[i].type==1){
                    headIcon.setImageResource(iconConverter(item[i].icon))
                }else if(item[i].type==2){
                    accIcon.setImageResource(iconConverter(item[i].icon))
                }else if(item[i].type==3){
                    armorIcon.setImageResource(iconConverter(item[i].icon))
                }else if(item[i].type==4){
                    sheldIcon.setImageResource(iconConverter(item[i].icon))
                }else if(item[i].type==5){
                    pantIcon.setImageResource(iconConverter(item[i].icon))
                }else if(item[i].type==6){
                    weaponIcon.setImageResource(iconConverter(item[i].icon))
                }
            }
        }


        //更換title
        choiceTitle.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val mView: View = this.layoutInflater.inflate(R.layout.filter, null)
            alert.setTitle("選擇篩選稱號")
            val spinner = mView.findViewById<Spinner>(R.id.spinner)
            val tmpTitle: MutableList<String> = mutableListOf("初出茅廬")
            for(i in 0 until allTitle.size){
                tmpTitle.add(allTitle[i])
            }
            val adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tmpTitle)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            val dialog = alert.create()
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                       currentTitle=spinner.selectedItem.toString()
                        if(currentTitle=="初出茅廬"){
                            title.text="初出茅廬"
                            val tmp = UpdateUser(userId.toInt(),email!!,null,null,"",null,null,null)
                            viewModel.putUserInfo(tmp)
                            }else{
                            title.text=currentTitle
                            val tmp = UpdateUser(userId.toInt(),email!!,null,null,currentTitle,null,null,null)
                            viewModel.putUserInfo(tmp)
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE
                    -> {
                        dialog.cancel()
                    }
                }
            }
            alert.setPositiveButton("確定", dialogClickListener)
            alert.setNegativeButton("取消", dialogClickListener)
            alert.setView(mView)
            alert.show()
        }


    }



    private fun iconConverter(icon:String):Int{
        return  when(icon){
            //一階
            "head_1_2"->R.drawable.head_1_2
            "acc_1_2"->R.drawable.acc_1_2
            "armor_1_2"->R.drawable.armor_1_2
            "shield_1_2"->R.drawable.shield_1_2
            "pant_1_1"->R.drawable.pant_1_1
            "warriorWp_1_1"->R.drawable.warriorwp_1_1
            "mageWp_1_1"->R.drawable.magewp_1_1
            "archer_1_1"->R.drawable.archer_1_1

            //二階
            "head_2_1"->R.drawable.head_2_1
            "acc_2_2"->R.drawable.acc_2_2
            "armor_2_1"->R.drawable.armor_2_1
            "shield_2_1"->R.drawable.shield_2_1
            "pant_2_2"->R.drawable.pant_2_2
            "warriorWp_2_1"->R.drawable.warriorwp_2_1
            "mageWp_2_1"->R.drawable.magewp_2_1
            "archer_2_1"->R.drawable.archer_2_1
            //三階
            "head_3"->R.drawable.head_3
            "acc_3"->R.drawable.acc_3
            "armor_3"->R.drawable.armor_3
            "shield_3"->R.drawable.shield_3
            "pant_3"->R.drawable.pant_3
            "warriorwp_3"->R.drawable.warriorwp_3
            "magewp_3"->R.drawable.magewp_3
            "archer_3"->R.drawable.archer_3

            //四階
            "head_4"->R.drawable.head_4
            "acc_4"->R.drawable.acc_4
            "armor_4"->R.drawable.armor_4
            "shield_4"->R.drawable.shield_4
            "pant_4"->R.drawable.pant_4
            "warriorwp_4"->R.drawable.warriorwp_4
            "magewp_4"->R.drawable.magewp_4
            "archer_4"->R.drawable.archer_4

            //五階
            "head_5"->R.drawable.head_5
            "acc_5"->R.drawable.acc_5
            "armor_5"->R.drawable.armor_5
            "shield_5"->R.drawable.shield_5
            "pant_5"->R.drawable.pant_5
            "warriorwp_5"->R.drawable.warriorwp_5
            "magewp_5"->R.drawable.magewp_5
            "archer_5"->R.drawable.archer_5

            //六階
            "head_6"->R.drawable.head_6
            "acc_6"->R.drawable.acc_6
            "armor_6"->R.drawable.armor_6
            "shield_6"->R.drawable.shield_6
            "pant_6"->R.drawable.pant_6
            "warriorwp_6"->R.drawable.warriorwp_6
            "magewp_6"->R.drawable.magewp_6
            "archer_6"->R.drawable.archer_6
            //七階
            "head_7"->R.drawable.head_7
            "acc_7"->R.drawable.acc_7
            "armor_7"->R.drawable.armor_7
            "shield_7"->R.drawable.shield_7
            "pant_7"->R.drawable.pant_7
            "warriorwp_7"->R.drawable.warriorwp_7
            "magewp_7"->R.drawable.magewp_7
            "archer_7"->R.drawable.archer_7

            //八階
            "head_8"->R.drawable.head_8
            "acc_8"->R.drawable.acc_8
            "armor_8"->R.drawable.armor_8
            "shield_8"->R.drawable.shield_8
            "pant_8"->R.drawable.pant_8
            "warriorwp_8"->R.drawable.warriorwp_8
            "magewp_8"->R.drawable.magewp_8
            "archer_8"->R.drawable.archer_8

            //九階
            "head_9"->R.drawable.head_9
            "acc_9"->R.drawable.acc_9
            "armor_9"->R.drawable.armor_9
            "shield_9"->R.drawable.shield_9
            "pant_9"->R.drawable.pant_9
            "warriorwp_9"->R.drawable.warriorwp_9
            "magewp_9"->R.drawable.magewp_9
            "archer_9"->R.drawable.archer_9

            //十階
            "head_10"->R.drawable.head_10
            "acc_10"->R.drawable.acc_10
            "armor_10"->R.drawable.armor_10
            "shield_10"->R.drawable.shield_10
            "pant_10"->R.drawable.pant_10
            "warriorwp_10"->R.drawable.warriorwp_10
            "magewp_10"->R.drawable.magewp_10
            "archer_10"->R.drawable.archer_10


            else ->  R.drawable.archer_1_1}

    }




}