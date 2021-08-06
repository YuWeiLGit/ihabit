package com.example.ihabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.example.ihabit.data.NodeObj
import com.example.ihabit.data.UserInfo
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_achievement.*
import kotlinx.android.synthetic.main.fragment_power.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [powerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PowerFragment(userId: Int, email: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val usid = userId
    val email = email
    lateinit var currentUser: UserInfo
    var tPoint = 0
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
        return inflater.inflate(R.layout.fragment_power, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val outside3p=view.findViewById<ImageView>(R.id.textOutside3p)
        val outside2p=view.findViewById<ImageView>(R.id.textOutside2p)

        ///////////////////邏輯相關
        val viewModel = MainViewModel()
        val point = view.findViewById<TextView>(R.id.leftpointp)
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
                if (it[i].nodeId == 1) {
                    if (it[i].hasNode) {
                        no1.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 7) {
                    if (it[i].hasNode) {
                        no7.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 13) {
                    if (it[i].hasNode) {
                        no13.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 19) {
                    if (it[i].hasNode) {
                        no19.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 25) {
                    if (it[i].hasNode) {
                        no25.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 31) {
                    if (it[i].hasNode) {
                        no31.setImageResource(R.drawable.difficulty_point_on)
                    }
                }
            }




            ////所有節點的點擊監聽
            val no1 = view.findViewById<ImageView>(R.id.no1)
            val no7 = view.findViewById<ImageView>(R.id.no7)
            val no13 = view.findViewById<ImageView>(R.id.no13)
            val no19 = view.findViewById<ImageView>(R.id.no19)
            val no25 = view.findViewById<ImageView>(R.id.no25)
            val no31 = view.findViewById<ImageView>(R.id.no31)
            //檢查節點是否已經被點過
            no1.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(1, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 1) {
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
                                viewModel.postNewTalent(usid, 1, tPoint - 1)
                                no1.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 1, tPoint - 1)
                            no1.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no7.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(7, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 7) {
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
                                viewModel.postNewTalent(usid, 7, tPoint - 1)
                                no7.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 7, tPoint - 1)
                            no7.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no13.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(13, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 13) {
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
                                viewModel.postNewTalent(usid, 13, tPoint - 1)
                                no13.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 13, tPoint - 1)
                            no13.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no19.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(19, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 19) {
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
                                viewModel.postNewTalent(usid, 19, tPoint - 1)
                                no19.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 19, tPoint - 1)
                            no19.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no25.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(25, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 25) {
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
                                viewModel.postNewTalent(usid, 25, tPoint - 1)
                                no25.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 25, tPoint - 1)
                            no25.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no31.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(31, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 31) {
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
                                viewModel.postNewTalent(usid, 31, tPoint - 1)
                                no31.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 31, tPoint - 1)
                            no31.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            viewModel.taskResult.observe(owner = viewLifecycleOwner) {
                viewModel.getTalentPoint(usid)
                viewModel.getUserInfo(usid, email)
            }


        }



        //往左邊的frag
        outside3p.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
            val achievementFragment=AchievementFragment(usid,email,false)
            fragmentTransaction.replace(
                R.id.mainFragment,
                achievementFragment,
                "achievementFragment"
            ).commit()


        }
        //往右邊的frag
        outside2p.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
            val defFragment=DefFragment(usid,email)
            fragmentTransaction.replace(
                R.id.mainFragment,
                defFragment,
                "achievementFragment"
            ).commit()
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
        fun newInstance(param1: String, param2: String, userId: Int, email: String) =
            PowerFragment(userId, email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}