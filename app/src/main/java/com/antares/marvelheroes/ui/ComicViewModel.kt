package com.antares.marvelheroes.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antares.marvelheroes.network.IComicRepository
import com.antares.marvelheroes.utils.Resource
import com.antares.marvelheroes.data.characters.`Result` as Characters
import com.antares.marvelheroes.data.comics.`Result` as Comics
import com.antares.marvelheroes.data.events.`Result` as Events
import com.antares.marvelheroes.network.CharactersException
import com.antares.marvelheroes.network.ComicsException
import com.antares.marvelheroes.network.EventsException
import com.antares.marvelheroes.utils.Constants.Companion.GENERIC_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val repository: IComicRepository
) : ViewModel() {

    private val _characters : MutableLiveData<Resource<List<Characters>>> = MutableLiveData()
    val characters: LiveData<Resource<List<Characters>>>
        get() = _characters

    private val _comics : MutableLiveData<Resource<List<Comics>>> = MutableLiveData()
    val comics: LiveData<Resource<List<Comics>>>
        get() = _comics

    private val _events : MutableLiveData<Resource<List<Events>>> = MutableLiveData()
    val events: LiveData<Resource<List<Events>>>
        get() = _events

    private val _detailCharacter: MutableLiveData<Resource<List<Characters>>> = MutableLiveData()
    val detailCharacter : LiveData<Resource<List<Characters>>>
        get() =  _detailCharacter


    private val handlerException = CoroutineExceptionHandler { _, exception ->
        when(exception) {
            is CharactersException -> {
                _characters.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
            is EventsException -> {
                _characters.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
            is ComicsException -> {
                _comics.value = Resource.Failure(exception.message ?: GENERIC_ERROR)
            }
        }
    }

    fun getCharacters() = viewModelScope.launch(handlerException) {
        _characters.postValue(Resource.Loading)
        _characters.postValue(Resource.Success(repository.getCharacters().data.results))
    }

    fun getComics() = viewModelScope.launch(handlerException) {
        _comics.postValue(Resource.Loading)
        _comics.postValue(Resource.Success(repository.getComics().data.results))
    }

    fun getEvents() = viewModelScope.launch(handlerException) {
        _events.postValue(Resource.Loading)
        _events.postValue(Resource.Success(repository.getEvents().data.results))
    }

    fun getCharacterDetail(characterId: Int) = viewModelScope.launch(handlerException) {
        Log.i("ComicResponse",characterId.toString())
        Log.i("ComicResponse",repository.getCharacter(characterId).toString())
        _detailCharacter.postValue(Resource.Loading)
        _detailCharacter.postValue(Resource.Success(repository.getCharacter(characterId).data.results))
    }
}