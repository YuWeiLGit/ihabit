package com.example.ihabit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ihabit.ForgeFragment
import com.example.ihabit.UpgradeFragment
import com.example.ihabit.activity.BlackSmithActivity

class BlacksmithPagerAdapter(fm:FragmentManager, bh:Int, userid:Int,blackSmithActivity: BlackSmithActivity,email:String) :FragmentPagerAdapter(fm,bh){

    val userid=userid
    val activity=blackSmithActivity
    val email=email

    override fun getCount(): Int {
       return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> "鍛造"
            else-> "升級"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->ForgeFragment(userid,activity)
            else->UpgradeFragment(userid,activity,email)
        }
    }
}