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
import kotlinx.android.synthetic.main.fragment_money.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoneyFragment (userId: Int, email: String) : Fragment() {
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
        return inflater.inflate(R.layout.fragment_money, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val outside3m = view.findViewById<ImageView>(R.id.textOutside3m)
        val outside2m = view.findViewById<ImageView>(R.id.textOutside2m)

        ///////////////////邏輯相關
        val viewModel = MainViewModel()
        val point = view.findViewById<TextView>(R.id.leftpointm)
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
                if (it[i].nodeId == 6) {
                    if (it[i].hasNode) {
                        no6.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 12) {
                    if (it[i].hasNode) {
                        no12.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 18) {
                    if (it[i].hasNode) {
                        no18.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 24) {
                    if (it[i].hasNode) {
                        no24.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 30) {
                    if (it[i].hasNode) {
                        no30.setImageResource(R.drawable.difficulty_point_on)
                    }
                } else if (it[i].nodeId == 36) {
                    if (it[i].hasNode) {
                        no36.setImageResource(R.drawable.difficulty_point_on)
                    }
                }
            }


            ////所有節點的點擊監聽
            val no6 = view.findViewById<ImageView>(R.id.no6)
            val no12 = view.findViewById<ImageView>(R.id.no12)
            val no18 = view.findViewById<ImageView>(R.id.no18)
            val no24 = view.findViewById<ImageView>(R.id.no24)
            val no30 = view.findViewById<ImageView>(R.id.no30)
            val no36 = view.findViewById<ImageView>(R.id.no36)
            //檢查節點是否已經被點過
            no6.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(6, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 6) {
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
                                viewModel.postNewTalent(usid, 6, tPoint - 1)
                                no6.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 6, tPoint - 1)
                            no6.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no12.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(12, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 12) {
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
                                viewModel.postNewTalent(usid, 12, tPoint - 1)
                                no12.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 12, tPoint - 1)
                            no12.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no18.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(18, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 18) {
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
                                viewModel.postNewTalent(usid, 18, tPoint - 1)
                                no18.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 18, tPoint - 1)
                            no18.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no24.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(24, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 24) {
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
                                viewModel.postNewTalent(usid, 24, tPoint - 1)
                                no24.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 24, tPoint - 1)
                            no24.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no30.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(30, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 30) {
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
                                viewModel.postNewTalent(usid, 30, tPoint - 1)
                                no30.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 30, tPoint - 1)
                            no30.setImageResource(R.drawable.difficulty_point_on)
                        }
                    }
                }
            }
            no36.setOnClickListener {
                var touch: NodeObj? = null
                var previous: Int? = null
                //如果沒有被點過
                if (!isTouch(36, allTalent)) {
                    if (tPoint > 0) {
                        for (i in 0 until allTalent.size) {
                            if (allTalent[i].nodeId == 36) {
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
                                viewModel.postNewTalent(usid, 36, tPoint - 1)
                                no36.setImageResource(R.drawable.difficulty_point_on)
                            }
                        }
                        //沒有上個節點發api
                        else {
                            viewModel.postNewTalent(usid, 36, tPoint - 1)
                            no36.setImageResource(R.drawable.difficulty_point_on)
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
        outside3m.setOnClickListener {

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
        //往右邊的frag
        outside2m.setOnClickListener {
            val fragmentManager: FragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)
            val achievementFragment = MageFragment(usid, email)
            fragmentTransaction.replace(
                R.id.mainFragment,
                achievementFragment,
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
            MoneyFragment(userId, email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}