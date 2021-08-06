package com.example.ihabit.activity

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.*
import com.example.ihabit.adapter.IconAdapter
import com.example.ihabit.adapter.SelectedTagAdapter
import com.example.ihabit.adapter.WeeklyAdapter
import com.example.ihabit.data.Tag
import com.example.ihabit.data.Task
import com.example.ihabit.data.TaskForReturn
import com.example.ihabit.data.TaskForUpdate
import com.example.ihabit.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class AddHabitActivity() : AppCompatActivity() {

    val requestCodeForAddTag = 2
    lateinit var timer: TextView
    lateinit var alarmService: AlarmService


    val selectedTagAdapter = SelectedTagAdapter(this)
    lateinit var date: TextView
    val calendarFragment = CalendarFragment()
    val timePickerFragment = timePickerFragment()
    val weeklyAdapter = WeeklyAdapter(this)
    lateinit var currentIcon: ImageView
    var updateTask: TaskForReturn? = null


    //修改判斷
    val viewModel = MainViewModel()

    //任務相關參數
    var notionTime = "08:00"
    var positive = ""
    var iconNumber: Int = R.drawable.icon1
    val sdf = SimpleDateFormat("yyyy/MM/dd")
    var startDate = startDateConvert(sdf.format(Date()))
    val today = startDate
    var period = arrayListOf<String>("7")
    val isHide = false
    val isInform = true
    val isClose = false
    val isSocialized = true
    var selectTag: ArrayList<Tag> = arrayListOf()
    var id by Delegates.notNull<Int>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)
        timer = findViewById<TextView>(R.id.addtimer)
        val backToMain = findViewById<Button>(R.id.backToMain)
        val addText = findViewById<EditText>(R.id.addHabitName)
        val randomButton = findViewById<Button>(R.id.randomButton)
        val positiveText = findViewById<EditText>(R.id.addPositiveText)
        val frequency = findViewById<Switch>(R.id.frequency)
        currentIcon = findViewById(R.id.currentIcon)
        alarmService = AlarmService(this)
        if (!update()) {
            id = intent.getStringExtra("userid")!!.toInt()
        }
//        val uId = intent.getStringExtra("userid")!!.toInt()
        creativeNotificationChannel()
        //標籤recyclerView
        val tagRecyclerView = findViewById<RecyclerView>(R.id.tagRecycler)
        tagRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tagRecyclerView.adapter = selectedTagAdapter
        if (update()) {
            id = intent.getStringExtra("userId")!!.toInt()
            viewModel.getUserTags(id)
        }

        //設定通知
        val alarmManager =
            this.getSystemService(Context.ALARM_SERVICE) as? AlarmManager


        //tag觀察者
        viewModel.selectedTags.observe(this) { it ->
            selectedTagAdapter.refreshItems(it)
        }

        //頻率開啟week點選
        val weeklyRecyclerView = findViewById<RecyclerView>(R.id.weeklyRecycler)
        weeklyRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        //如果是更新的就打API拿資料
        if (update()) {
            val habitId = intent?.getStringExtra("habitId")
            val userId = intent?.getStringExtra("userId")
            viewModel.getTaskDetail(habitId?.toInt()!!, userId?.toInt()!!)
        }
        viewModel.taskDetail.observe(this) { it ->
            updateTask = it

            //任務名字
            addText.setText(it.habitName)
            //起始時間
            startDate = it.startDate
            //重複時間
            if (periodSize(it.period)) {
                frequency.isChecked = true
                weeklyRecyclerView.adapter = weeklyAdapter
                period.remove("7")
                period = stringToArray(it.period)
                weeklyAdapter.choice(period)
            }
            //鼓勵的話
            positiveText.setText(it.message)

            //預設時間
            timer.text = it.InformTime
            notionTime = it.InformTime
            //任務ICON
            val iconNum = it.icon.toInt()
            val tmp = idConverter(iconNum)
            currentIcon.setImageResource(tmp)
            //tags
            selectTag = it.tags
            selectedTagAdapter.refreshItems(selectTag)

        }


        //icon 相關
        val iconRecyclerView = findViewById<RecyclerView>(R.id.iconRecyclerView)
        val iconAdapter = IconAdapter(this)
        iconRecyclerView.adapter = iconAdapter
        //switch 開啟一到日
        frequency.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                weeklyRecyclerView.adapter = weeklyAdapter
                period.remove("7")
            } else {
                val tmp = arrayListOf<String>("7")
                period = tmp
                weeklyRecyclerView.adapter = null
            }
        }

        //日期相關當天時間與選取時間
        date = findViewById(R.id.currentData)
        date.text = startDate
        date.setOnClickListener {
            val tmp: FragmentManager = supportFragmentManager
            val tmpFt = tmp.beginTransaction()
            tmpFt.replace(R.id.calendarFragement, calendarFragment, "mainFragment")
                .commit()
        }

        //新增提醒時間
        timer.setOnClickListener {
            val tmp: FragmentManager = supportFragmentManager
            val tmpFt = tmp.beginTransaction()
            tmpFt.replace(R.id.timerFragment, timePickerFragment, "timePickerFragment")
                .commit()
        }


        backToMain.setOnClickListener {
            val word = addText.text.toString()
            if (updateTask != null) {
                val habitId = updateTask!!.habitId
                val userId = id
                //偵測名字
                var habitName: String? = null
                if (updateTask!!.habitName != word
                ) {
                    habitName = word
                }
                //日期
                var newStartDate: String? = null
                if (updateTask!!.startDate != this.startDate
                ) {
                    newStartDate = this.startDate
                }
                //period
                var newPeriod: String? = null
                if (updateTask!!.period != arraylistToString(period)
                ) {
                    newPeriod = arraylistToString(period)
                }
                var newMessage: String? = null
                if (updateTask!!.message != this.positive
                ) {
                    newPeriod = this.positive
                }
                var newisHide: Boolean? = null
                var newisInform: Boolean? = updateTask!!.isInform
                var newInformtime: String? = null
                if (updateTask!!.InformTime != this.notionTime
                ) {
                    newInformtime = this.notionTime
                }
                var newIcon: String? = null
                if (updateTask!!.icon != idToTaskForm(this.iconNumber).toString()
                ) {
                    newIcon = idToTaskForm(this.iconNumber).toString()
                }
                var newisclose: Boolean? = null
                var newisSocailized: Boolean? = null
                var tagid: ArrayList<Int>? = null
                if (updateTask!!.tags != selectTag) {
                    val delete=tagConvertToTaskForm(updateTask!!.tags)
                    for(i in 0 until delete.size){
                        if(delete[i]!=-1){
                            viewModel.deleteTagForTask(habitId,delete[i])
                        }
                    }
                    tagid = tagConvertToTaskForm(selectTag)
                    for(i in 0 until tagid.size){
                        if(tagid[i]!=-1){
                            viewModel.postNewTagOftask(habitId,tagid[i] )
                        }
                    }
                }
                val update = TaskForUpdate(
                    habitId,
                    userId,
                    habitName,
                    newStartDate,
                    newPeriod,
                    newMessage,
                    newisHide,
                    newisInform,
                    newInformtime,
                    newIcon,
                    newisclose,
                    newisSocailized
                )
                Log.i("AAA","$update",null)
                viewModel.updateHabit(update)
                finish()
            } else {
                if (addText.text.isEmpty()) {
                    finish()
                } else {
                    if (period.size == 0) {
                        period.add("7")
                    }
                    val tmp = Task(
                        id,
                        word,
                        startDate,
                        arraylistToString(period),
                        positive,
                        isHide,
                        isInform,
                        notionTime,
                        idToTaskForm(iconNumber).toString(),
                        isClose,
                        isSocialized,
                        tagConvertToTaskForm(selectTag),
                        0,
                        0
                    )

                    val time = convertOfTime(startDate, notionTime) - 28800000
                    val repeatTime = time + TimeUnit.DAYS.toMillis(1)
                    if(notionTime!="08:00"){
                        alarmService.setExactAlarm(time, word)
                    }
                    alarmService.setRepetitiveAlarm(repeatTime, word)
                    viewModel.addnewTask(tmp)
                    finish()
                }
            }
        }


        //random鼓勵
        randomButton.setOnClickListener {
            positiveText.setText(allPositiveWordLists.random())
            positive = positiveText.text.toString()
        }
    }

    fun periodSize(str: String): Boolean {
        val chars: MutableList<Char> = ArrayList()
        for (ch in str.toCharArray()) {
            chars.add(ch)
        }
            if(chars.isEmpty()){
                return false
            }
        return chars[0] != '7'
    }

    fun sendDateString(date: String) {
        if (date != "") {
            this.date.text = date
            startDate = date
        }

    }



    fun tagCompare(tag: ArrayList<Tag>, id: ArrayList<Int>): Boolean {
        if (tag.size != id.size) {
            return false
        } else {
            val tmp = arrayListOf<Int>()
            for (i in 0 until tag.size) {
                tmp.add(tag[i].tagId)
            }
            return tmp == id
        }
    }


    fun idToTaskForm(id: Int): Int {
        return when (id) {
            R.drawable.icon1 -> 1
            R.drawable.icon2 -> 2
            R.drawable.icon3 -> 3
            R.drawable.icon4 -> 4
            R.drawable.icon5 -> 5
            R.drawable.icon7 -> 7
            R.drawable.icon8 -> 8
            R.drawable.icon9 -> 9
            R.drawable.icon10 -> 10
            R.drawable.icon11 -> 11
            R.drawable.icon12 -> 12
            R.drawable.icon13 -> 13
            R.drawable.icon14 -> 14
            R.drawable.icon15 -> 15
            R.drawable.icon16 -> 16
            R.drawable.icon17 -> 17
            R.drawable.icon18 -> 18
            R.drawable.icon19 -> 19
            R.drawable.icon20 -> 20
            R.drawable.icon21 -> 21
            R.drawable.icon22 -> 22
            R.drawable.icon23 -> 23
            R.drawable.icon24 -> 24
            R.drawable.icon25 -> 25
            R.drawable.icon26 -> 26
            R.drawable.icon27 -> 27
            R.drawable.icon28 -> 28


            else -> 6
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun creativeNotificationChannel() {
        val name = "iHabit"
        val descriptionText = "該執行習慣了!"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =
            NotificationChannel("iHabit", name, importance).apply { description = descriptionText }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    fun idConverter(id: Int): Int {
        return when (id) {
            1 -> R.drawable.icon1
            2 -> R.drawable.icon2
            3 -> R.drawable.icon3
            4 -> R.drawable.icon4
            5 -> R.drawable.icon5
            7 -> R.drawable.icon7
            8 -> R.drawable.icon8
            9 -> R.drawable.icon9
            10 -> R.drawable.icon10
            11 -> R.drawable.icon11
            12 -> R.drawable.icon12
            13 -> R.drawable.icon13
            14 -> R.drawable.icon14
            15 -> R.drawable.icon15
            16 -> R.drawable.icon16
            17 -> R.drawable.icon17
            18 -> R.drawable.icon18
            19 -> R.drawable.icon19
            20 -> R.drawable.icon20
            21 -> R.drawable.icon21
            22 -> R.drawable.icon22
            23 -> R.drawable.icon23
            24 -> R.drawable.icon24
            25 -> R.drawable.icon25
            26 -> R.drawable.icon26
            27 -> R.drawable.icon27
            28 -> R.drawable.icon28

            else -> R.drawable.icon6
        }
    }

    fun tagConvertToTaskForm(mutableList: MutableList<Tag>): ArrayList<Int> {
        val tmp: ArrayList<Int> = arrayListOf()
        for (i in 0 until mutableList.size) {
            tmp.add(mutableList[i].tagId)
        }
        return tmp
    }

    fun arraylistToString(period: ArrayList<String>): String {
        var tmp = period[0]
        for (i in 1 until period.size) {
            val tmp2 = period[i]
            tmp = "$tmp,$tmp2"
        }
        return tmp
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertOfTime(date: String, hour: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        if (sdf.parse(date).after(sdf.parse(today))) {
            val tmp = "$date/$hour"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm", Locale.TAIWAN)
            val localDate = LocalDateTime.parse(tmp, formatter)
            return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        } else {
            val tmp = "$today/$hour"
            Log.i("CCC", tmp, null)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm", Locale.TAIWAN)
            val localDate = LocalDateTime.parse(tmp, formatter)
            return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

        }
    }


    fun startDateConvert(date: String): String {
        val str = date
        val split = str.split('/') //用空格拆
        val year = split[0]
        var month = split[1]
        var date = split[2]
        return "$year-$month-$date"

    }

    fun stringToArray(period: String): ArrayList<String> {
        val strs = period.split(",")
        val tmp = arrayListOf<String>()
        for (i in strs.indices) {
            tmp.add(strs[i])
        }
        return tmp
    }

    fun update(): Boolean {
        val tmp = intent.getStringExtra("habitId")
        return tmp != null
    }


    fun sendHourString(time: String) {
        timer.text = time
        notionTime = time
    }

    fun sendIcon(data: Int) {
        currentIcon.setImageResource(data)
        iconNumber = data
    }

    fun addPeriod(addPeriod: String) {
        this.period.add(addPeriod)
    }

    fun removePeriod(removeDay: String) {
        this.period.remove(removeDay)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val backSelectTag: ArrayList<Tag> = arrayListOf()

            if (data?.getStringArrayListExtra("tagId") != null || data?.getStringArrayListExtra("tagName") != null) {
                val tmpId: ArrayList<String> = data?.getStringArrayListExtra("tagId")!!
                val tmpName: ArrayList<String> = data?.getStringArrayListExtra("tagName")!!
                if (tmpName != null) {
                    for (i in tmpName.indices) {
                        val tmp = Tag(tmpName[i], tmpId[i].toInt())
                        backSelectTag.add(tmp)
                    }
                    selectTag = backSelectTag
                }
                selectedTagAdapter.refreshItems(selectTag)
            }


        }

    }


}