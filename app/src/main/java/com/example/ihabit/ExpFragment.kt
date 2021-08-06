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
import kotlinx.android.synthetic.main.fragment_def.*
import kotlinx.android.synthetic.main.fragment_exp.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpFragment(userId: Int, email: String) : Fragment() {
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
        return inflater.inflate(R.layout.fragment_exp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val outside3e = view.findViewById<ImageView>(R.id.textOutside3e)
        val outside2e = view.findViewById<ImageView>(R.id.textOutside2e)

        ///////////////////邏輯相關
        val viewModel = MainViewModel()
        val point = view.findViewById<TextView>(R.id.leftpointe)
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
                if (it[i].nodeId == 5) {
                    if (it[i].hasNode) {
                        no5.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 11) {
                    if (it[i].hasNode) {
                        no11.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 17) {
                    if (it[i].hasNode) {
                        no17.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 23) {
                    if (it[i].hasNode) {
                        no23.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 29) {
                    if (it[i].hasNode) {
                        no29.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 35) {
                    if (it[i].hasNode) {
                        no35.setImageResource(R.drawable.difficulty_point_on)
                    }
                }
            }


            ////所有節點的點擊監聽
            val no5 = view.findViewById<ImageView>(R.id.no5)
            val no11 = view.findViewById<ImageView>(R.id.no11)
            val no17 = view.findViewById<ImageView>(R.id.no17)
            val no23 = view.findViewById<ImageView>(R.id.no23)
            val no29 = view.findViewById<ImageView>(R.id.no29)
            val no35 = view.findViewById<ImageView>(R.id.no35)

            //檢查節點是否已經被點過
            no5.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(5, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 5) {
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
                                viewModel.postNewTalent(usid, 5, tPoint - 1)
                                no5.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 5, tPoint - 1)
                            no5.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no11.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(11, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 11) {
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
                                viewModel.postNewTalent(usid, 11, tPoint - 1)
                                no11.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 11, tPoint - 1)
                            no11.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no17.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(17, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 17) {
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
                                viewModel.postNewTalent(usid, 17, tPoint - 1)
                                no17.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 17, tPoint - 1)
                            no17.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no23.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(23, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 23) {
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
                                viewModel.postNewTalent(usid, 23, tPoint - 1)
                                no23.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 23, tPoint - 1)
                            no23.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no29.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(29, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 29) {
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
                                viewModel.postNewTalent(usid, 29, tPoint - 1)
                                no29.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 29, tPoint - 1)
                            no29.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no35.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(35, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 35) {
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
                                viewModel.postNewTalent(usid, 35, tPoint - 1)
                                no35.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 35, tPoint - 1)
                            no35.setImageResource(R.drawable.difficulty_point_on)
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
        outside3e.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
            val achievementFragment = DefFragment(usid, email)
            fragmentTransaction.replace(
                R.id.mainFragment,
                achievementFragment,
                "achievementFragment"
            ).commit()


        }
        //往右邊的frag
        outside2e.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
            val powerFragment=MoneyFragment(usid,email)
            fragmentTransaction.replace(
                R.id.mainFragment,
                powerFragment,
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
            ExpFragment(userId, email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}