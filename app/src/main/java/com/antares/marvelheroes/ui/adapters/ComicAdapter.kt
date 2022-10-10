package com.antares.marvelheroes.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.marvelheroes.data.comics.Result
import com.antares.marvelheroes.databinding.ItemComicsBinding
import com.bumptech.glide.Glide

class ComicAdapter (private val comicsList: MutableList<Result>) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>(){

    private var onItemClickListener: ((Int) -> Unit)? = null

    inner class ComicViewHolder(var binding: ItemComicsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Result){
            val urlImage = comic.thumbnail.path.plus("/portrait_medium.").plus(comic.thumbnail.extension)
            binding.apply {
                tvComic.text = comic.title
                Glide.with(ivComic.context).load(urlImage).into(ivComic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(ItemComicsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comicsList[position]
        holder.bind(comicsList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(comic.id) }
        }
    }

    override fun getItemCount() = comicsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateComicList(results: List<Result>){
        comicsList.clear()
        comicsList.addAll(results)
        notifyDataSetChanged()
    }
}