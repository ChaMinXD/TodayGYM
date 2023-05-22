package com.example.TodayGYM.Exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.databinding.FragmentExerciseBinding


class ExerciseFragment : Fragment() {
    lateinit var binding: FragmentExerciseBinding
    lateinit var listAdapter: ListAdapter
    lateinit var routineAdapter: RoutineAdapter
    lateinit var db:ExerciseDatabase
    var routineList=ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExerciseBinding.inflate(inflater,container,false)
        val type=arguments?.getString("type").toString()
        val place=arguments?.getString("place").toString()
        binding.typePlaceTextview.text=type+" > "+place
        db= Room.databaseBuilder(requireContext(),ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().build()
        init()
        return binding.root
    }
    fun init(){
        //listAdapter
        var exerciseList=ArrayList(db.exerciseDao().getAll())
        listAdapter= ListAdapter(exerciseList)
        binding.listRecyclerview.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        listAdapter.clickListener=object :ListAdapter.OnItemClickListener{
            override fun btnClicked(item: ExerciseEntity) {
                routineList.add(item.ExName)
                routineAdapter.notifyDataSetChanged()
            }

            override fun textClicked(item: ExerciseEntity) {

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