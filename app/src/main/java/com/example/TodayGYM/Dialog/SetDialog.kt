package com.example.TodayGYM.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.TodayGYM.databinding.SetDialogBinding

class SetDialog:DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable=true
    }
    lateinit var binding:SetDialogBinding
    var set=4
    interface OnSetListener{
        fun setBtnClicked()
    }
    var setListener: OnSetListener?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= SetDialogBinding.inflate(inflater,container,false)
        binding.plusBtn.setOnClickListener {
            set=binding.dialogSet.text.toString().toInt()
            set++
            binding.dialogSet.text=set.toString()
        }
        binding.minusBtn.setOnClickListener {
            set=binding.dialogSet.text.toString().toInt()
            if(set>1){
                set--
                binding.dialogSet.text=set.toString()
            }
        }
        binding.setBtn.setOnClickListener {
            setListener?.setBtnClicked()
        }
        return binding.root
    }
}