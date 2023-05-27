package com.example.TodayGYM.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.TodayGYM.databinding.RoutineDialogBinding


class RoutineDialog:DialogFragment() {
    var routineName="나만의 루틴이름 1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable=true
    }
    lateinit var binding:RoutineDialogBinding
    interface OnRoutineListener{
        fun setBtnClicked()
    }
    var routineListener: OnRoutineListener?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=RoutineDialogBinding.inflate(inflater,container,false)
        binding.okBtn.setOnClickListener {
            binding.routineGroup1.visibility=View.INVISIBLE
            binding.routineGroup2.visibility=View.VISIBLE
        }
        binding.noBtn.setOnClickListener {
            dismiss()
        }
        binding.setBtn.setOnClickListener {
            routineName=binding.setnameEdittext.text.toString()
            routineListener?.setBtnClicked()
        }

        return binding.root
    }
}