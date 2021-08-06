package com.example.ihabit

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.activity.*
import com.example.ihabit.adapter.TaskAdapter
import com.example.ihabit.data.*
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_frame.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFrameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFrameFragment(activity: MainActivity, userInfo: UserInfo,userId:Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val activity = activity
    var tasks: MutableList<TaskOverView> = mutableListOf()
    var tags: MutableList<Tag> = mutableListOf()
    val userInfo=userInfo
    val userId=userId
    var lastLevel=-1
    var isOPen=false
    private val taskAdapter = TaskAdapter(activity,userId,userInfo.email ,this)
    private val viewModel = MainViewModel()
    val  alarmService = AlarmService(activity)
    val sdf = SimpleDateFormat("yyyy/MM/dd")
    var today = startDateConvert(sdf.format(Date()))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //鬧鐘相關
        alarmService.cancelAll()

        viewModel.getUserTags(userId)
        val lvNum=view.findViewById<TextView>(R.id.LvNum)
        val level=userInfo.level.toString()
        lvNum.text="LV: $level"
        //進來就拿到userId
        viewModel.getTask(userId)



        //火爐動畫
        val fire = view.findViewById<ImageView>(R.id.fire)
        var fireplace= resources.getDrawable(R.drawable.fireplace) as AnimationDrawable
        fire.setImageDrawable(fireplace)
        fireplace.start()


        //經驗值進度條
        val progressBar=view.findViewById<ProgressBar>(R.id.progressBar)
        val progressNum=view.findViewById<TextView>(R.id.expNum)
        progressBar.max=100
        progressBar.progress=userInfo.exp
        val percent:Int=userInfo.exp
        progressNum.text="經驗值: $percent"
        viewModel.getUserInfo(userId,userInfo.email)
        viewModel.loginUser.observe(owner=viewLifecycleOwner){it->
            val tmp=it.level
            progressBar.progress=it.exp
            lvNum.text="LV: $tmp"
            val percentInside:Int=it.exp
            progressNum.text="經驗值: $percentInside %"

            if(it.level==lastLevel+1){
                if(!isOPen){
                    isOPen=true
                    val tmp=AchievementFragment(userId, userInfo.email,true)
                    val builder=
                        AlertDialog.Builder(activity)
                            .setMessage("獲得一點天賦，是否立即使用")
                            .setTitle("恭喜升級 !!")
                            .setPositiveButton("確認") { dialog, _ ->
                                dialog.cancel()
                                activity.startAcheFragment(tmp)
                                isOPen=false
                            }
                            .setNegativeButton("取消"){dialog,_->
                                dialog.cancel()
                            }
                    val alter=builder.create()
                    alter.show()
                }

            }else{
                lastLevel=it.level
            }


        }

        //走路動畫
        val charaterAni=view.findViewById<ImageView>(R.id.charaterAni)

        var walkAni= resources.getDrawable(R.drawable.warrior_walk) as AnimationDrawable

        if(userInfo.career=="法師"){
            walkAni= resources.getDrawable(R.drawable.healer_walk) as AnimationDrawable
        }else if(userInfo.career=="弓箭手"){
            walkAni= resources.getDrawable(R.drawable.archer_walk) as AnimationDrawable
        }

        charaterAni.setImageDrawable(walkAni)
        walkAni.start()


        //戰鬥場景
        val battleScene=view.findViewById<ImageView>(R.id.toBattle)
        battleScene.setImageResource(R.drawable.fighticon)
        battleScene.setOnClickListener {
            val intent = Intent(activity, BossActivity::class.java)
            intent.putExtra("id",userId.toString())
            intent.putExtra("mail",userInfo.email)

            activity.startActivity(intent)
        }


        //到打鐵匠
        val toBlackSmith=view.findViewById<ImageView>(R.id.toBlackSmith)
        toBlackSmith.setImageResource(R.drawable.storeicon)
        toBlackSmith.setOnClickListener {
            val intent = Intent(activity, BlackSmithActivity::class.java)
            intent.putExtra("uId",userId.toString())
            val tmp=userInfo.email
            intent.putExtra("eMail",tmp)
            activity.startActivity(intent)
        }

        //裝備act
        val headIcon = view.findViewById<ImageView>(R.id.headicon)
        if(userInfo.career=="弓箭手"){
            headIcon.setImageResource(R.drawable.ranger_head)
        }else if(userInfo.career=="法師"){
            headIcon.setImageResource(R.drawable.healer_head)
        }

        headIcon.setOnClickListener {
            val intent = Intent(activity, CharacterActivity::class.java)
            intent.putExtra("UID",userId.toString())
            val tmp=userInfo.email
            intent.putExtra("EMAIL",tmp)
            activity.startActivity(intent)
        }

        //任務觀察者全資料
        viewModel.selectedTask.observe(owner = viewLifecycleOwner){it->

            for(i in 0 until it.size){
                if(it[i].isInform){
                    var notionTime="00:00"
                    if(it[i].infromTime!=null){
                        notionTime=it[i].infromTime
                    }
                    val word=it[i].habitName
                    val time = convertOfTime(today, notionTime) - 28800000
                    val repeatTime=time + TimeUnit.DAYS.toMillis(1)
                    val hour=LocalDateTime.now()


                    val currentTime =hour.format(DateTimeFormatter.ofPattern("HH:mm"))
                    if(convertOfTime(today, notionTime) >convertOfTime(today, currentTime))
                    {
                        val tmp=convertOfTime(today, currentTime)
                        alarmService.setExactAlarm(time,word)
                    }

                    alarmService.setRepetitiveAlarm(repeatTime,word)
                }
            }


            taskAdapter.refreshItems(it)
        }
        //tags觀察者
        viewModel.tags.observe(owner = viewLifecycleOwner) {it->
            Log.i("okhttp" ,"刷新",null)
         tags=it
        }

        /// 按鍵切換到新增任務
        val recyclerView = view.findViewById<RecyclerView>(R.id.tasksRecycleer)
        val tmpLayOutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager =tmpLayOutManager
        recyclerView.adapter = taskAdapter
        val add = view.findViewById<TextView>(R.id.addHabit)

        //開啟filter 未完成回傳給全任務的adapter全任務的adapter
         val filter = view.findViewById<ImageView>(R.id.filter)
        filter.setOnClickListener {
            val alert = AlertDialog.Builder(activity)
            val mView: View = activity.layoutInflater.inflate(R.layout.filter, null)
            alert.setTitle("選擇篩選條件")
            val spinner = mView.findViewById<Spinner>(R.id.spinner)
            val tmpTag: MutableList<String> = mutableListOf("全部")
            for(i in 0 until tags.size){
                tmpTag.add(tags[i].tagName)
            }
            val adapter =
                ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, tmpTag)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            val dialog = alert.create()
            val dialogClickListener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.selectByTag(spinner.selectedItem.toString())
                    }
                    DialogInterface.BUTTON_NEGATIVE
                    -> {
                        dialog.cancel()
                    }
                }
            }
            alert.setPositiveButton("確定", dialogClickListener)
            alert.setNegativeButton("取消", dialogClickListener)
            alert.setView(mView)
            alert.show()

        }


        //新增任務
        add.setOnClickListener {
            val intent = Intent(activity.baseContext, AddHabitActivity::class.java)
            intent.putExtra("userid",userId.toString())
            activity.startActivity(intent)
        }
        //檢視全部任務
        val toAllTask = view.findViewById<ImageView>(R.id.allTask)
        toAllTask.setImageResource(R.drawable.bookicon)
        toAllTask.setOnClickListener {
            val intent = Intent(activity, AllTasksActivity::class.java)
            intent.putExtra("id",userId.toString())
            activity.startActivity(intent)
        }






    }
    fun update(){
        viewModel.getUserInfo(userId,userInfo.email)
    }

    //日期轉換
    fun startDateConvert(date: String): String {
        val str = date
        val split = str.split('/') //用空格拆
        val year = split[0]
        var month = split[1]
        var date = split[2]
        return "$year-$month-$date"

    }
    //日期比較與轉換long
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertOfTime(date: String, hour: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        if(sdf.parse(date).after(sdf.parse(today))){
            val tmp = "$date/$hour"
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm", Locale.TAIWAN)
            val localDate = LocalDateTime.parse(tmp, formatter)
            return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        }else{
            val tmp = "$today/$hour"
            Log.i("CCC",tmp,null)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm", Locale.TAIWAN)
            val localDate = LocalDateTime.parse(tmp, formatter)
            return localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_frame, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFrameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance( mainActivity: MainActivity, userInfo: UserInfo,userId: Int) =
            MainFrameFragment(mainActivity, userInfo,userId  ).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}