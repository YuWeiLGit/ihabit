package com.example.ihabit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import com.example.ihabit.activity.BossActivity
import com.example.ihabit.activity.LoginActivity
import com.example.ihabit.activity.NewPassWordActivity
import com.example.ihabit.data.UpdateUser
import com.example.ihabit.viewModel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment(userid: Int, email: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val userid = userid
    private val email = email
    private val viewModel = MainViewModel()
    private var isChangeing=false
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
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userHead = view.findViewById<ImageView>(R.id.userHead)
        val playerName = view.findViewById<TextView>(R.id.playerName)
        val userEmail=view.findViewById<TextView>(R.id.email)
        val changePassword=view.findViewById<TextView>(R.id.changePassword)
        val logOut=view.findViewById<TextView>(R.id.logOut)
        val playerEditName = view.findViewById<EditText>(R.id.playerEditName)
        playerEditName.visibility=View.INVISIBLE

        val changeName = view.findViewById<TextView>(R.id.changeName)
        changeName.setOnClickListener {
            if(!isChangeing){
                isChangeing=true
                changeName.text="確認"
                playerName.visibility=View.INVISIBLE
                playerEditName.visibility=View.VISIBLE
            }else{
                isChangeing=false
                changeName.text="修改"
                val newName=playerEditName.text.toString()
                val tmp=UpdateUser(userid,email,newName,null,null,null,null,null)
                playerName.visibility=View.VISIBLE
                playerEditName.visibility=View.INVISIBLE
                playerName.text=newName
                viewModel.putUserInfo(tmp)
            }

        }
        viewModel.result.observe(viewLifecycleOwner){
            viewModel.getUserInfo(userid, email)
        }
        viewModel.getUserInfo(userid, email)



        viewModel.loginUser.observe(viewLifecycleOwner) { it ->
            if (it.career == "弓箭手") {
                userHead.setImageResource(R.drawable.ranger_head)
            } else if (it.career == "法師") {
                userHead.setImageResource(R.drawable.healer_head)
            }
            playerName.text = it.name
            playerEditName.setText(it.name)
            playerEditName.visibility=View.INVISIBLE
            userEmail.text=it.email
        }

        logOut.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            requireActivity().startActivity(intent)
        }
        changePassword.setOnClickListener {
            val intent = Intent(activity, NewPassWordActivity::class.java)
            intent.putExtra("userid",userid.toString())
            intent.putExtra("email",email)
            requireActivity().startActivity(intent)
        }



    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, userid: Int, email: String) =
            SettingFragment(userid, email).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}