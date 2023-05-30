package com.example.TodayGYM.Exercise

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    lateinit var binding: ActivityExerciseBinding
    var routineList=ArrayList<String>()
    lateinit var type:String
    lateinit var place:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        type= App.prefs.getString("type","")
        place= App.prefs.getString("place","")
        val List=intent.getSerializableExtra("routineList")
        val index=intent.getIntExtra("index",0)
        val routinename:String
        val isRoutine=intent.getBooleanExtra("isRoutine",false)
        if(!isEmpty(intent.getStringExtra("routinename"))){
            routinename= intent.getStringExtra("routinename").toString()
            }
        else{
            routinename= intent.getStringExtra("오늘의 루틴").toString()
        }

        Log.d("ActivityName",routinename)

        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").split(",")
            for (i in parse) {
                routineList.add(i.trim())
            }
        }
        Log.d("Acitivty",routineList.toString())
        binding.typePlaceTextview.text=type+" > "+place
        binding.exNameTextview.text=routineList[index]
        val fragment=Exercise_IngFragment()
        val bundle=Bundle()
        bundle.putSerializable("routineList",routineList)
        bundle.putString("routinename",routinename)
        bundle.putBoolean("isRoutine",isRoutine)
        fragment.arguments=bundle
        supportFragmentManager.beginTransaction().replace(R.id.exercise_fragmentview,fragment).commit()


        setContentView(binding.root)
    }
    fun setExname(a:String){
        binding.exNameTextview.text=a
    }
    fun setInvisible(){
        binding.exNameTextview.visibility= View.INVISIBLE
        binding.typePlaceTextview.visibility=View.INVISIBLE
    }
    fun setVisible(){
        binding.exNameTextview.visibility= View.VISIBLE
        binding.typePlaceTextview.visibility=View.VISIBLE

    }

}