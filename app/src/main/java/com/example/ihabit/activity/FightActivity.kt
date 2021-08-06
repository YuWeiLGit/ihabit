package com.example.ihabit.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihabit.R
import com.example.ihabit.adapter.SkillAdapter
import com.example.ihabit.data.Monster
import com.example.ihabit.data.UserInfo
import com.example.ihabit.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_fight.*
import kotlinx.android.synthetic.main.task_overview.*


class FightActivity : AppCompatActivity() {

    val viewModel = MainViewModel()
    lateinit var monster: Monster
    lateinit var userInfo: UserInfo
    lateinit var fightContent: TextView
    lateinit var monsterPic: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var userMpNum:TextView
    var lightAttack = 0
    var normalAttack = 0
    var criticalStrike = 0
    var potionCount = 3
    var bossAtk: String = ""
    var bossAtkNum = 0
    var bossName = ""
    var career = ""
    var def = 0
    var currentMana = 0
    var fullMana = 0
    var skillIsOpen = false
    var playerTurn = true
    var warriorBuff = 0
    var monsterDeBuff = 0
    var mageEnhance = false
    var bossFullHp=0
    var userFullHp=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)

        //intent拿值
        val monsterNum = intent.getStringExtra("monsterNum")
        val id = intent.getStringExtra("ID")
        val email = intent.getStringExtra("mail")
        fightContent = findViewById(R.id.battleContent)

        var fighting = true


        monsterPic = findViewById<ImageView>(R.id.fightBoss)
        val bossHp = findViewById<ProgressBar>(R.id.bossHealth)
        val userHp = findViewById<ProgressBar>(R.id.userHp)
        val userMp = findViewById<ProgressBar>(R.id.userMp)
        val hit = findViewById<ImageView>(R.id.hit)
        val potionleft = findViewById<TextView>(R.id.potionLeft)
        val bossHpNum=findViewById<TextView>(R.id.bossHpNumber)
        val userHpNum=findViewById<TextView>(R.id.userHpNumber)
         userMpNum=findViewById<TextView>(R.id.userMpNumber)
        potionleft.text = potionCount.toString()
         recyclerView = findViewById<RecyclerView>(R.id.abilityRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val potion = findViewById<ImageView>(R.id.potion)
        potion.setImageResource(R.drawable.medicine)
        val ability = findViewById<ImageView>(R.id.ability)
        ability.setImageResource(R.drawable.skillicon)
        val damage = findViewById<ImageView>(R.id.screach)
        damage.visibility = View.INVISIBLE
        rightIn("")


        //拿使用者資訊
        viewModel.getUserInfo(id!!.toInt(), email!!)
        viewModel.loginUser.observe(this) {
            userInfo = it
            userHp.max = it.hp
            userHp.progress = it.hp
            userMp.max = it.magic
            userFullHp=it.hp
            userHpNum.text="$userFullHp / $userFullHp"
            userMp.progress = it.magic
            currentMana = it.magic
            fullMana = it.magic
            userMpNum.text="$currentMana / $fullMana"
            def = it.def
            viewModel.setUserHP(it.hp)
            career = it.career!!
            if (career == "戰士") {
                hit.setImageResource(R.drawable.warriorattack)
            } else if (career == "弓箭手") {
                hit.setImageResource(R.drawable.archerattack)
            } else {
                hit.setImageResource(R.drawable.magicattack)
            }


        }
        //進到act取得當前monster
        viewModel.getMonster(monsterNum!!.toInt())
        viewModel.monster.observe(this) { it ->
            this.monster = it
            monsterPic.setImageResource(imgConvert(it.icon))
            lightAttack = it.lightAttack
            normalAttack = it.normalAttack
            criticalStrike = it.criticalStrike
            bossHp.max = it.hp
            bossHp.progress = it.hp
            bossFullHp=it.hp
            bossHpNum.text="$it.hp / $bossFullHp"
            viewModel.setMonsterHp(it.hp)
            bossName = it.monsterName

        }


        //普通攻擊
        hit.setOnClickListener {
            if (playerTurn) {
                val monsterName = monster.monsterName
                val atk = userInfo.atk
                playerTurn = false
                var userAtk = userInfo.atk
                if (mageEnhance) {
                    userAtk *= 2
                    mageEnhance = false
                }
                monsterPic.alpha = 0.1f
                monsterPic.animate().translationX(0f).alpha(1f).setDuration(800).start()
                rightIn("你對$monsterName 造成了 $atk 點傷害")
                viewModel.damageToBoss(userAtk, monsterAtk())
            } else {
                Toast.makeText(this, "還不是你的回合 !", Toast.LENGTH_LONG).show()
            }
        }
        //使用藥水
        potion.setOnClickListener {
            if (playerTurn) {
                if (potionCount > 0) {
                    playerTurn = false
                    potionCount--
                    potionleft.text = potionCount.toString()
                    if (potionCount == 0) {
                        potion.alpha = 0.1f
                    }
                    playerTurn = false
                    val tmp = userInfo.hp
                    val tmp2 = tmp * 25 / 100
                    rightIn("你對自己 回復了 $tmp2 點生命")
                    rightIn("藥水數量剩餘$potionCount")
                    viewModel.healToUser(tmp2, monsterAtk())
                } else {
                    Toast.makeText(this, "藥水用完了 !", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "還不是你的回合 !", Toast.LENGTH_LONG).show()
            }

        }
        //使用技能
        ability.setOnClickListener {
            if (playerTurn) {
                val adapter = SkillAdapter(career, this)
                if (skillIsOpen) {
                    recyclerView.adapter = null
                    skillIsOpen = false
                } else {
                    recyclerView.adapter = adapter
                    skillIsOpen = true
                }
            } else {
                Toast.makeText(this, "還不是你的回合 !", Toast.LENGTH_LONG).show()
            }
        }

        //動畫是否出現
        viewModel.aniDamage.observe(this) {
            if (it) {
                damage.visibility = View.VISIBLE
                rightInForBoss()
            } else {
                damage.visibility = View.INVISIBLE
            }
        }

        monsterPic.setOnClickListener {
            if(!fighting){
                finish()
            }
        }



        //觀察勝負
        viewModel.userHp.observe(this) { it ->

            if (it <= 0) {
                if(fighting){
                    fighting = false
                    userHp.progress = 0
                    userHpNum.text="0 / $userFullHp"
                    rightIn("挑戰失敗")
                    if(career=="戰士"){
                        monsterPic.setImageResource(R.drawable.warrior_dead)
                    }
                    else if(career=="弓箭手"){
                        monsterPic.setImageResource(R.drawable.archer_dead)
                    }else{
                        monsterPic.setImageResource(R.drawable.mage_dead)
                    }
                }

                //玩家失敗
            }
            if (fighting) {
                var tmp = it
                if (tmp < 0) {
                    tmp = 0
                }
                userHp.progress = tmp
                userHpNum.text="$tmp / $userFullHp"
                playerTurn = true
            }


        }
        viewModel.monsterHp.observe(this) { it ->
            if (it <= 0) {
                if(fighting){
                    fighting = false
                    bossHp.progress = 0
                    bossHpNum.text="0/$bossFullHp"
                    rightIn("獲勝")

                    monsterPic.setImageResource(R.drawable.shop_img_coin_04)
                    viewModel.postWin(id.toInt(),monsterNum.toInt())
                    //玩家勝利
                }
            }
            if (fighting) {
                var tmp = it
                if (tmp < 0) {
                    tmp = 0
                }
                bossHp.progress = tmp
                bossHpNum.text="$tmp/$bossFullHp"
            }
        }
        viewModel.winBattle.observe(this){it->
            val wintext=findViewById<TextView>(R.id.winText)
            val tmp=it.data.toString()
            wintext.text=("贏得獎勵"
                    + System.getProperty("line.separator")
                    + " $ "+ tmp)

        }

    }


    //隨機魔王攻擊數值
    private fun monsterAtk(): Int {
        val list = listOf(lightAttack, criticalStrike, normalAttack)
        var tmp = list.random()
        bossAtk = if (tmp == lightAttack) {
            "輕攻擊 傷害"
        } else if (tmp == 0) {
            "沒有效果的攻擊"
        } else if (tmp == normalAttack) {
            "普通攻擊 傷害"
        } else {
            "爆擊 傷害"
        }
        tmp -= def

        if (monsterDeBuff > 0) {
            tmp = lightAttack - def
            bossAtk="輕攻擊 傷害"
            monsterDeBuff--
        }
        if (warriorBuff > 0) {
            val buff = def * 3 / 10
            tmp -= buff
            warriorBuff--
        }
        if (tmp < 0) {
            tmp = 0
        }

        bossAtkNum = tmp
        return tmp
    }

    //寫入text content
    private fun rightInForBoss() {
        val tmp = fightContent.text.toString()
        val tmp2 = ("$bossName 對你造成了 $bossAtkNum 點的 $bossAtk "
                + System.getProperty("line.separator")
                + tmp)
        fightContent.text = tmp2

    }

    //寫入text content
    fun rightIn(content: String) {
        if (fightContent.text.toString() == "test") {
            fightContent.text = "戰鬥開始"
        } else {
            val tmp = fightContent.text.toString()
            val tmp2 = (content
                    + System.getProperty("line.separator")
                    + tmp)
            fightContent.text = tmp2
        }
    }

    fun useSkill(num: Int) {

        if (career == "戰士") {
            when (num) {
                1 -> {
                    val costMana = fullMana * 30 / 100
                    if (currentMana >= costMana) {
                        val tmp = userInfo.atk * 15 / 10
                        skillAtk(tmp, "乞丐大劍")
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                2 -> {
                    val costMana = fullMana * 40 / 100
                    if (currentMana >= costMana) {
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                        rightIn("啟用 石化皮膚 提升防禦力")
                        warriorBuff = 2
                        viewModel.healToUser(0, monsterAtk())
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                3 -> {
                    val costMana = fullMana * 50 / 100
                    if (currentMana >= costMana) {
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                        val uHp = userInfo.hp
                        val tmp2 = uHp * 20 / 100
                        rightIn("你對自己 使用振奮之吼 回復了 $tmp2 點生命")
                        viewModel.healToUser(tmp2, monsterAtk())
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else if (career == "弓箭手") {
            when (num) {
                1 -> {
                    val costMana = fullMana * 25 / 100
                    if (currentMana >= costMana) {
                        val tmp = userInfo.atk * 15 / 10
                        skillAtk(tmp, "百步穿楊")
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                2 -> {
                    val costMana = fullMana * 30 / 100
                    if (currentMana >= costMana) {
                        val tmp = userInfo.atk
                        specialAtK(3, tmp)
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                        rightIn("你用箭雨對$bossName 造成了 3 次 $tmp 點傷害")
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                3 -> {
                    val costMana = fullMana * 40 / 100
                    if (currentMana >= costMana) {
                        monsterDeBuff=3
                        viewModel.healToUser(0, monsterAtk())
                        rightIn("你對$bossName 使用了 閃電箭 弱化他的攻擊三回合")
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            when (num) {
                1 -> {
                    val costMana = fullMana * 20 / 100
                    if (currentMana >= costMana) {
                        var tmp = userInfo.atk
                        if (mageEnhance) {
                            tmp *= 2
                            mageEnhance = false
                        }
                        specialAtK(2, tmp)
                        rightIn("你用雷擊術對$bossName 造成了 2 次 $tmp 點傷害")
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                2 -> {
                    val costMana = fullMana * 35 / 100
                    if (currentMana >= costMana) {
                        mageEnhance = true
                        rightIn("你使用了祕法超載 下次傷害必為兩倍傷害")
                        viewModel.healToUser(0, monsterAtk())
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }
                3 -> {
                    val costMana = fullMana * 50 / 100
                    if (currentMana >= costMana) {
                        var tmp = userInfo.atk * 3
                        if (mageEnhance) {
                            tmp *= 2
                        }
                        skillAtk(tmp, "隕石術")
                        currentMana -= costMana
                        userMp.progress = currentMana
                        userMpNum.text="$currentMana / $fullMana"
                    } else {
                        Toast.makeText(this, "魔力不足 !", Toast.LENGTH_LONG).show()
                    }
                }

            }


        }
        recyclerView.adapter=null

    }

    fun specialAtK(time: Int, atk: Int) {
        viewModel.specialMove(time, atk, monsterAtk())
    }


    fun skillAtk(atkPoint: Int, SkillName: String) {
        val monsterName = monster.monsterName
        playerTurn = false
        monsterPic.alpha = 0.1f
        monsterPic.animate().translationX(0f).alpha(1f).setDuration(800).start()
        rightIn("你對$$monsterName  使用了$SkillName 造成了 $atkPoint 點傷害")
        viewModel.damageToBoss(atkPoint, monsterAtk())
    }


    fun imgConvert(name: String): Int {
        return when (name) {
            "monster1" -> R.drawable.monster1
            "monster2" -> R.drawable.monster2
            "monster3" -> R.drawable.monster3
            "monster4" -> R.drawable.monster4
            "monster5" -> R.drawable.monster5
            "monster6" -> R.drawable.monster6
            "monster7" -> R.drawable.monster7
            "monster8" -> R.drawable.monster8
            "monster9" -> R.drawable.monster9
            else -> R.drawable.monster10
        }


    }


}