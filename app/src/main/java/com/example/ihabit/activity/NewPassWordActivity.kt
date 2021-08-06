package com.example.ihabit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.observe
import com.example.ihabit.R
import com.example.ihabit.viewModel.MainViewModel

class NewPassWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pass_word)
        val userId = intent?.getStringExtra("userid")
        val email=  intent?.getStringExtra("email")
        val viewModel=MainViewModel()
        val logOut=findViewById<TextView>(R.id.backLogOut)
        val oldPassword=findViewById<EditText>(R.id.oldPassWord)
        val newPassword=findViewById<EditText>(R.id.newPassword)
        val newPasswordAgain=findViewById<EditText>(R.id.enterNewPasswordAgain)
        val okUpdate=findViewById<TextView>(R.id.okUpdate)



        logOut.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
        }


        okUpdate.setOnClickListener {
            if(newPassword.text.toString()!=newPasswordAgain.text.toString()){
                Toast.makeText(
                    this, "新密碼與驗證不符 !",
                    Toast.LENGTH_SHORT
                ).show();
            }else{
            viewModel.putPassword(userId!!.toInt(),email!!,oldPassword.text.toString(),newPassword.text.toString())
            }
        }

        viewModel.changePass.observe(this){
            if(it.data){
                Toast.makeText(
                    this, "修改成功 !",
                    Toast.LENGTH_SHORT
                ).show();
            }else{
                val tmp=it.message
                Toast.makeText(
                    this, tmp,
                    Toast.LENGTH_SHORT
                ).show();


            }



        }




    }
}