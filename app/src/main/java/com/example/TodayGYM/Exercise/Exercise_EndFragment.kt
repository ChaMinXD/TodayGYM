package com.example.TodayGYM.Exercise

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.RoutineEntity
import com.example.TodayGYM.DB.migration_1_2
import com.example.TodayGYM.DB.migration_2_3
import com.example.TodayGYM.Dialog.RoutineDialog
import com.example.TodayGYM.Dialog.SetDialog
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.FragmentExerciseEndBinding
import java.util.ArrayList


class Exercise_EndFragment : Fragment() {
    lateinit var routineName:String
    lateinit var binding:FragmentExerciseEndBinding
    lateinit var routine_db:ExerciseDatabase
    var routineList= ArrayList<String>()
    lateinit var routinename:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseEndBinding.inflate(inflater,container,false)
        routine_db= Room.databaseBuilder(requireContext(), ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().addMigrations(
            migration_1_2
        ).addMigrations(migration_2_3).build()
        routinename= arguments?.getString("routineName").toString()
        Log.d("EndName",routinename)
        val time=arguments?.getInt("time")
        var min= time?.div(60)
        var sec= time?.rem(60)
        binding.timeTextview.text=min.toString()+"분 "+sec.toString()+"초 동안 \n 운동했어요 !"
        var List=arguments?.getSerializable("routinelist")
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
        binding.routinenameTextview.text=routinename
        binding.goodBtn.setOnClickListener {
            showRoutineDialog()
            routine_db.routineDao().insertData(RoutineEntity(routineList,routinename))
            Log.d("routineDB",routine_db.routineDao().getAll().toString())

        }
        binding.badBtn.setOnClickListener {
            //visible
            val intent= Intent(context,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun showRoutineDialog(){
        val routineDialog= RoutineDialog()
        routineDialog.routineListener=object : RoutineDialog.OnRoutineListener{
            override fun setBtnClicked() {
                routineName=routineDialog.routineName
                routineDialog.dismiss()
                //
                val intent= Intent(context,MainActivity::class.java)
                intent.putExtra("type","유산소")
                intent.putExtra("place","유산소")
                startActivity(intent)

            }
        }
        routineDialog.show(requireFragmentManager(),"SetDialog")
    }

}