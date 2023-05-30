package com.example.TodayGYM.Exercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.migration_1_2
import com.example.TodayGYM.DB.migration_2_3
import com.example.TodayGYM.Dialog.SetDialog
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.FragmentExerciseIngBinding
import java.util.*
import kotlin.concurrent.timer

class Exercise_IngFragment : Fragment() {
    lateinit var binding:FragmentExerciseIngBinding
    lateinit var db: ExerciseDatabase
    lateinit var routinename:String
    var isRoutine:Boolean = false
    private var time=0;
    private var timerTask: Timer?=null
    var index = 0
    var routineList=ArrayList<String>()
    var laptime=ArrayList<Int>()
    var set=4
    var now_set=1
    var send_time=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseIngBinding.inflate(inflater,container,false)
        db= Room.databaseBuilder(requireContext(), ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().addMigrations(
            migration_1_2
        ).addMigrations(migration_2_3).build()
        isRoutine= arguments?.getBoolean("isRoutine")!!
        val List=arguments?.getSerializable("routineList")
        routinename= arguments?.getString("routinename").toString()

        Log.d("IngName",routinename)

        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").trim().split(",")
            for (i in parse) {
                routineList.add(i.trim())
            }
        }
        showSetDialog()
        init()

        return binding.root
    }
    fun init(){
        var src=db.exerciseDao().getExercise(routineList[index]).ExSrc
        binding.exerciseImageview.setImageResource(src)
        binding.playBtn.setOnClickListener {
            startTimer()
        }
        binding.pauseBtn.setOnClickListener {
            pauseTimer()
        }
        binding.restPlayBtn.setOnClickListener {
            endTimer()
            binding.ingGroup1.visibility=View.VISIBLE
            binding.ingGroup2.visibility=View.INVISIBLE
        }
        binding.stopBtn.setOnClickListener {
            resetTimer()
            now_set++
            if(now_set>set){
                var routine_num=routineList.size
                Log.d("num",routine_num.toString())
                index++
                Log.d("index",index.toString())
                now_set=1
                if(index==routine_num){
                    //마지막 운동끝난경우
                    for ( i in laptime){
                        send_time+=i
                    }
                    val activity=activity as ExerciseActivity
                    activity.setInvisible()
                    var endFragment=Exercise_EndFragment()
                    val bundle=Bundle()
                    bundle.putString("routineName",routinename)
                    bundle.putSerializable("routinelist",routineList)
                    bundle.putInt("time",send_time)
                    bundle.putBoolean("isRoutine",isRoutine)
                    endFragment.arguments=bundle
                    activity.supportFragmentManager.beginTransaction().replace(R.id.exercise_fragmentview,endFragment).commit()

                }
                else{
                    //세트수 끝난경우
                    (activity as ExerciseActivity).setExname(routineList[index])
                    showSetDialog()
                    Log.d("index",routineList[index])
                    var src=db.exerciseDao().getExercise(routineList[index]).ExSrc
                    binding.exerciseImageview.setImageResource(src)

                }
            }
            else{
                // 세트수 남은경우
                binding.ingGroup1.visibility=View.INVISIBLE
                binding.ingGroup2.visibility=View.VISIBLE
                startTimer()
                binding.listTextview.text=now_set.toString()+" / "+set.toString()+ " 세트"
            }
        }
    }
    fun startTimer(){

        timerTask=timer(period=1000){
            time++
            val min=time/60
            val sec=time%60
            activity?.runOnUiThread {
                binding.timerMin.text=min.toString()
                binding.timerSec.text=sec.toString()
            }
        }

    }
    fun pauseTimer(){
        timerTask?.cancel()
    }

    fun resetTimer(){
        laptime.add(time)
        time=0
        binding.timerMin.text="00"
        binding.timerSec.text="00"
        Log.d("laptime",laptime.toString())
        timerTask?.cancel()
    }
    fun endTimer(){
        time=0
        binding.timerMin.text="00"
        binding.timerSec.text="00"
        timerTask?.cancel()
    }
    fun showSetDialog(){
        val setDialog= SetDialog()
        setDialog.setListener=object : SetDialog.OnSetListener{
            override fun setBtnClicked() {
                set=setDialog.set
                Log.d("diaset",set.toString())
                binding.listTextview.text=now_set.toString()+" / "+set.toString()+ " 세트"
                setDialog.dismiss()
            }
        }
        setDialog.show(requireFragmentManager(),"SetDialog")
    }


}