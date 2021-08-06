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
import com.example.ihabit.adapter.ForgeAdapter
import com.example.ihabit.data.gear
import com.example.ihabit.viewModel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgeFragment(userid:Int,blackSmithActivity: BlackSmithActivity) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel = MainViewModel()
    private val forgeAdapter=ForgeAdapter(userid,blackSmithActivity,this,viewModel)
    private val activity=blackSmithActivity
    private val userid=userid
    private var gearOrder: MutableList<gear> = mutableListOf()
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
        return inflater.inflate(R.layout.fragment_forge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.forgeRecycler)
        val tmpLayOutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager =tmpLayOutManager
        recyclerView.adapter = forgeAdapter
        refresh()
        viewModel.userItem.observe(owner = viewLifecycleOwner){
            val item=it.allItems
            forgeAdapter.refresh(item)
            if(gearOrder.size==it.allItems.size){

                val item=it.allItems
                val tmp: MutableList<gear> = mutableListOf()
                for(i in 0 until gearOrder.size){
                    for(j in 0 until item.size){
                        if(gearOrder[i].type==item[j].type)
                        tmp.add(item[j])
                    }
                }
                forgeAdapter.refresh(tmp)
            }else{
                val item=it.allItems
                forgeAdapter.refresh(item)
            }
            val coin=it.money
            activity.moneyChange(coin)
            coinUpdate(coin)


        }



        viewModel.taskResult2.observe(owner = activity){it->
            Log.i("BBB","排序",null)
              refresh()
        }


    }

    fun refresh(){
        Log.i("BBB","排序2",null)
        viewModel.getItem(userid.toString())
    }


    fun changeOrder(gear:MutableList<gear>){
        gearOrder=gear
    }

    fun coinUpdate(coin:Int){
        this.forgeAdapter.coinUpdate(coin)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String,userid:Int,blackSmithActivity: BlackSmithActivity) =
            ForgeFragment(userid,blackSmithActivity).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}