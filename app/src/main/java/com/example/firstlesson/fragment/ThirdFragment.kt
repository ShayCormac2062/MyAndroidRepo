package com.example.firstlesson.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.MainActivity
import com.example.firstlesson.adapter.ElementAdapter
import com.example.firstlesson.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(ElementAdapter((requireActivity() as MainActivity).elements))
    }

    private fun init(adapter: ElementAdapter) {
        binding.apply {
            listTwo.layoutManager = LinearLayoutManager(requireActivity()).apply {
                orientation = RecyclerView.VERTICAL
            }
            listTwo.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            listTwo.adapter = adapter
        }
    }
}