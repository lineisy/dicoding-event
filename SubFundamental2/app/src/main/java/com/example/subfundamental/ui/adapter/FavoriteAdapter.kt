package com.example.subfundamental.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subfundamental.data.local.FavoriteEntity
import com.example.subfundamental.databinding.ItemEventBinding
import com.example.subfundamental.ui.adapter.EventAdapter.Companion.DIFF_CALLBACK
import com.example.subfundamental.ui.adapter.EventAdapter.EventViewHolder
import com.example.subfundamental.ui.main.DetailActivity
import com.example.subfundamental.ui.response.ListEventsItem

class FavoriteAdapter:
    ListAdapter<FavoriteEntity,
            FavoriteAdapter.FavoriteViewHolder>(
        DIFF_CALLBACK
            )
{
    inner class FavoriteViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: FavoriteEntity) {
            binding.tvNama.text = event.name
            binding.tvOverView.text = event.summary

            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.ivContent)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("EXTRA_ID_EVENT", event.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        const val VIEW_TYPE_UPCOMING = 1
        const val VIEW_TYPE_FINISHED = 2
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}