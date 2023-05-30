package com.example.TodayGYM.Exercise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.DB.migration_1_2
import com.example.TodayGYM.DB.migration_2_3
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.FragmentExerciseBinding


class ExerciseFragment : Fragment() {
    lateinit var binding: FragmentExerciseBinding
    lateinit var listAdapter: ListAdapter
    lateinit var routineAdapter: RoutineAdapter
    lateinit var db:ExerciseDatabase
    lateinit var type:String
    lateinit var place:String
    var routineList=ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseBinding.inflate(inflater,container,false)
        type= App.prefs.getString("type","")
        place=App.prefs.getString("place","")
        binding.typePlaceTextview.text=type+" > "+place
        var List=arguments?.getSerializable("routineList")
        if(List.toString()!="[]"&&List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").trim().split(",")
            for (i in parse) {
                routineList.add(i.trim())
            }
        }

        db= Room.databaseBuilder(requireContext(), ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().addMigrations(
            migration_1_2
        ).addMigrations(migration_2_3).build()
        init()

        return binding.root
    }
    fun init(){
        binding.startBtn.setOnClickListener {
            val intent= Intent(context,ExerciseActivity::class.java)
            intent.putExtra("routineList",routineList)
            intent.putExtra("index",0)
            intent.putExtra("routinename","오늘의 루틴")
            intent.putExtra("isRoutine",false)

            startActivity(intent)
        }
        //listAdapter
        var exerciseList=ArrayList(db.exerciseDao().getList(type,place))
        listAdapter= ListAdapter(exerciseList)
        binding.listRecyclerview.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        listAdapter.clickListener=object :ListAdapter.OnItemClickListener{
            override fun btnClicked(item: ExerciseEntity) {
                routineList.add(item.ExName.trim())
                routineAdapter.notifyDataSetChanged()

            }

            override fun textClicked(item: ExerciseEntity) {
                val exercise_explain_Fragment = Exercise_ExplainFragment()
                val bundle=Bundle()
                bundle.putInt("exSrc",item.ExSrc)
                bundle.putString("exExplain",item.ExExplain)
                bundle.putString("exName",item.ExName)
                bundle.putSerializable("routineList",routineList)
                bundle.putBoolean("isRoutine",false)
                exercise_explain_Fragment.arguments=bundle
                val activity=activity as MainActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.fl_container,exercise_explain_Fragment).commit()
            }

        }
        binding.listRecyclerview.adapter=listAdapter
        //routineAdapter
        routineAdapter= RoutineAdapter(routineList)
        routineAdapter.clickListener=object :RoutineAdapter.OnItemClickListener{
            override fun xBtnClicked(item: String) {
                routineList.remove(item)
                routineAdapter.notifyDataSetChanged()
            }

        }
        binding.routineRecyclerview.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.routineRecyclerview.adapter=routineAdapter



    }
}