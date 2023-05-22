package com.example.TodayGYM.Exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TodayGYM.databinding.RowRoutineBinding

class RoutineAdapter(var items:ArrayList<String>):RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:RowRoutineBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.routineXbtn.setOnClickListener {
                clickListener?.xBtnClicked(items[adapterPosition])
            }

        }
    }
    var clickListener: OnItemClickListener?=null
    interface  OnItemClickListener{
        fun xBtnClicked(item:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:RowRoutineBinding=
            RowRoutineBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.routineTextview.text=items[position]
    }

}