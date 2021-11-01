package com.example.firstlesson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstlesson.databinding.CharacterInfoViewBinding
import com.example.firstlesson.entities.Character

class CharacterInfoFragment(private var character: Character): Fragment() {

    private lateinit var binding: CharacterInfoViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterInfoViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            characterPhoto.setImageResource(character.imageUrl)
            characterName.text = character.name
            characterDescription.text = character.description
        }
    }
}