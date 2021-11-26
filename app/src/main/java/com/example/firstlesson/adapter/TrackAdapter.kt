package com.example.firstlesson.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.databinding.OneTrackElementBinding
import com.example.firstlesson.entity.Track
import com.example.firstlesson.entity.TracksRepository

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    var completeClickListener: ((trackModel: Track) -> Unit)? = null

    inner class TrackViewHolder(
        private val binding: OneTrackElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: Track) = with(binding) {
            trackCover.setImageResource(item.cover)
            trackName.text = item.name ?: "Название не указано"
            trackAuthor.text = item.author ?: "Автор неизвестен"
            playTrack.setOnClickListener {
                completeClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder =
        TrackViewHolder(
            OneTrackElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(TracksRepository.songList[position])
    }

    override fun getItemCount(): Int = TracksRepository.songList.size

}