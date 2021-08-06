package com.example.ihabit.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.example.ihabit.AchievementFragment
import com.example.ihabit.MainFrameFragment
import com.example.ihabit.R
import com.example.ihabit.SettingFragment
import com.example.ihabit.data.SingletonUserId
import com.example.ihabit.data.UserInfo
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {


    val viewModel=MainViewModel()



   lateinit var  settingFragment : SettingFragment
   lateinit var achievementFragment : AchievementFragment
    val fragmentManager: FragmentManager = supportFragmentManager
    var fragmentTransaction = fragmentManager.beginTransaction()
    var currentUser:UserInfo?=null
    var idForResume by Delegates.notNull<Int>()
    var PACKAGE_NAME: String? = null
    var tmpUser=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PACKAGE_NAME = applicationContext.packageName;
        //登入後拿到user的id
        val userId = intent.getStringExtra("user_id")!!.toInt()
        tmpUser=userId
        idForResume=userId
        val email= intent.getStringExtra("email")!!
        SingletonUserId.getInstance()?.setID(userId)
        val tmp=AchievementFragment(userId, email,true)
        achievementFragment=tmp

        viewModel.getUserInfo(userId,email)
        settingFragment=SettingFragment(userId,email)




        //使用者資料有更新舊開啟新的main frag
        viewModel.loginUser.observe(this){it->
            val mainFrameFragment=MainFrameFragment(this,it,userId)
            currentUser=viewModel.loginUser.value
            startMainFragment(mainFrameFragment)
            homePage.setImageResource(R.drawable.home_f)
            achievement.setImageResource(R.drawable.medal_n)
            setting.setImageResource(R.drawable.btn_profile_n__2_)
        }




        //


        val homepage = findViewById<ImageView>(R.id.homePage)
        val achievement = findViewById<ImageView>(R.id.achievement)
        val setting = findViewById<ImageView>(R.id.setting)

        //回到主Fragment
        homepage.setOnClickListener {
            if(currentUser!=null){
                homePage.setImageResource(R.drawable.home_f)
                achievement.setImageResource(R.drawable.medal_n)
                setting.setImageResource(R.drawable.btn_profile_n__2_)
                val mainFrameFragment = MainFrameFragment(this,viewModel.loginUser.value!!,userId)
                startMainFragment(mainFrameFragment)
            }
        }
        //到成就Fragment
        achievement.setOnClickListener {
            homePage.setImageResource(R.drawable.home_n)
            achievement.setImageResource(R.drawable.medal_f)
            setting.setImageResource(R.drawable.btn_profile_n__2_)
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.mainFragment,
                achievementFragment,
                "achievementFragment"
            ).commit()
        }
        //到設定Fragment
        setting.setOnClickListener {
            homePage.setImageResource(R.drawable.home_n)
            achievement.setImageResource(R.drawable.medal_n)
            setting.setImageResource(R.drawable.stats_btn)
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.mainFragment,
                settingFragment,
                "settingFragment"
            ).commit()
        }



    }

    override fun onBackPressed() {
//        super.onBackPressed()
        homePage.setImageResource(R.drawable.home_f)
        achievement.setImageResource(R.drawable.medal_n)
        setting.setImageResource(R.drawable.btn_profile_n__2_)
        val mainFrameFragment = MainFrameFragment(this,viewModel.loginUser.value!!,tmpUser)
        startMainFragment(mainFrameFragment)
    }


    //開啟主frag方法
    private fun startMainFragment(frameFragment: MainFrameFragment){
        fragmentTransaction = fragmentManager.beginTransaction()
        homePage.setImageResource(R.drawable.home_f)
        achievement.setImageResource(R.drawable.medal_n)
        setting.setImageResource(R.drawable.btn_profile_n__2_)
        fragmentTransaction.replace(R.id.mainFragment, frameFragment, "mainFragment")
            .commit()
    }
    fun startAcheFragment(frameFragment: AchievementFragment){
        fragmentTransaction = fragmentManager.beginTransaction()
        homePage.setImageResource(R.drawable.home_n)
        achievement.setImageResource(R.drawable.medal_f)
        setting.setImageResource(R.drawable.btn_profile_n__2_)
        fragmentTransaction.replace(R.id.mainFragment, frameFragment, "mainFragment")
            .commit()
    }

    //onResume叫main frag
   override  fun onResume() {
        super.onResume()
        if(currentUser!=null){
            homePage.setImageResource(R.drawable.home_f)
            achievement.setImageResource(R.drawable.medal_n)
            setting.setImageResource(R.drawable.btn_profile_n__2_)
            val mainFrameFragment = MainFrameFragment(this,viewModel.loginUser.value!!,idForResume)
            startMainFragment(mainFrameFragment)
        }
    }




}