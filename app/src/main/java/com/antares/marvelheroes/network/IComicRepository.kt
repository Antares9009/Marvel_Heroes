package com.antares.marvelheroes.network

import com.antares.marvelheroes.data.characters.CharacterResponse
import com.antares.marvelheroes.data.comics.ComicResponse
import com.antares.marvelheroes.data.events.EventResponse

interface IComicRepository {

    suspend fun getCharacters() : CharacterResponse

    suspend fun getComics() : ComicResponse

    suspend fun getEvents() : EventResponse

    suspend fun getCharacter(characterId: Int) : CharacterResponse
}