package com.example.firstlesson.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.firstlesson.CharacterRepository
import com.example.firstlesson.databinding.CharacterInfoViewBinding
import com.example.firstlesson.entities.Character

class CharacterInfoFragment(var id1: Int): Fragment() {

    private lateinit var binding: CharacterInfoViewBinding
    @RequiresApi(Build.VERSION_CODES.N)
    private var character = CharacterRepository().characters.stream().filter { character ->
        character.id == id1
    }.findFirst().get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterInfoViewBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            characterPhoto.setImageResource(character.imageUrl)
            characterName.text = character.name
            characterDescription.text = character.description
        }
    }
}