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
import com.example.TodayGYM.Dialog.RoutineDelDialog
import com.example.TodayGYM.Dialog.RoutineDialog
import com.example.TodayGYM.Dialog.SetDialog
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.FragmentExerciseEndBinding
import java.util.ArrayList


class Exercise_EndFragment : Fragment() {
    lateinit var binding:FragmentExerciseEndBinding
    lateinit var routine_db:ExerciseDatabase
    var routineList= ArrayList<String>()
    lateinit var routinename:String
    var isRoutine=false



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
        isRoutine= arguments?.getBoolean("isRoutine")!!
        val time=arguments?.getInt("time")
        var min= time?.div(60)
        var sec= time?.rem(60)
        binding.timeTextview.text=min.toString()+"분 "+sec.toString()+"초 동안 \n 운동했어요 !"
        var List=arguments?.getSerializable("routinelist")
        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").trim().split(",")
            for (i in parse) {
                routineList.add(i.trim())
            }
        }
        init()
        return binding.root
    }
    fun init(){
        val activity=activity as ExerciseActivity
        activity.setVisible()
        binding.routinenameTextview.text=routinename
        binding.goodBtn.setOnClickListener {
            if(!isRoutine) {
                showRoutineDialog()
            }
            else{
                val intent= Intent(context,MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.badBtn.setOnClickListener {
            if(isRoutine){
                showRoutineDelDialog()
            }
            else{
                val intent= Intent(context,MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    fun showRoutineDialog(){
        val routineDialog= RoutineDialog()
        routineDialog.routineListener=object : RoutineDialog.OnRoutineListener{
            override fun setBtnClicked() {
                routinename=routineDialog.routineName.trim()
                Log.d("routineName",routineDialog.routineName)
                routineDialog.dismiss()
                routine_db.routineDao().insertData(RoutineEntity(routineList,routinename))
                Log.d("routineDB",routine_db.routineDao().getAll().toString())

                val intent= Intent(context,MainActivity::class.java)
                startActivity(intent)

            }
        }
        routineDialog.show(requireFragmentManager(),"routineDialog")
    }
    fun showRoutineDelDialog(){
        val routineDelDialog=RoutineDelDialog()
        routineDelDialog.routineDelListener=object :RoutineDelDialog.OnRoutineDelListener{
            override fun okBtnClicked() {
                routine_db.routineDao().deleteData(RoutineEntity(routineList,routinename))
                routineDelDialog.dismiss()
                val intent= Intent(context,MainActivity::class.java)
                startActivity(intent)

            }
        }
        routineDelDialog.show(requireFragmentManager(),"routineDelDialog")

    }

}