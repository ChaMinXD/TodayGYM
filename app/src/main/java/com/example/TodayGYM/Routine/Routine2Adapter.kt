package com.example.TodayGYM.Routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.DB.RoutineEntity
import com.example.TodayGYM.databinding.RowRoutine1Binding
import com.example.TodayGYM.databinding.RowRoutine2Binding

class Routine2Adapter(var items: ArrayList<ExerciseEntity>) :
    RecyclerView.Adapter<Routine2Adapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowRoutine2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.routineDetailBtn.setOnClickListener {
                clickListener?.detailClicked(items[adapterPosition])
            }

        }
    }

    var clickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun detailClicked(item: ExerciseEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowRoutine2Binding =
            RowRoutine2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.routineTextview.text = items[position].ExName
    }
    fun update(newlist:ArrayList<ExerciseEntity>){
        items.clear()
        items.addAll(newlist)
        this.notifyDataSetChanged()
    }
}