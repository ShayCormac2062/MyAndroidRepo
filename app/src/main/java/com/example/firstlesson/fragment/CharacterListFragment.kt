package com.example.firstlesson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.R
import com.example.firstlesson.adapter.CharacterAdapter
import com.example.firstlesson.databinding.CharacterListBinding

class CharacterListFragment: Fragment() {

    private lateinit var binding: CharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.characterList) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }
            adapter = CharacterAdapter().apply {
                clickListener = {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, CharacterInfoFragment(it.id))
                        .addToBackStack(null)
                        .commit()
                }
            }

        }
    }
}