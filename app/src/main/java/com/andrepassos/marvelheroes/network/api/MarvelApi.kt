package com.andrepassos.marvelheroes.network.api

import com.andrepassos.marvelheroes.model.CharacterApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(@Query("nameStartsWith") name: String): Call<CharacterApiResponse>
}