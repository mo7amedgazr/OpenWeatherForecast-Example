package com.example.upcomings.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upcomings.databinding.ItemUpcomingWeatherBinding
import com.example.upcomings.domain.entity.WeatherListItem


class UpcomingListAdapter :
    ListAdapter<WeatherListItem, UpcomingListAdapter.WeatherViewHolder>(Companion) {

    private var context: Context? = null

    inner class WeatherViewHolder(val binding: ItemUpcomingWeatherBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<WeatherListItem>() {
        override fun areItemsTheSame(
            oldItem: WeatherListItem,
            newItem: WeatherListItem
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: WeatherListItem,
            newItem: WeatherListItem
        ): Boolean =
            false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUpcomingWeatherBinding.inflate(layoutInflater, parent, false)
        return WeatherViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.weatherItem = currentItem
        holder.binding.executePendingBindings()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}