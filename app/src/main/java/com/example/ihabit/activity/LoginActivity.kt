package com.example.ihabit.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.ihabit.R
import com.example.ihabit.viewModel.MainViewModel
import com.example.ihabit.viewModel.SignViewModel

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val viewModel= SignViewModel()
        val viewModel2= MainViewModel()
        val enterAccount=findViewById<EditText>(R.id.enteraccount)
        val enterPassword=findViewById<EditText>(R.id.enterpassword)
        val forgetPassword=findViewById<TextView>(R.id.forgetPassword)

        val logButton=findViewById<TextView>(R.id.loginButton)
        logButton.setOnClickListener {
            val account=enterAccount.text.toString()
            val password=enterPassword.text.toString()
            viewModel.loginPost(account,password)
        }



        forgetPassword.setOnClickListener {

            if(enterAccount.text.isEmpty()){
                Toast.makeText(
                    this, "請輸入信箱 !",
                    Toast.LENGTH_SHORT
                ).show();
            }else{
            viewModel2.getPassword(enterAccount.text.toString())
                Toast.makeText(
                    this, "新密碼已發信致信箱 !",
                    Toast.LENGTH_SHORT
                ).show();
            }



        }


        viewModel.userId.observe(this){it->
            if(it==0){
                Toast.makeText(
                    this, "帳號密碼有誤 !",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else{
                Log.i("okhttp",it.toString(),null)
                val intent = Intent(baseContext, MainActivity::class.java)
                val account=enterAccount.text.toString()
                intent.putExtra("user_id", it.toString())
                intent.putExtra("email",account)
                startActivity(intent);
            }
        }

        //新會員註冊
        val newLogin=findViewById<TextView>(R.id.newAccountDone)
        newLogin.setOnClickListener {
            val intent = Intent(this,AssignActivity ::class.java)
            startActivityForResult(intent,0)
        }



    }

    @CallSuper
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus;
            if (view != null) {
                if (isShouldHideKeyBord(view, ev)) {
                    hideSoftInput(view?.windowToken)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyBord(v: View, ev: MotionEvent): Boolean {
        if (v != null && (v is EditText)) {
            val l: IntArray = intArrayOf(0, 0)
            v.getLocationInWindow(l);
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth();
            return !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom);
        }
        return false;
    }

    private fun hideSoftInput(token: IBinder) {
        if (token != null) {
            val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}