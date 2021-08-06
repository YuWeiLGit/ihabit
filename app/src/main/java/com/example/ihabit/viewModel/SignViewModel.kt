package com.example.ihabit.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.ihabit.Okhttp
import com.example.ihabit.data.UserSign
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {
    val okHttp = Okhttp()
    var isValidate = MutableLiveData<MutableList<Boolean>>()
    var logincode = MutableLiveData<Int>()
    var userId = MutableLiveData<Int>()


    companion object {
        var USER_ID = 0
    }

    //註冊
    fun userSignPost(userSign: UserSign) {
        Log.i("okhttp", "sign", null)
        viewModelScope.launch {
            val tmp = okHttp.postApi(okHttp.signInPostRequest(userSign))
            logincode.value = tmp
        }
    }

    //認證信箱
    fun validateEmail(email: String) {
        viewModelScope.launch {
            val tmp = okHttp.getEmailApi(okHttp.getEmailIsExist(email))
            val tmp2 = isValidate.value
            if (tmp2 != null) {
                tmp2.add(tmp)
                isValidate.value = tmp2
            } else {
                val tmp3 = mutableListOf<Boolean>()
                tmp3.add(tmp)
                isValidate.value = tmp3
            }
        }
    }

    //登入
    fun loginPost(email: String, password: String) {

        viewModelScope.launch {

            val result = okHttp.postLoginApi(okHttp.login(email, password))
            userId.value = result
            USER_ID = result
        }

    }

}