package com.example.ihabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.example.ihabit.data.NodeObj
import com.example.ihabit.data.UserInfo
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_achievement.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AchievementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AchievementFragment(userId: Int, email: String,useAni:Boolean) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val usid = userId
    val email = email
    lateinit var currentUser: UserInfo
    var tPoint = 0
    var useAni=useAni

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anim1 = view.findViewById<ImageView>(R.id.forAnim)
        val anim2 = view.findViewById<TextView>(R.id.forAnim2)
        val ani = AnimationUtils.loadAnimation(activity, R.anim.rotate_animation)

        val bottom = view.findViewById<ImageView>(R.id.buttonImage)
        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        val text = view.findViewById<TextView>(R.id.poweupText)
        val text2 = view.findViewById<TextView>(R.id.poweupText2)
        val text3 = view.findViewById<TextView>(R.id.poweupText3)
        val outside = view.findViewById<ImageView>(R.id.textOutside)
        val outside2 = view.findViewById<ImageView>(R.id.textOutside2)
        val outside3 = view.findViewById<ImageView>(R.id.textOutside3)
        val cover = view.findViewById<TextView>(R.id.cover)

        //初始動畫監聽
        if(useAni){
            ani.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    bottom.visibility = View.INVISIBLE
                    text.visibility = View.INVISIBLE
                    text2.visibility = View.INVISIBLE
                    text3.visibility = View.INVISIBLE
                    outside.visibility = View.INVISIBLE
                    outside2.visibility = View.INVISIBLE
                    outside3.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    anim1.visibility = View.INVISIBLE
                    anim2.visibility = View.INVISIBLE
                    bottom.startAnimation(fadeIn)
                    bottom.visibility = View.VISIBLE
                    text.startAnimation(fadeIn)
                    text.visibility = View.VISIBLE
                    text2.startAnimation(fadeIn)
                    text2.visibility = View.VISIBLE
                    text3.startAnimation(fadeIn)
                    text3.visibility = View.VISIBLE
                    outside.startAnimation(fadeIn)
                    outside.visibility = View.VISIBLE
                    outside2.startAnimation(fadeIn)
                    outside2.visibility = View.VISIBLE
                    outside3.startAnimation(fadeIn)
                    outside3.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            fadeIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    cover.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            anim1.startAnimation(ani)
        }
            else{
            cover.visibility = View.INVISIBLE
            anim1.visibility = View.INVISIBLE
            anim2.visibility = View.INVISIBLE
            bottom.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
            text2.visibility = View.VISIBLE
            text3.visibility = View.VISIBLE
            outside.visibility = View.VISIBLE
            outside2.visibility = View.VISIBLE
            outside3.visibility = View.VISIBLE
            }




        ///////////////////邏輯相關
        val viewModel = MainViewModel()
        val point = view.findViewById<TextView>(R.id.leftpoint)
        //拿到有多少技能點
        viewModel.getUserInfo(usid, email)
        viewModel.loginUser.observe(owner = viewLifecycleOwner) { it ->
            currentUser = it
            tPoint = it.talentPoint
            point.text = "剩餘點數: ${it.talentPoint}"
        }
        //拿到目前那些已經有點過了
        viewModel.getTalentPoint(usid)
        var allTalent: MutableList<NodeObj> = mutableListOf()
        viewModel.allTalent.observe(owner = viewLifecycleOwner) { it ->
            allTalent = it
            for (i in 0 until it.size) {
                if (it[i].nodeId == 2) {
                    if (it[i].hasNode) {
                        no2.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 8) {
                    if (it[i].hasNode) {
                        no8.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 14) {
                    if (it[i].hasNode) {
                        no14.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 20) {
                    if (it[i].hasNode) {
                        no20.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 26) {
                    if (it[i].hasNode) {
                        no26.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 32) {
                    if (it[i].hasNode) {
                        no32.setImageResource(R.drawable.difficulty_point_on)
                    }
                }
            }

            //往左邊的frag
            outside3.setOnClickListener {
                val fragmentManager: FragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
                val powerFragment=MageFragment(usid,email)
                fragmentTransaction.replace(
                    R.id.mainFragment,
                    powerFragment,
                    "achievementFragment"
                ).commit()



            }
            //往右邊的frag
            outside2.setOnClickListener {
                val fragmentManager: FragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
                val powerFragment=PowerFragment(usid,email)
                fragmentTransaction.replace(
                    R.id.mainFragment,
                    powerFragment,
                    "achievementFragment"
                ).commit()
            }

            ////所有節點的點擊監聽
            val no2 = view.findViewById<ImageView>(R.id.no2)
            val no8 = view.findViewById<ImageView>(R.id.no8)
            val no14 = view.findViewById<ImageView>(R.id.no14)
            val no20 = view.findViewById<ImageView>(R.id.no20)
            val no26 = view.findViewById<ImageView>(R.id.no26)
            val no32 = view.findViewById<ImageView>(R.id.no32)
            //檢查節點是否已經被點過
            no2.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(2, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 2) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous, allTalent)) {
                                viewModel.postNewTalent(usid, 2, tPoint - 1)
                                no2.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 2, tPoint - 1)
                            no2.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no8.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(8, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 8) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch!!.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous!!, allTalent)) {
                                viewModel.postNewTalent(usid, 8, tPoint - 1)
                                no8.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 8, tPoint - 1)
                            no8.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no14.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(14, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 14) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch!!.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous!!, allTalent)) {
                                viewModel.postNewTalent(usid, 14, tPoint - 1)
                                no14.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 14, tPoint - 1)
                            no14.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no20.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(20, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 20) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch!!.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous!!, allTalent)) {
                                viewModel.postNewTalent(usid, 20, tPoint - 1)
                                no20.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 20, tPoint - 1)
                            no20.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no26.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(26, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 26) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch!!.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous!!, allTalent)) {
                                viewModel.postNewTalent(usid, 26, tPoint - 1)
                                no26.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 26, tPoint - 1)
                            no26.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no32.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(32, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 32) {
                                //得到具體的nodelistObj
                                touch = allTalent[i]
                            }
                        }
                        //看是否有上個點
                        if (touch != null) {
                            previous = touch!!.lastNode
                        }
                        //看上個點是否被點過
                        if (previous != null) {
                            //點過就發api
                            if (isTouch(previous!!, allTalent)) {
                                viewModel.postNewTalent(usid, 32, tPoint - 1)
                                no32.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 32, tPoint - 1)
                            no32.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            viewModel.taskResult.observe(owner = viewLifecycleOwner) {
                viewModel.getTalentPoint(usid)
                viewModel.getUserInfo(usid, email)
            }


        }
    }


    private fun isTouch(id: Int, talent: MutableList<NodeObj>): Boolean {
        for (i in 0 until talent.size) {
            if (talent[i].nodeId == id) {
                if (talent[i].hasNode) {
                    return true
                }
            }
        }
        return false
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, userId: Int, email: String,useAni:Boolean) =
            AchievementFragment(userId, email,useAni).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}