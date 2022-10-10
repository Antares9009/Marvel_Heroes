package com.antares.marvelheroes.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.marvelheroes.data.events.Result
import com.antares.marvelheroes.databinding.ItemEventsBinding
import com.bumptech.glide.Glide

class EventAdapter (private val eventsList: MutableList<Result>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    private var onItemClickListener: ((Int) -> Unit) ? = null

    inner class EventViewHolder(var binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: Result){
            val urlImage = event.thumbnail.path.plus("/portrait_medium.").plus(event.thumbnail.extension)
            binding.apply {
                tvEvent.text = event.title
                Glide.with(ivEvent.context).load(urlImage).into(ivEvent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(ItemEventsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventsList[position]
        holder.bind(eventsList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let{ it(event.id)}
        }
    }

    override fun getItemCount() = eventsList.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEventsList(results: List<Result>){
        eventsList.clear()
        eventsList.addAll(results)
        notifyDataSetChanged()
    }
}