package com.antares.marvelheroes.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antares.marvelheroes.data.characters.Result
import com.antares.marvelheroes.databinding.ItemCharactersBinding
import com.bumptech.glide.Glide

class CharacterAdapter (private val charactersList: MutableList<Result>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    inner class CharacterViewHolder(var binding: ItemCharactersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(character: Result){
            val urlImage = character.thumbnail.path.plus("/portrait_medium.").plus(character.thumbnail.extension)
            binding.apply {
                tvName.text = character.name
                Glide.with(ivCharacter.context).load(urlImage).into(ivCharacter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(ItemCharactersBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = charactersList[position]
        holder.bind(charactersList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(character.id) }
        }
    }

    override fun getItemCount() = charactersList.size

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharactersList(results: List<Result>){
        charactersList.clear()
        charactersList.addAll(results)
        notifyDataSetChanged()
    }


}