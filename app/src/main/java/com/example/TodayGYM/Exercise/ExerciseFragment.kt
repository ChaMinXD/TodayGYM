package com.example.TodayGYM.Exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.TodayGYM.DB.ExerciseDatabase
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
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
        type=arguments?.getString("type").toString()
        place=arguments?.getString("place").toString()
        binding.typePlaceTextview.text=type+" > "+place
        var check=arguments?.getBoolean("check")
        var List=arguments?.getSerializable("routineList")
        if(List!=null) {
            var parse = List.toString().replace("[", "").replace("]", "").split(",")
            for (i in parse) {
                routineList.add(i)
            }
        }

        db= Room.databaseBuilder(requireContext(),ExerciseDatabase::class.java,"ExerciseDB").allowMainThreadQueries().build()
        init()
//        if(check == true){
//            var exName=arguments?.getString("exName")
//            if (exName != null) {
//                routineList.add(exName)
//            }
//            routineAdapter.notifyDataSetChanged()
//        }
        return binding.root
    }
    fun init(){
        //listAdapter
        var exerciseList=ArrayList(db.exerciseDao().getList(type,place))
        listAdapter= ListAdapter(exerciseList)
        binding.listRecyclerview.layoutManager=
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        listAdapter.clickListener=object :ListAdapter.OnItemClickListener{
            override fun btnClicked(item: ExerciseEntity) {
                routineList.add(item.ExName)
                routineAdapter.notifyDataSetChanged()
            }

            override fun textClicked(item: ExerciseEntity) {
                val exercise_explain_Fragment = Exercise_ExplainFragment()
                val bundle=Bundle()
                bundle.putInt("exSrc",item.ExSrc)
                bundle.putString("exExplain",item.ExExplain)
                bundle.putString("exName",item.ExName)
                bundle.putString("type",type)
                bundle.putString("place",place)
                bundle.putSerializable("routineList",routineList)
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