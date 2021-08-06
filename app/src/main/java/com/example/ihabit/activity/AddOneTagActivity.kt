package com.example.ihabit.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.example.ihabit.R
import com.example.ihabit.data.SingletonUserId
import com.example.ihabit.viewModel.MainViewModel

class AddOneTagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_one_tag)
        val userId = SingletonUserId.getInstance()?.getId()!!
        val back = findViewById<TextView>(R.id.editTagBack)
        val text = findViewById<EditText>(R.id.enterTag)
        val viewModel=MainViewModel()


        back.setOnClickListener {
            if (TextUtils.isEmpty(text.text)) {
               finish()
            }else{
            viewModel.addNewTag(text.text.toString(),userId)
                finish()
            }
        }


    }
}