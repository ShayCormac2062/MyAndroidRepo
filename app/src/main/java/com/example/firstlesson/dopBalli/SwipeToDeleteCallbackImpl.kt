package com.example.firstlesson.dopBalli

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.databinding.FragmentSecondBinding
import com.example.firstlesson.entity.Element

class SwipeToDeleteCallbackImpl(private var elements: ArrayList<Element>, context: Context, private var binding: FragmentSecondBinding): SwipeToDeleteCallback(context) {

    @SuppressLint("NotifyDataSetChanged")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        elements.removeAt(viewHolder.adapterPosition)
        binding.listOne.adapter?.notifyDataSetChanged()
    }
}