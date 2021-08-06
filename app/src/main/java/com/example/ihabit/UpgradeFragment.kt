package com.example.ihabit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.activity.BlackSmithActivity
import com.example.ihabit.adapter.UpgradeAdapter
import com.example.ihabit.viewModel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpgradeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpgradeFragment(userid:Int,blackSmithActivity: BlackSmithActivity,email:String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel=MainViewModel()
    private val upgradeAdapter=UpgradeAdapter(userid,email,blackSmithActivity,viewModel)
    private val activity=blackSmithActivity
    private val userid=userid

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
        return inflater.inflate(R.layout.fragment_upgrade, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.upgradeRecycler)
        val tmpLayOutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager =tmpLayOutManager
        recyclerView.adapter=upgradeAdapter
        viewModel.getItem(userid.toString())

        viewModel.userItem.observe(owner= viewLifecycleOwner){it->
            Log.i("CCC","刷新",null)
            val item=it.allItems
            upgradeAdapter.refresh(item)
            val coin=it.money
            activity.moneyChange(coin)
            upgradeAdapter.coinUpdate(coin)
        }

        viewModel.upgradeBack.observe(owner= viewLifecycleOwner){_->
            viewModel.getItem(userid.toString())
        }


    }
fun refresh(){
    viewModel.getItem(userid.toString())
}
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String,userid:Int,blackSmithActivity: BlackSmithActivity,email:String) =
            UpgradeFragment(userid,blackSmithActivity,email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

