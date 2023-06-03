package com.example.TodayGYM.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.GoalDialogBinding
import com.example.TodayGYM.databinding.RoutineDeleteDialogBinding

class GoalDialog:DialogFragment() {
    lateinit var binding: GoalDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= GoalDialogBinding.inflate(inflater,container,false)
        binding.setBtn.setOnClickListener {
            App.prefs.setString("goal",binding.setnameEdittext.text.toString())
            dismiss()
        }
        return binding.root
    }
}