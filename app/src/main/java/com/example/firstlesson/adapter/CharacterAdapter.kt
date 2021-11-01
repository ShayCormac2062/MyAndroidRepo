package com.example.firstlesson.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.CharacterRepository
import com.example.firstlesson.R
import com.example.firstlesson.databinding.BriefCharacterViewBinding
import com.example.firstlesson.entities.Character

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var clickListener: ((characterModel: Character) -> Unit)? = null
    private var characters = CharacterRepository().characters

    inner class CharacterViewHolder(
        private val binding: BriefCharacterViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: Character) = with(binding) {
           briefCharacterPhoto.setImageResource(item.imageUrl)
            briefCharacterName.text = item.name
            characterInfo.setOnClickListener {
                clickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            BriefCharacterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        characters[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = characters.size
}