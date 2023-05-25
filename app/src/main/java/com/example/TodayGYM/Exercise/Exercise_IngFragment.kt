package com.example.TodayGYM.Exercise

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.R
import com.example.TodayGYM.SetDialog
import com.example.TodayGYM.databinding.FragmentExerciseIngBinding
import java.util.*
import kotlin.concurrent.timer
import kotlin.properties.Delegates

class Exercise_IngFragment : Fragment() {
    lateinit var binding:FragmentExerciseIngBinding
    lateinit var db: ExerciseDatabase
    lateinit var routinename:String
    private var time=0;
    private var timerTask: Timer?=null
    var index = 0
    var routineList=ArrayList<String>()
    var laptime=ArrayList<Int>()
    var set=4
    var now_set=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseIngBinding.inflate(inflater,container,false)
        db= Room.databaseBuilder(requireContext(),ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().build()

        showSetDialog()
        routinename= arguments?.getString("routinename").toString()
        var List=arguments?.getSerializable("routineList")
        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").split(",")
            for (i in parse) {
                routineList.add(i)
            }
        }
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
            resetTimer()
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
                now_set=1
                if(index==routine_num){
                    //마지막 운동끝난경우

                }
                else{
                    //세트수 끝난경우
                    (activity as ExerciseActivity).setExname(routineList[index])
                    showSetDialog()

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
    fun showSetDialog(){
        val setDialog=SetDialog()
        setDialog.setListener=object :SetDialog.OnSetListener{
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