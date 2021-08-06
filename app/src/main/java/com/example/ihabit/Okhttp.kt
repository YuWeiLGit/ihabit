package com.example.ihabit

import android.util.Log
import com.example.ihabit.data.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class Okhttp {

    val addHabitUrl = "http://34.80.130.206/AddHabit"
    val signupUrl = "http://34.80.130.206/User/SignUp"
    val client = OkHttpClient()
    val gson = Gson()



    //get 全稱號
    fun getTitle(userid: Int):Request{
        val url = "http://34.80.130.206/User/UserTitle/$userid"
        return Request.Builder().url(url).build()
    }
    suspend fun getTitleApi(request: Request): MutableList<String> {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "get title failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<MutableList<String>>(
                            body,
                            object : TypeToken<MutableList<String>>() {}.type
                        )
                        Log.i("okhttp", "get title success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get title unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }







    //新增天賦
    fun postNewTalent(userid: Int, nodid: Int, talentPoint: Int): Request {
        val url = "http://34.80.130.206/Talent/Add"
        val body = "{\"userId\":\"$userid\",\"nodeId\":\"$nodid\",\"talentPoint\":\"$talentPoint\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun postTalent(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "delete done date failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "add talent success", null)
                    } else {
                        Log.i("okhttp", "delete task unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }

    //post 打怪勝利
    fun postWin(userid: Int,monsterid:Int):Request{
        val url = "http://34.80.130.206/WinGame"
        val body = "{\"userId\":\"$userid\",\"monsterId\":\"$monsterid\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }
    suspend fun postWin(request: Request):BackOfWin {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post win failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<BackOfWin>(
                            body,
                            object : TypeToken<BackOfWin>() {}.type
                        )
                        Log.i("okhttp", "post win success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("CCC", "post win unsuccess", null)
                        Log.i("CCC", httpCode.toString(), null)
                    }

                }

            })
        }
    }






    //道具升階
    fun postUpgradeItem(userid: Int,gearId: Int):Request{
        val url = "http://34.80.130.206/UpPropLevel"
        val body = "{\"userId\":\"$userid\",\"propId\":\"$gearId\"}"
        Log.i("okhttp", body, null)
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun postUpgradeItem(request: Request):UpgradeBackFull {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "upgrade failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<UpgradeBackFull>(
                            body,
                            object : TypeToken<UpgradeBackFull>() {}.type
                        )
                        Log.i("okhttp", "post upgrage success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))


                    } else {
                        Log.i("CCC", "post upgrade unsuccess", null)
                        Log.i("CCC", httpCode.toString(), null)
                    }

                }

            })
        }
    }





    //道具鍛造
    fun postForgeItem(userid: Int,gearId:Int):Request{
        val url = "http://34.80.130.206/BuildProp"
        val body = "{\"userId\":\"$userid\",\"propId\":\"$gearId\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun postForgeItem(request: Request):ForgeBackFull {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "forge failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<ForgeBackFull>(
                            body,
                            object : TypeToken<ForgeBackFull>() {}.type
                        )
                        Log.i("okhttp", "post win success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("CCC", "post forge unsuccess", null)
                        Log.i("CCC", httpCode.toString(), null)
                    }
                }

            })
        }
    }



    //get 怪物資訊
    fun getMonster(level:Int):Request{
        val url = "http://34.80.130.206/MonsterInfo/$level"
        return Request.Builder().url(url).build()
    }
    suspend fun getMonsterApi(request: Request): Monster {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "get monster failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<Monster>(
                            body,
                            object : TypeToken<Monster>() {}.type
                        )
                        Log.i("okhttp", "get monster success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get monster unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }





    //get拿到會員道具
    fun getUserItem(userid: Int):Request{
        val url = "http://34.80.130.206/UserEquip/$userid"
        return Request.Builder().url(url).build()
    }

    suspend fun getItems(request: Request): UserItem {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "get item failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<UserItem>(
                            body,
                            object : TypeToken<UserItem>() {}.type
                        )
                        Log.i("okhttp", "get item success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get item unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }
    //delete刪除tag
    fun deleteTag(habitId: Int,tagId: Int): Request {
        val url = "http://34.80.130.206/DeleteHabitTag"
        val body = "{\"habitId\":\"$habitId\",\"tagId\":\"$tagId\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").delete(requestBody)
            .url(url).build()
    }

    suspend fun deleteTagOfTask(request: Request): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "delete tag failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "delete tag success", null)
                    } else {
                        Log.i("okhttp", "delete tag bad", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }








    //put 修改會員資料
    fun changeUserInfo(updateUser: UpdateUser): Request {
        val url = "http://34.80.130.206/User/UpdateUser"
        val body = gson.toJson(updateUser)
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").put(requestBody)
            .url(url).build()
    }

    suspend fun putUserInfo(request: Request): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "change userInfo failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "change Info success", null)
                    } else {
                        Log.i("okhttp", "change Info", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }




    //get那些技能點過
    fun getTalentPoint(userid: Int): Request {
        val url = "http://34.80.130.206/Talent/NodeList/$userid"
        return Request.Builder().url(url).build()
    }

    suspend fun getTalent(request: Request): TalentInfo {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "delete done date failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<TalentInfo>(
                            body,
                            object : TypeToken<TalentInfo>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get taskOverView unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }


    //delete習慣
    fun deleteTask(habitId: Int, userid: Int): Request {
        val url = "http://34.80.130.206/DeleteHabit/$habitId/$userid"
        val body = "{\"habitId\":\"$habitId\",\"userId\":\"$userid\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder()
            .url(url)
            .delete(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()
    }


    suspend fun deleteTaskApi(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "delete done date failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "delete task success", null)
                    } else {
                        Log.i("okhttp", "delete task unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }


    //put 移除標籤
    fun closeTag(userid: Int, tagId: Int): Request {
        val url = "http://34.80.130.206/CloseTag"
        val body = "{\"userId\":\"$userid\",\"tagId\":\"$tagId\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").put(requestBody)
            .url(url).build()
    }

    suspend fun putDeleteTag(request: Request): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "delete tag success", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    val body = response.body!!.string()
                    val itemList = JSONObject(body)
                    Log.i("okhttp", "$itemList", null)
                    val result = itemList.get("data") as Boolean
                    continuation.resumeWith(Result.success(result))
                }

            })
        }
    }

    //post新曾tag
    fun postTagOfTask(habitId: Int, tagId: Int): Request {
        val url = "http://34.80.130.206/AddHabitTag"
        val body = "{\"habitId\":\"$habitId\",\"tagId\":\"$tagId\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun postNewTagOfTask(request: Request) {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post new tag of task failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "post new tag of tasksuccess", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }

                }

            })
        }
    }


    //post新增標籤
    fun addNewTag(tag: String, userid: Int): Request {
        val url = "http://34.80.130.206/AddTag"
        val body = "{\"tagName\":\"$tag\",\"userId\":\"$userid\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun postNewTag(request: Request) {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "email get failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "post new tag success", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }

                }

            })
        }
    }


    //email認證相關
    fun getEmailIsExist(email: String): Request {
        val url = "http://34.80.130.206/User/isEmailExist/$email"
        return Request.Builder().url(url).build()
    }

    suspend fun getEmailApi(request: Request): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "email get failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val itemList = JSONObject(body)
                        val result = itemList.get("data") as Boolean
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }

                }

            })
        }
    }

    //post會員資料
    fun postUserInfo(userid: Int, email: String): Request {
        val url = "http://34.80.130.206/User/Info"
        val body = "{\"userId\":\"$userid\",\"email\":\"$email\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder().addHeader("Content-Type", "application/json").post(requestBody)
            .url(url).build()
    }

    suspend fun getUserInfoApi(request: Request): UserInfo {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "userInfo get failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<UserInfo>(
                            body,
                            object : TypeToken<UserInfo>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get userInfo unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }
            })
        }
    }
    //get 習慣項目清單overview
    fun getHabitOverViewForAlarm(userid: Int): Request {
        val url = "http://34.80.130.206/GetHabitList/$userid?isClose=true"
        return Request.Builder().url(url).build()
    }

    suspend fun getHabitOverViewForAlarm(request: Request): MutableList<TaskOverView> {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "HabitOverView get failure", null)
                }
                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<MutableList<TaskOverView>>(
                            body,
                            object : TypeToken<MutableList<TaskOverView>>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get taskOverView unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }
            })
        }
    }







    //get 習慣項目清單overview
    fun getHabitOverView(userid: Int): Request {
        val url = "http://34.80.130.206/GetHabitList/$userid?isClose=true"
        return Request.Builder().url(url).build()
    }

    suspend fun getHabitOverView(request: Request): MutableList<TaskOverView> {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "HabitOverView get failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<MutableList<TaskOverView>>(
                            body,
                            object : TypeToken<MutableList<TaskOverView>>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get taskOverView unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }
            })
        }
    }


    //get 個人習慣標籤
    fun getTags(userid: String): Request {
        val url = "http://34.80.130.206/GetTagList/$userid"
        return Request.Builder().url(url).build()
    }

    suspend fun getUserTags(request: Request): MutableList<Tag> {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "userTags get failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<MutableList<Tag>>(
                            body,
                            object : TypeToken<MutableList<Tag>>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "get userTag unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }

                }

            })
        }
    }

    //post新增習慣完成日
    fun postHabitDoneDate(habitId: Int, date: String): Request {
        val url = "http://34.80.130.206/AddHabitDoneDate"
        val body = "{\"habitId\":\"$habitId\",\"date\":\"$date\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun postHabitDoneDate(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post done date failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 201) {
                        Log.i("okhttp", "post success", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }


    //取得習慣細節
    fun getHabitDetail(habitId: Int, userid: Int): Request {
        val url = "http://34.80.130.206/GetHabitDetail/$habitId/$userid"
        return Request.Builder().url(url).build()
    }

    suspend fun getTaskDetail(request: Request): TaskForReturn {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "get detail failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "detail success", null)
                        val body = response.body!!.string()
                        Log.i("okhttp", body.toString(), null)
                        val result = GsonBuilder().create().fromJson<TaskForReturn>(
                            body,
                            object : TypeToken<TaskForReturn>() {}.type
                        )
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "detail unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }



    //get 密碼
    fun getPassword(email: String): Request {
        val url = "http://34.80.130.206/User/ForgetPassWord/$email"
        return Request.Builder().url(url).build()
    }

    suspend fun getPasswordApi(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "get password failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "password success", null)
                    } else {
                        Log.i("okhttp", "password unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }




    //put修改習慣
    fun putUpdateHabit(update: TaskForUpdate): Request {
        val url = "http://34.80.130.206/UpdateHabit"
        val body = gson.toJson(update)
            .toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        Log.i("okhttp", "$body", null)
        return Request.Builder()
            .url(url)
            .put(body)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun putHabitUpdate(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "update failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "update success", null)
                    } else {
                        Log.i("okhttp", "update unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }


    //刪除習慣執行日
    fun deleteHabitDoneDate(habitId: Int, date: String): Request {
        val url = "http://34.80.130.206/DeleteHabitDoneDate/$habitId/$date"
        val body = "{\"habitId\":\"$habitId\",\"date\":\"$date\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder()
            .url(url)
            .delete(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun deleteHabitDoneDate(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "delete done date failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "delete success", null)
                    } else {
                        Log.i("okhttp", "delete unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }

    //會員登入相關
    fun login(email: String, password: String): Request {
        val url = "http://34.80.130.206/User/LogIn"
        val body = "{\"email\":\"$email\",\"password\":\"$password\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun postLoginApi(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "post success", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    val body = response.body!!.string()
                    val itemList = JSONObject(body)
                    Log.i("okhttp", "$itemList", null)
                    val result = itemList.get("data") as Int
                    continuation.resumeWith(Result.success(result))
                }

            })
        }
    }

    //put修改密碼
    fun putPassword(userid: Int,email: String,originPassword:String,newPassword:String): Request {
        val url = "http://34.80.130.206/User/ResetPassword"
        val body = "{\"userId\":\"$userid\",\"email\":\"$email\",\"originPassword\":\"$originPassword\",\"newPassword\":\"$newPassword\"}"
        val requestBody = body.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        Log.i("okhttp", body, null)
        return Request.Builder()
            .url(url)
            .put(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun putUpdatePassword(request: Request): BackChangePassword {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "update failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "detail success", null)
                        val body = response.body!!.string()
                        Log.i("okhttp", body.toString(), null)
                        val result = GsonBuilder().create().fromJson<BackChangePassword>(
                            body,
                            object : TypeToken<BackChangePassword>() {}.type
                        )
                        Log.i("okhttp", "update password success", null)
                        Log.i("okhttp", result.toString(), null)
                        continuation.resumeWith(Result.success(result))
                    } else {
                        Log.i("okhttp", "update password unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                }

            })
        }
    }


    //會員註冊
    suspend fun postApi(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "assign success", null)
                    } else {
                        Log.i("okhttp", "assign unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }

    fun signInPostRequest(userSign: UserSign): Request {
        val url = signupUrl
        val body = gson.toJson(userSign)
            .toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
        return Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()
    }


    //新增任務
    fun toBody(task: Task): RequestBody {
        val json = gson.toJson(task)
        Log.i("OKHTTP", json.toString(), null)
        return json.toRequestBody("application/json: charset=utf-8".toMediaTypeOrNull())
    }

    fun taskPostRequest(task: Task): Request {
        val url = addHabitUrl
        val body = toBody(task)
        Log.i("OKHTTP", body.toString(), null)
        return Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    suspend fun postTask(request: Request): Int {
        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                    Log.i("okhttp", "post failure", null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val httpCode = response.code
                    if (httpCode == 200) {
                        Log.i("okhttp", "post success", null)
                    } else {
                        Log.i("okhttp", "post unsuccess", null)
                        Log.i("okhttp", httpCode.toString(), null)
                    }
                    continuation.resumeWith(Result.success(httpCode))
                }

            })
        }
    }


}