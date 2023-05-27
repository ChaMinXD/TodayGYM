package com.example.TodayGYM

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.DB.migration_1_2
import com.example.TodayGYM.DB.migration_2_3
import com.example.TodayGYM.Exercise.ExerciseFragment
import com.example.TodayGYM.Routine.RoutineFragment
import com.example.TodayGYM.Setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    lateinit var db:ExerciseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectIntent=intent
        val type=selectIntent.getStringExtra("type")
        val place=selectIntent.getStringExtra("place")
        db= Room.databaseBuilder(this, ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().addMigrations(
            migration_1_2).addMigrations(migration_2_3).build()
        loadTxt()  // SelectActivity 에서 할지 ?


        var bnv_main=findViewById(R.id.bnv_main) as BottomNavigationView
        bnv_main.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.first -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val exerciseFragment = ExerciseFragment()
                    val bundle=Bundle()
                    bundle.putString("type",type)
                    bundle.putString("place",place)
                    bundle.putBoolean("check",false)
                    exerciseFragment.arguments=bundle
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container,exerciseFragment).commit()
                }
                R.id.second -> {
                    val routineFragment = RoutineFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, routineFragment).commit()
                }
                R.id.third -> {
                    val settingFragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.first
        }

    }
    fun loadTxt() {
        val inputData: InputStream = resources.openRawResource(R.raw.exerciselist)
        var a :List<String>
        try {
            val bufferedReader = BufferedReader(InputStreamReader(inputData, "UTF-8"))
            while (true) {
                val string = bufferedReader.readLine()
                if (string != null) {
                    a= string.split("/")
                    val iResId = resources.getIdentifier("@drawable/"+a[3], "drawable", this.getPackageName())
                    val data=ExerciseEntity(a[0],a[1],a[2],iResId,a[4])
                    db.exerciseDao().insertData(data)
                } else {
                    break
                }
            }
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}