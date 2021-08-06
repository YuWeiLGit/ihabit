package com.example.ihabit.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ihabit.Okhttp
import com.example.ihabit.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


//主頁面使用
class MainViewModel : ViewModel() {


    val okHttp = Okhttp()

    //使用者資料
    var loginUser = MutableLiveData<UserInfo>()

    //使用者全標籤
    val tags = MutableLiveData<MutableList<Tag>>()

    //被選取的標籤
    val selectedTags = MutableLiveData<MutableList<Tag>>()

    //使用者全任務
    val allTasks = MutableLiveData<MutableList<TaskOverView>>()

    //篩選過的標籤
    val selectedTask = MutableLiveData<MutableList<TaskOverView>>()

    //全任務
    var allTaskWisClose = MutableLiveData<MutableList<TaskOverView>>()

    //檢測是否結果回來
    val result = MutableLiveData<Boolean>()

    //檢測是任務回來
    val taskResult = MutableLiveData<Int>()


    //檢視動畫1
    val aniDamage = MutableLiveData<Boolean>()

    //檢測是任務回來
    val taskResult2 = MutableLiveData<Int>()

    //全任務細節
    var allTaskDetail = MutableLiveData<MutableList<TaskForReturn>>()

    //單一任務細節
    val taskDetail = MutableLiveData<TaskForReturn>()

    //全已點天賦
    var allTalent = MutableLiveData<MutableList<NodeObj>>()

    //使用者道具
    val userItem = MutableLiveData<UserItem>()

    //計時器
    val timer = MutableLiveData<Int>()

    //鍛造數值
    val forgeNum = MutableLiveData<Int>()

    //怪物
    val monster = MutableLiveData<Monster>()

    //檢視裝備升接回來
    val upgradeBack = MutableLiveData<Int>()


    //怪物
    val monsterHp = MutableLiveData<Int>()

    //檢視裝備升接回來
    val userHp = MutableLiveData<Int>()

    //進度條動畫
    val forProgress2 = MutableLiveData<Int>()

    //檢視裝備升接回來
    val forProgress = MutableLiveData<Int>()

    //檢視裝備升接回來
    val upgrageresult = MutableLiveData<Int>()

    //戰鬥贏資訊
    val winBattle = MutableLiveData<BackOfWin>()

    //修改密碼
    val changePass = MutableLiveData<BackChangePassword>()

    //使用者稱號
    val userTitle = MutableLiveData<MutableList<String>>()

    //升級結果
    val resultForUpgrade = MutableLiveData<Boolean>()

    //升級結果
    val resultForUpgradeItem = MutableLiveData<gear>()


    //拿到天賦已點
    fun getTalentPoint(userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getTalent(okHttp.getTalentPoint(userid))
            allTalent.value = tmp.nodes
        }
    }

    //刪除習慣
    fun deleteTask(userid: Int, habitId: Int) {
        viewModelScope.launch {
            val tmp = okHttp.deleteTaskApi(okHttp.deleteTask(habitId, userid))
            taskResult.value = tmp
        }
    }

    //拿到使用者稱號
    fun getTitle(userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getTitleApi(okHttp.getTitle(userid))
            userTitle.value = tmp
        }
    }

    //變更密碼
    fun putPassword(userid: Int, email: String, old: String, new: String) {
        viewModelScope.launch {
            val tmp = okHttp.putUpdatePassword(okHttp.putPassword(userid, email, old, new))
            changePass.value = tmp
        }
    }


    fun getPassword(email: String) {
        viewModelScope.launch { okHttp.getPasswordApi(okHttp.getPassword(email)) }
    }


    //變更使用者訊息
    fun putUserInfo(updeteUser: UpdateUser) {
        viewModelScope.launch {
            val tmp = okHttp.putUserInfo(okHttp.changeUserInfo(updeteUser))
            result.value = tmp
        }
    }


    //更新裝備
    fun postForgeItem(userid: Int, itemId: Int) {
        viewModelScope.launch {
            val tmp = okHttp.postForgeItem(okHttp.postForgeItem(userid, itemId))
            forProgress.value = 30
            delay(300)
            forProgress.value = 60
            delay(300)
            forProgress.value = 90
            delay(300)
            forProgress.value = 100
            delay(300)
            forProgress.value = 0
            if (forgeNum.value != null) {
                if (tmp.data != null) {
                    forgeNum.value = tmp.data.propData
                }
            }

            //回傳金錢
            taskResult2.value = 0
        }
    }

    //裝備升級
    fun postUpgrade(userid: Int, itemId: Int) {
        viewModelScope.launch {
            Log.i("CCC","發送",null)
            val tmp = okHttp.postUpgradeItem(okHttp.postUpgradeItem(userid, itemId))
            forProgress2.value = 30
            delay(300)
            forProgress2.value = 60
            delay(300)
            forProgress2.value = 90
            delay(300)
            forProgress2.value = 100
            delay(300)
            forProgress2.value = 0

            if (tmp.message != null) {
                resultForUpgrade.value = tmp.message == "success"
            }
            if (tmp.newPropId != null) {
                upgrageresult.value = tmp.newPropId.level
            }
            upgradeBack.value = 0

        }
    }

    //戰鬥勝利
    fun postWin(userid: Int, monsterId: Int) {
        viewModelScope.launch {
            val tmp = okHttp.postWin(okHttp.postWin(userid, monsterId))
            winBattle.value = tmp
        }


    }


    //取得怪物
    fun getMonster(level: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getMonsterApi(okHttp.getMonster(level))
            monster.value = tmp
        }
    }

    //戰鬥相關數據
    fun setUserHP(hp: Int) {
        userHp.value = hp
    }

    fun setMonsterHp(hp: Int) {
        monsterHp.value = hp
    }

    private fun damageToUser(hp: Int) {
        val tmp = userHp.value
        val newHp = tmp?.minus(hp)
        userHp.value = newHp
    }

    fun healToUser(hp: Int, monsterAtk: Int) {
        val tmp = userHp.value
        val newHp = tmp?.plus(hp)
        userHp.value = newHp
        viewModelScope.launch {
            delay(300)
            aniDamage.value = true
            delay(500)
            aniDamage.value = false
            delay(300)
            damageToUser(monsterAtk)
        }

    }


    fun specialMove(atkTime: Int, atk: Int, monsterAtk: Int) {

        viewModelScope.launch {
            if (atkTime == 2) {
                val tmp = monsterHp.value
                var newHp = tmp?.minus(atk)
                delay(500)
                monsterHp.value = newHp
                delay(500)
                newHp = newHp!! - atk!!
                monsterHp.value = newHp
            } else if (atkTime == 3) {
                val tmp = monsterHp.value
                var newHp = tmp?.minus(atk)
                delay(500)
                monsterHp.value = newHp
                delay(500)
                newHp = newHp!! - atk!!
                monsterHp.value = newHp
                delay(500)
                newHp = newHp!! - atk!!
                monsterHp.value = newHp
            }


            if (monsterHp.value!! >= 0) {
                delay(300)
                aniDamage.value = true
                delay(500)
                aniDamage.value = false
                delay(300)
                damageToUser(monsterAtk)
            }

        }


    }


    fun damageToBoss(hp: Int, monsterAtk: Int) {
        val tmp = monsterHp.value
        val newHp = tmp?.minus(hp)
        viewModelScope.launch {

            delay(500)
            monsterHp.value = newHp

            if (monsterHp.value!! >= 0) {
                delay(300)
                aniDamage.value = true
                delay(500)
                aniDamage.value = false
                delay(300)
                damageToUser(monsterAtk)
            }

        }

    }

    //計時
    fun timer(time: Int) {
        viewModelScope.launch {
            delay(time.toLong())
            timer.value = time
        }


    }

    //新增天賦
    fun postNewTalent(userid: Int, nodId: Int, talentPoint: Int) {
        viewModelScope.launch {
            val tmp = okHttp.postTalent(okHttp.postNewTalent(userid, nodId, talentPoint))
            taskResult.value = tmp
        }
    }

    fun deleteAllTaskDetail() {
        allTaskDetail.value = mutableListOf()
    }


    fun getAllTaskDetail(allHabitId: MutableList<Int>, userid: Int) {
        deleteAllTaskDetail()
        for (i in 0 until allHabitId.size) {
            viewModelScope.launch {
                val tmp = okHttp.getTaskDetail(okHttp.getHabitDetail(allHabitId[i], userid))
                if (allTaskDetail.value == null) {
                    val tmp2: MutableList<TaskForReturn> = mutableListOf(tmp)
                    allTaskDetail.value = tmp2
                } else {
                    val tmp3: MutableList<TaskForReturn> = mutableListOf(tmp)
                    for (i in 0 until allTaskDetail.value!!.size) {
                        tmp3.add(allTaskDetail.value!![i])
                    }
                    allTaskDetail.value = tmp3
                }
            }
        }
    }


    //修改標籤
    fun deleteTagForTask(habitId: Int,tagId:Int){
        viewModelScope.launch {
            okHttp.deleteTagOfTask(okHttp.deleteTag(habitId, tagId))
        }
    }


    //新增任務習慣
    fun postNewTagOftask(habitId: Int,tagId: Int){
        viewModelScope.launch {
            okHttp.postNewTagOfTask(okHttp.postTagOfTask(habitId, tagId))

        }
    }

    //修改習慣
    fun updateHabit(habit: TaskForUpdate) {
        viewModelScope.launch { okHttp.putHabitUpdate(okHttp.putUpdateHabit(habit)) }
    }

    fun selectByTag(tag: String) {
        val selected: MutableList<TaskOverView> = mutableListOf()
        if (allTasks.value != null) {
            if (tag == "全部") {
                selectedTask.value = allTasks.value
            } else {
                for (i in 0 until allTasks.value!!.size) {
                    if (allTasks.value!![i].tags != null) {
                        for (j in 0 until allTasks.value!![i].tags.size) {
                            if (allTasks.value!![i].tags[j].tagName == tag) {
                                selected.add(allTasks.value!![i])
                            }
                        }
                    }
                }
                selectedTask.value = selected
            }
        }
    }

    private fun stringToArray(period: String): ArrayList<String> {
        val strs = period.split(",")
        val tmp = arrayListOf<String>()
        for (i in strs.indices) {
            tmp.add(strs[i])
        }
        return tmp
    }

    //新增TAG
    fun addNewTag(tag: String, userid: Int) {
        viewModelScope.launch {
            okHttp.postNewTag(okHttp.addNewTag(tag, userid))
        }
        viewModelScope.launch { getUserTags(userid) }
    }


    // 移除tag
    fun deleteTag(userid: Int, tag: Tag) {
        viewModelScope.launch {
            val tmp = okHttp.putDeleteTag(okHttp.closeTag(userid, tag.tagId))
            result.value = tmp
            Log.i("OKHTTP", "結果回來", null)
        }
    }

    //新建任務
    fun addnewTask(task: Task) {
        viewModelScope.launch {
            val tmp = okHttp.postTask(okHttp.taskPostRequest(task))
            taskResult.value = tmp
        }
    }

    //刪除習慣執行日
    fun deleteDoneDate(habitId: Int, date: String) {
        viewModelScope.launch {
            val tmp = okHttp.deleteHabitDoneDate(okHttp.deleteHabitDoneDate(habitId, date))
            if (tmp == 201) {
                Log.i("okhttp", "post done date ok", null)
            } else {

                Log.i("okhttp", "post done date bad $tmp", null)
            }
        }
    }

    //新增習慣執行日
    fun postDoneDate(habitId: Int, date: String) {
        viewModelScope.launch {
            val tmp = okHttp.postHabitDoneDate(okHttp.postHabitDoneDate(habitId, date))
            taskResult.value = tmp
            if (tmp == 200) {
                Log.i("okhttp", "post done date ok", null)
            } else {
                Log.i("okhttp", "post done date bad $tmp", null)
            }
        }
    }

    //拿取使用者資料
    fun getUserInfo(userid: Int, email: String) {
        viewModelScope.launch {
            val tmp = okHttp.getUserInfoApi(okHttp.postUserInfo(userid, email))
            Log.i("OKHTTP", "重發", null)
            loginUser.value = tmp
        }
    }


    //拿使用者道具
    fun getItem(userid: String) {
        viewModelScope.launch {
            val tmp = okHttp.getItems(okHttp.getUserItem(userid.toInt()))
            userItem.value = tmp
        }
    }


    //拿取使用者全標籤
    fun getUserTags(userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getUserTags(okHttp.getTags(userid.toString()))
            tags.value = tmp
        }
    }

    //拿取使用者全任務總攬
    fun getTask(userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getHabitOverView(okHttp.getHabitOverView(userid))
            val tmp2: MutableList<TaskOverView> = mutableListOf()
            for (i in 0 until tmp.size) {
                if (!tmp[i].isClose) {
                    tmp2.add(tmp[i])
                }
            }
            allTasks.value = tmp2
            selectedTask.value = allTasks.value
        }
    }


    fun getAllTask(userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getHabitOverView(okHttp.getHabitOverView(userid))
            allTaskWisClose.value = tmp

        }
    }


    fun getTaskDetail(habitId: Int, userid: Int) {
        viewModelScope.launch {
            val tmp = okHttp.getTaskDetail(okHttp.getHabitDetail(habitId, userid))
            taskDetail.value = tmp
        }

    }

}