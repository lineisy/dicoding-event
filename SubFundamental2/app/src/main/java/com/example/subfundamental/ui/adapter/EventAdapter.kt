package com.example.subfundamental.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subfundamental.R
import com.example.subfundamental.databinding.ItemEventBinding
import com.example.subfundamental.ui.main.DetailActivity
import com.example.subfundamental.ui.response.ListEventsItem

class EventAdapter :
    ListAdapter<ListEventsItem,
            EventAdapter.EventViewHolder>(
        DIFF_CALLBACK
    ) {
    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: ListEventsItem) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }


//    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }


    companion object {
        const val VIEW_TYPE_UPCOMING = 1
        const val VIEW_TYPE_FINISHED = 2
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
