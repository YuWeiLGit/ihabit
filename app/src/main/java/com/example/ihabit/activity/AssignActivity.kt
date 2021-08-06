package com.example.ihabit.activity

import android.app.Activity
import android.os.Bundle
import android.os.IBinder
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.ihabit.R
import com.example.ihabit.viewModel.SignViewModel
import com.example.ihabit.data.UserSign
import java.util.regex.Pattern

class AssignActivity : AppCompatActivity() {


    val signViewModel = SignViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign)
        val doneButton = findViewById<TextView>(R.id.insideNewAccountDone)
        val name = findViewById<EditText>(R.id.entername)
        val email = findViewById<EditText>(R.id.enteremail)
        val password = findViewById<EditText>(R.id.enterpassword)
        val vaild = findViewById<TextView>(R.id.vaildEmail)
        var emailIsNotOk = true
        var career="戰士"
        val warrior=findViewById<ImageView>(R.id.warrior)
        val right=findViewById<ImageView>(R.id.careerright)
        val left=findViewById<ImageView>(R.id.careerleft)
        val careerName=findViewById<TextView>(R.id.careerName)
        val careerDes=findViewById<TextView>(R.id.careerDes)

        //如果信箱驗證過才可以註冊，收到註冊成功201 finish
        doneButton.setOnClickListener {
            val signEmail = email.text.toString()
            val signName = name.text.toString()
            val signPass = password.text.toString()
            val userSign = UserSign(signName, signEmail, signPass,careerConvert(career))
            if (emailIsNotOk) {
                Toast.makeText(
                    this, "請先認證email !",
                    Toast.LENGTH_SHORT
                ).show();
            } else {
                signViewModel.userSignPost(userSign)
            }

        }
        signViewModel.logincode.observe(this) { it ->
            if (it == 200) {
                finish()
            } else {
                Toast.makeText(
                    this, "$it" + "註冊失敗 !",
                    Toast.LENGTH_SHORT
                ).show();
            }

        }
        //職業點擊監聽
        warrior.setOnClickListener {
            career="戰士"
            careerName.text=career
            careerDes.text="血量多但攻擊力低"

        }
        right.setOnClickListener {
            when(career){
                "戰士" ->{
                    career="法師"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack2)
                    careerDes.text="精通元素的奧秘，瞬間傷害的強大蓋過了天生體質上的缺陷，沒有人能小看他的魔法"
                }
                "法師" ->{
                    career="弓箭手"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack1)
                    careerDes.text="森林中的狙擊手，百步穿楊的精準射擊，從來沒有人能躲過他的弓箭"
                }
                "弓箭手" ->{
                    career="戰士"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack3__81542)
                    careerDes.text="剛勇無比的職業，傷害雖然遜於其他兩個職業，但天生血量與防禦素質都非常的高"
                }
            }
        }
        left.setOnClickListener {
            when(career){
                "戰士" ->{
                    career="弓箭手"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack1)
                    careerDes.text="森林中的狙擊手，百步穿楊的精準射擊，從來沒有人能躲過他的弓箭"
                }
                "法師" ->{
                    career="戰士"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack3__81542)
                    careerDes.text="剛勇無比的職業，傷害雖然遜於其他兩個職業，但天生血量與防禦素質都非常的高"

                }
                "弓箭手" ->{
                    career="法師"
                    careerName.text=career
                    warrior.setImageResource(R.drawable.attack2)
                    careerDes.text="精通元素的奧秘，瞬間傷害的強大蓋過了天生體質上的缺陷，沒有人能小看他的魔法"

                }
            }
        }
        ///審略符號 ellip size m end


        //email監聽
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                emailIsNotOk = false
            }

        })
        //檢查email是否在資料庫已經存在
        vaild.setOnClickListener {
            if (isValidEmailId(email.text.toString())) {
                signViewModel.validateEmail(email.text.toString())
            } else {
                Toast.makeText(
                    this, "無效的email格式 !",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
        signViewModel.isValidate.observe(this) { it ->
            emailIsNotOk = it[it.size - 1]
            if (it[it.size - 1]) {
                Toast.makeText(
                    this, "信箱已被使用 !",
                    Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                    this, "此信箱認證成功 !",
                    Toast.LENGTH_SHORT
                ).show();
            }

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



    //職業轉換
    private fun careerConvert(career: String): Int {
        return when (career) {
            "弓箭手" -> 1
            "法師" -> 2
            else -> 3
        }
    }

    //驗證email
    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}