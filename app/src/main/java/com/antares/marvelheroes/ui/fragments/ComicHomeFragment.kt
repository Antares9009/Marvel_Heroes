package com.antares.marvelheroes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.antares.marvelheroes.data.characters.`Result` as Characters
import com.antares.marvelheroes.data.comics.`Result` as Comics
import com.antares.marvelheroes.data.events.`Result` as Events
import com.antares.marvelheroes.databinding.FragmentComicHomeBinding
import com.antares.marvelheroes.ui.ComicViewModel
import com.antares.marvelheroes.ui.adapters.CharacterAdapter
import com.antares.marvelheroes.ui.adapters.ComicAdapter
import com.antares.marvelheroes.ui.adapters.EventAdapter
import com.antares.marvelheroes.utils.Constants.Companion.GENERIC_ERROR
import com.antares.marvelheroes.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicHomeFragment : Fragment() {

    private var _binding: FragmentComicHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ComicViewModel> { defaultViewModelProviderFactory }
    private val characterAdapter = CharacterAdapter(mutableListOf())
    private val comicAdapter = ComicAdapter(mutableListOf())
    private val eventAdapter = EventAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initViews()
        initClickListener()
        initObservers()
    }

    private fun initViewModels(){
        viewModel.getCharacters()
        //viewModel.getComics()
        //viewModel.getEvents()
    }

    private fun initViews(){
        binding.rvCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvComics.apply {
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        }
        binding.rvEvents.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initClickListener(){
        characterAdapter.setOnItemClickListener {
            val action = ComicHomeFragmentDirections.actionComicHomeFragmentToCharacterDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initObservers(){
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleCharacterResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleCharacterFailure(response.messageError)
                }
            }
        }

        viewModel.comics.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleComicResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleComicFailure(response.messageError)
                }
            }
        }

        viewModel.events.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    handleEventResponse(response.value)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    handleEventFailure(response.messageError)
                }
            }
        }
    }

    private fun handleCharacterResponse(results: List<Characters>){
        characterAdapter.updateCharactersList(results ?: emptyList())
    }

    private fun handleCharacterFailure(message: String?){
        Snackbar.make(binding.rvCharacters, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleComicResponse(results: List<Comics>){
        comicAdapter.updateComicList(results ?: emptyList())
    }

    private fun handleComicFailure(message: String?){
        Snackbar.make(binding.rvComics, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleEventResponse(results: List<Events>){
        eventAdapter.updateEventsList(results ?: emptyList())
    }

    private fun handleEventFailure(message: String?){
        Snackbar.make(binding.rvEvents, message ?: GENERIC_ERROR, Snackbar.LENGTH_SHORT).show()
    }
}