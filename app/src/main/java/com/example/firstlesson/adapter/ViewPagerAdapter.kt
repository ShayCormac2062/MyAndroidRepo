package com.example.firstlesson.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.databinding.ElementViewBinding
import com.example.firstlesson.databinding.ViewPagerElementViewBinding
import com.example.firstlesson.entity.Element

class ViewPagerAdapter(private var pictures: ArrayList<Int>):
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(
        private val binding: ViewPagerElementViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(id: Int) = binding.picture.setImageResource(id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            ViewPagerElementViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    override fun getItemCount(): Int = pictures.size
}