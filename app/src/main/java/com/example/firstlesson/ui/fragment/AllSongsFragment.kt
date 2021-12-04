package com.example.firstlesson.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.R
import com.example.firstlesson.adapter.TrackAdapter
import com.example.firstlesson.databinding.FragmentAllSongsBinding

class AllSongsFragment : Fragment() {

    private lateinit var binding: FragmentAllSongsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllSongsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.songs) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = TrackAdapter().apply {
                completeClickListener = { track ->
                    parentFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.container, OneSongFragment(track))
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

}