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
import kotlinx.android.synthetic.main.fragment_power.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DefFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefFragment(userId: Int, email: String) : Fragment() {
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
        return inflater.inflate(R.layout.fragment_def, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val outside3d = view.findViewById<ImageView>(R.id.textOutside3d)
        val outside2d = view.findViewById<ImageView>(R.id.textOutside2d)

        ///////////////////邏輯相關
        val viewModel = MainViewModel()
        val point = view.findViewById<TextView>(R.id.leftpointd)
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
                if (it[i].nodeId == 3) {
                    if (it[i].hasNode) {
                        no3.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 9) {
                    if (it[i].hasNode) {
                        no9.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 15) {
                    if (it[i].hasNode) {
                        no15.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 21) {
                    if (it[i].hasNode) {
                        no21.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 27) {
                    if (it[i].hasNode) {
                        no27.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 33) {
                    if (it[i].hasNode) {
                        no33.setImageResource(R.drawable.difficulty_point_on)
                    }
                }
            }


            ////所有節點的點擊監聽
            val no3 = view.findViewById<ImageView>(R.id.no3)
            val no9 = view.findViewById<ImageView>(R.id.no9)
            val no15 = view.findViewById<ImageView>(R.id.no15)
            val no21 = view.findViewById<ImageView>(R.id.no21)
            val no27 = view.findViewById<ImageView>(R.id.no27)
            val no33 = view.findViewById<ImageView>(R.id.no33)
            //檢查節點是否已經被點過
            no3.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(3, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 3) {
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
                                viewModel.postNewTalent(usid, 3, tPoint - 1)
                                no3.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 3, tPoint - 1)
                            no3.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no9.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(9, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 9) {
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
                                viewModel.postNewTalent(usid, 9, tPoint - 1)
                                no9.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 9, tPoint - 1)
                            no9.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no15.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(15, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 15) {
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
                                viewModel.postNewTalent(usid, 15, tPoint - 1)
                                no15.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 15, tPoint - 1)
                            no15.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no21.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(21, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 21) {
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
                                viewModel.postNewTalent(usid, 21, tPoint - 1)
                                no21.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 21, tPoint - 1)
                            no21.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no27.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(27, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 27) {
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
                                viewModel.postNewTalent(usid, 27, tPoint - 1)
                                no27.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 27, tPoint - 1)
                            no27.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no33.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(33, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 33) {
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
                                viewModel.postNewTalent(usid, 33, tPoint - 1)
                                no33.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 33, tPoint - 1)
                            no33.setImageResource(R.drawable.difficulty_point_on)
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
        outside3d.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
            val achievementFragment = PowerFragment(usid, email)
            fragmentTransaction.replace(
                R.id.mainFragment,
                achievementFragment,
                "achievementFragment"
            ).commit()


        }
        //往右邊的frag
        outside2d.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left)
            val powerFragment=ExpFragment(usid,email)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DefFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, userId: Int, email: String) =
            DefFragment(userId, email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}