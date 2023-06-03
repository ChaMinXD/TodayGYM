package com.example.TodayGYM.Routine

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.TodayGYM.DB.*
import com.example.TodayGYM.Dialog.RoutineDelDialog
import com.example.TodayGYM.Exercise.ExerciseActivity
import com.example.TodayGYM.Exercise.Exercise_ExplainFragment
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.FragmentRoutineBinding


class RoutineFragment : Fragment() {
    lateinit var binding: FragmentRoutineBinding
    lateinit var db: ExerciseDatabase
    lateinit var routine1Adapter: Routine1Adapter
    lateinit var routine2Adapter: Routine2Adapter
    var isok=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(requireContext(), ExerciseDatabase::class.java, "ExerciseDB")
            .allowMainThreadQueries().addMigrations(
                migration_1_2
            ).addMigrations(migration_2_3).build()
        init()

        return binding.root
    }

    fun init() {
        binding.ExerciseGoalTextview.text= App.prefs.getString("goal","목표를 입력하세요")

        var routineEntitylist=db.routineDao().getAll()
        routine1Adapter= Routine1Adapter(routineEntitylist as ArrayList<RoutineEntity>)
        binding.routineRecyclerview1.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)


        routine1Adapter.clickListener=object :Routine1Adapter.OnItemClickListener{
            override fun playClicked(item: RoutineEntity) {
                val intent= Intent(requireContext(),ExerciseActivity::class.java)
               var routinelist=ArrayList(item.Routine)
                intent.putExtra("routineList",routinelist)
                intent.putExtra("routinename",item.RoutineName)
                intent.putExtra("isRoutine",true)
                startActivity(intent)
            }

            override fun detailClicked(item: RoutineEntity) {
                showDetailRoutine(item)
            }

            override fun delClicked(item: RoutineEntity) {
                db.routineDao().deleteData(item)
                routineEntitylist=db.routineDao().getAll()
                routine1Adapter.update(routineEntitylist as ArrayList<RoutineEntity>)
            }
        }
        binding.routineRecyclerview1.adapter=routine1Adapter
        binding.backIcon.setOnClickListener {
            binding.routineGroup2.visibility=View.INVISIBLE
            binding.routineGroup1.visibility=View.VISIBLE
        }


    }
    fun showDetailRoutine(item:RoutineEntity){
        binding.startIcon.setOnClickListener {
            val intent= Intent(requireContext(),ExerciseActivity::class.java)
            var routinelist=ArrayList(item.Routine)
            intent.putExtra("routineList",routinelist)
            intent.putExtra("routinename",item.RoutineName)
            intent.putExtra("isRoutine",true)
            startActivity(intent)
        }
        binding.routineGroup1.visibility=View.INVISIBLE
        binding.routineGroup2.visibility=View.VISIBLE
        var routineList=ArrayList<ExerciseEntity>()
        for(i in item.Routine){
            routineList.add(db.exerciseDao().getExercise(i))
        }

        routine2Adapter=Routine2Adapter(routineList)
        binding.routineRecyclerview2.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        routine2Adapter.clickListener=object :Routine2Adapter.OnItemClickListener{
            override fun detailClicked(item: ExerciseEntity) {
                val exercise_explain_Fragment = Exercise_ExplainFragment()
                val bundle=Bundle()
                bundle.putInt("exSrc",item.ExSrc)
                bundle.putString("exExplain",item.ExExplain)
                bundle.putString("exName",item.ExName)
                bundle.putSerializable("routineList",routineList)
                bundle.putBoolean("isRoutine",true)
                exercise_explain_Fragment.arguments=bundle
                val activity=activity as MainActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.fl_container,exercise_explain_Fragment).commit()
            }

        }
        binding.routineRecyclerview2.adapter=routine2Adapter
    }



}