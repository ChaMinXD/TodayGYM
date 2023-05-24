package com.example.TodayGYM.Exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.FragmentExerciseIngBinding
import java.util.*
import kotlin.concurrent.timer

class Exercise_IngFragment : Fragment() {
    lateinit var binding:FragmentExerciseIngBinding
    private var time=0;
    private var timerTask: Timer?=null
    var laptime=ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseIngBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }
    fun init(){
        binding.playBtn.setOnClickListener {
            startTimer()
        }
        binding.pauseBtn.setOnClickListener {
            pauseTimer()
        }
        binding.stopBtn.setOnClickListener {
            resetTimer()
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
        timerTask?.cancel()
    }


}