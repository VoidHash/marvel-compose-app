package com.andrepassos.marvelheroes.network.api

import androidx.compose.runtime.mutableStateOf
import com.andrepassos.marvelheroes.model.CharacterApiResponse
import com.andrepassos.marvelheroes.model.CharacterResult
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepository(private val api: MarvelApi) {

    val characters =
        MutableStateFlow<NetworkResult<CharacterApiResponse>>(NetworkResult.Initial())

    val characterDetails = mutableStateOf<CharacterResult?>(null)

    fun query(query: String) {
        characters.value = NetworkResult.Loading()
        api.getCharacters(query)
            .enqueue(object : Callback<CharacterApiResponse> {
                override fun onResponse(
                    call: Call<CharacterApiResponse>,
                    response: Response<CharacterApiResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            characters.value = NetworkResult.Success(it)
                        }
                    } else {
                        characters.value = NetworkResult.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<CharacterApiResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                        characters.value = NetworkResult.Error(it)
                    }
                    t.printStackTrace()
                }
            })
    }

    fun getSingleCharacter(id: Int?) {
        id?.let {
            characterDetails.value = characters.value.data?.characterData?.results?.firstOrNull {
                characterResult -> characterResult?.id == id
            }
        }
    }
}