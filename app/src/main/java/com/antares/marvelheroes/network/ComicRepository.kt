package com.antares.marvelheroes.network

import android.util.Log
import com.antares.marvelheroes.data.characters.CharacterResponse
import com.antares.marvelheroes.data.comics.ComicResponse
import com.antares.marvelheroes.data.events.EventResponse
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private val api: ComicApi
): IComicRepository {

    override suspend fun getCharacters(): CharacterResponse {
        return try {
            api.getCharacters()
        } catch (e: Exception){
            throw CharactersException()
        }
    }

    override suspend fun getComics(): ComicResponse {
        return try {
            api.getComics()
        } catch (e: Exception){
            throw ComicsException()
        }
    }

    override suspend fun getEvents(): EventResponse {
        return try {
            api.getEvents()
        } catch (e: Exception){
            throw EventsException()
        }
    }

    override suspend fun getCharacter(characterId: Int): CharacterResponse {
        return try {
            Log.i("ComicResponse",api.getCharacter(characterId).toString())
            api.getCharacter(characterId)
        } catch (e: Exception){
            throw CharactersException()
        }
    }
}

class CharactersException() : RuntimeException()
class ComicsException() : RuntimeException()
class EventsException() : RuntimeException()