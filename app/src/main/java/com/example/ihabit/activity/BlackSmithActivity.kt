package com.example.ihabit.activity

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.ihabit.R
import com.example.ihabit.adapter.BlacksmithPagerAdapter
import com.example.ihabit.viewModel.MainViewModel
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BlackSmithActivity : AppCompatActivity() {


    lateinit var charaterAni:ImageView
    lateinit var waitAni:AnimationDrawable
    lateinit var workAni:AnimationDrawable
    private val viewModel=MainViewModel()
    lateinit var coinNum:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_smith)
        val userId = intent?.getStringExtra("uId")
        val email=  intent?.getStringExtra("eMail")


        val tabLayout=findViewById<TabLayout>(R.id.tabLayout)
        val viewPager=findViewById<ViewPager>(R.id.viewpager)
        val fragmentManager=supportFragmentManager
         coinNum=findViewById<TextView>(R.id.coinNum)
        val adapter = BlacksmithPagerAdapter(fragmentManager,2,userId!!.toInt(),this,email!!)
        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)


        viewModel.getUserInfo(userId.toInt(),email!!)
        viewModel.loginUser.observe(this){it->
            val tmp=it.money
            coinNum.text=tmp.toString()
        }




        ///動畫
         charaterAni=findViewById(R.id.blacksmith)
         waitAni= resources.getDrawable(R.drawable.blacksmith_wait) as AnimationDrawable
        workAni=resources.getDrawable(R.drawable.blacksmith_work) as AnimationDrawable
        charaterAni.setImageDrawable(waitAni)
        waitAni.start()





    }

        fun moneyChange(coin:Int){
            coinNum.text=coin.toString()
        }
       fun startToWork(boolean:Boolean){
            if(boolean){
                Log.i("BBB","打鐵",null)
                charaterAni.clearAnimation()
                charaterAni.setImageDrawable(workAni)
                workAni.start()
            }
        }
        fun finishWork(){
            waitAni= resources.getDrawable(R.drawable.blacksmith_wait) as AnimationDrawable
            Log.i("BBB","休息",null)
            charaterAni.clearAnimation()
            charaterAni.setImageDrawable(waitAni)
            waitAni.start()
        }

}