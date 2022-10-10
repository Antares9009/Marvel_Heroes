package com.antares.marvelheroes.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.antares.marvelheroes.databinding.FragmentCharacterDetailBinding
import com.antares.marvelheroes.ui.ComicViewModel
import com.antares.marvelheroes.data.characters.Result
import com.antares.marvelheroes.utils.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var _binding: FragmentCharacterDetailBinding
    private val binding get() = _binding
    private val viewModel by viewModels<ComicViewModel>{defaultViewModelProviderFactory}
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharacterDetails()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private fun getCharacterDetails() {
        viewModel.getCharacterDetail(args.characterId)
    }

    private fun initObservers(){
        viewModel.detailCharacter.observe(viewLifecycleOwner) { character ->
            when(character){
                is Resource.Loading -> {
                    Log.i("DetailsCharacter","Loading")
                }
                is Resource.Success -> {
                    //handleSuccess(character.value)
                }
                is Resource.Failure -> {
                    handleFailure()
                }
            }
        }
    }

    private fun handleFailure() {
        Log.i("DetailsFragment", "FAilure")
    }

    private fun handleSuccess(character: Result){
        val urlImage = character.thumbnail.path.plus("/portrait_medium.").plus(character.thumbnail.extension)
        binding.apply {
            tvDetailName.text = character.name
            Glide.with(ivDetail.context).load(urlImage).into(ivDetail)
        }
    }

}