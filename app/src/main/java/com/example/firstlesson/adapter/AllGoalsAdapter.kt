package com.example.firstlesson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.databinding.OneGoalViewBinding
import com.example.firstlesson.entity.Goal

class AllGoalsAdapter(private var goalsList: ArrayList<Goal>) : ListAdapter<Goal, AllGoalsAdapter.AllGoalsViewHolder>(GoalDiffCallback()) {

    var infoClickListener: ((model: Goal) -> Unit)? = null
    var deleteClickListener: ((model: Goal) -> Unit)? = null

    inner class AllGoalsViewHolder(
        private val binding: OneGoalViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Goal) = with(binding) {
            tvTitle.text = if (item.title != "") item.title else "Название не указано"
            briefTaskView.setOnClickListener {
                infoClickListener?.invoke(item)
            }
            deleteTaskBtn.setOnClickListener {
                deleteClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGoalsViewHolder =
        AllGoalsViewHolder(
            OneGoalViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: AllGoalsViewHolder, position: Int) =
        holder.bind(goalsList[position])

    override fun getItemCount(): Int = goalsList.size
}

class GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {

    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean = areItemsTheSame(oldItem, newItem)

}