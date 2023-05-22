package com.example.TodayGYM.Exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.databinding.RowListBinding

class ListAdapter(var items:ArrayList<ExerciseEntity>):RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowListBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.listBtn.setOnClickListener {
                clickListener?.btnClicked(items[adapterPosition])
            }
            binding.listTextview.setOnClickListener {
                clickListener?.textClicked(items[adapterPosition])
            }
        }
    }
    var clickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun btnClicked(item:ExerciseEntity)
        fun textClicked(item: ExerciseEntity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:RowListBinding=
            RowListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.listTextview.text=items[position].ExName
    }
}