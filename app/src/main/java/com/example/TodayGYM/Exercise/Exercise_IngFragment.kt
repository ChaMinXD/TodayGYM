package com.example.TodayGYM.Exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.TodayGYM.R
import com.example.TodayGYM.databinding.FragmentExerciseIngBinding

class Exercise_IngFragment : Fragment() {
    lateinit var binding:FragmentExerciseIngBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise__ing, container, false)
    }


}