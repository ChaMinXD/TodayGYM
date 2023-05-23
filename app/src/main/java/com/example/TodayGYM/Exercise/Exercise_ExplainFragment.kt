package com.example.TodayGYM.Exercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.FragmentExerciseExplainBinding


class Exercise_ExplainFragment : Fragment() {
    lateinit var binding: FragmentExerciseExplainBinding
    lateinit var type:String
    lateinit var place:String
    lateinit var exName:String
    lateinit var exExplain:String
    var exSrc:Int=0
    var routineList=ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseExplainBinding.inflate(inflater,container,false)
        //Bundle 데이터 가져오기
        type=arguments?.getString("type").toString()
        place=arguments?.getString("place").toString()
        exName=arguments?.getString("exName").toString()
        exExplain= arguments?.getString("exExplain")!!
        exSrc= arguments?.getInt("exSrc")!!
        //List Data 형태에 맞게 변환 Serializable > ArrayLIst
        var List=arguments?.getSerializable("routineList")
        var parse=List.toString().replace("[","").replace("]","").split(",")
        for(i in parse){
            routineList.add(i)
        }

        init()
        return binding.root
    }
    fun init(){
        val activity=activity as MainActivity
        binding.exerciseExplainTextview.text = exExplain.replace("\"","").replace("+","").replace("\\uF06C","*").replace("\\n", "\n").replace("\\t", "\t")
        binding.typePlaceTextview.text=type+" > "+place
        binding.exNameTextview.text=exName
        binding.exerciseImageview.setImageResource(exSrc)
        binding.backBtn.setOnClickListener {
            val exerciseFragment = ExerciseFragment()
            val bundle=Bundle()
            bundle.putString("type",type)
            bundle.putString("place",place)
            bundle.putBoolean("check",false)
            bundle.putSerializable("routineList",routineList)
            exerciseFragment.arguments=bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fl_container,exerciseFragment).commit()
        }
        binding.plusBtn.setOnClickListener {
            val exerciseFragment = ExerciseFragment()
            val bundle=Bundle()
            routineList.add(exName)
            bundle.putString("type",type)
            bundle.putString("place",place)
            bundle.putBoolean("check",true)
            bundle.putString("exName",exName)
            bundle.putSerializable("routineList",routineList)
            exerciseFragment.arguments=bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.fl_container,exerciseFragment).commit()

        }
    }

}