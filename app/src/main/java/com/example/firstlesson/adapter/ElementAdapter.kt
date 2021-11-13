package com.example.firstlesson.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.databinding.ElementViewBinding
import com.example.firstlesson.entity.Element

class ElementAdapter(elements: List<Element>): RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {

    private var elements: List<Element>? = elements

    inner class ElementViewHolder(
        private val binding: ElementViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: Element) = with(binding) {
            nameOfElement.text = item.name
            elementDescription.text = item.description
            viewPager.adapter = ViewPagerAdapter(item.pictures)
            position.text = "Позиция: ${(elements?.indexOf(item))?.plus(1)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder =
        ElementViewHolder(
            ElementViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        elements?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        elements?.let {
            return it.size
        }
        return 0
    }
}