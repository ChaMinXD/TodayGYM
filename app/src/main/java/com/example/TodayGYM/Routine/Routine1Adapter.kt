package com.example.TodayGYM.Routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TodayGYM.DB.ExerciseEntity
import com.example.TodayGYM.DB.RoutineEntity
import com.example.TodayGYM.databinding.RowRoutine1Binding

class Routine1Adapter(var items: ArrayList<RoutineEntity>) :
    RecyclerView.Adapter<Routine1Adapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowRoutine1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.routinePlayBtn.setOnClickListener {
                clickListener?.playClicked(items[adapterPosition])
            }
            binding.routineDetailBtn.setOnClickListener {
                clickListener?.detailClicked(items[adapterPosition])
            }
            binding.routineDelBtn.setOnClickListener {
                clickListener?.delClicked(items[adapterPosition])
            }

        }
    }

    var clickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun playClicked(item: RoutineEntity)
        fun detailClicked(item: RoutineEntity)
        fun delClicked(item: RoutineEntity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowRoutine1Binding =
            RowRoutine1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.routineTextview.text = items[position].RoutineName
    }
    fun update(newlist:ArrayList<RoutineEntity>){
        items.clear()
        items.addAll(newlist)
        this.notifyDataSetChanged()
    }
}