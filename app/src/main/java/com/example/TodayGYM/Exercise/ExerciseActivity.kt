package com.example.TodayGYM.Exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseBinding
    var routineList=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)

        val fragment=Exercise_IngFragment()
        supportFragmentManager.beginTransaction().replace(R.id.exercise_fragmentview,fragment).commit()
        setContentView(binding.root)


    }
    fun init(){
        val type=intent.getStringExtra("type")
        val place=intent.getStringExtra("place")
        val List=intent.getSerializableExtra("routinelist")
        val index=intent.getIntExtra("index",0)
        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").split(",")
            for (i in parse) {
                routineList.add(i)
            }
        }
        Log.d("test",routineList.toString())
    }

}