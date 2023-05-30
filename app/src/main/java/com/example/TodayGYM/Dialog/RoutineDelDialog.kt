package com.example.TodayGYM.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.TodayGYM.databinding.RoutineDeleteDialogBinding

class RoutineDelDialog: DialogFragment() {
    lateinit var binding: RoutineDeleteDialogBinding
    interface OnRoutineDelListener{
        fun okBtnClicked()
    }
    var routineDelListener: OnRoutineDelListener?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= RoutineDeleteDialogBinding.inflate(inflater,container,false)
        binding.okBtn.setOnClickListener {
            routineDelListener?.okBtnClicked()
        }
        binding.noBtn.setOnClickListener {
            dismiss()
        }


        return binding.root
    }
}