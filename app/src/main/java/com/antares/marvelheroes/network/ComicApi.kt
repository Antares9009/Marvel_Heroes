package com.antares.marvelheroes.network

import com.antares.marvelheroes.data.characters.CharacterResponse
import com.antares.marvelheroes.data.comics.ComicResponse
import com.antares.marvelheroes.data.events.EventResponse
import com.antares.marvelheroes.utils.Constants.Companion.API_KEY
import com.antares.marvelheroes.utils.Constants.Companion.HASH
import com.antares.marvelheroes.utils.Constants.Companion.TIME_STAMP
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") api: String = API_KEY,
        @Query("ts") timestamp: String = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ) : CharacterResponse

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int,
        @Query("apikey") api: String = API_KEY,
        @Query("ts") timestamp: String = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ) : CharacterResponse

    @GET("comics")
    suspend fun getComics(
        @Query("apikey") api: String = API_KEY,
        @Query("ts") timestamp: String = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ) : ComicResponse

    @GET("events")
    suspend fun getEvents(
        @Query("apikey") api: String = API_KEY,
        @Query("ts") timestamp: String = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ) : EventResponse


}