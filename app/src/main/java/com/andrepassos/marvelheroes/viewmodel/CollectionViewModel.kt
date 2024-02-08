package com.andrepassos.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrepassos.marvelheroes.db.CharacterEntity
import com.andrepassos.marvelheroes.db.IMarvelDataSource
import com.andrepassos.marvelheroes.model.CharacterResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val dataSource: IMarvelDataSource)
    : ViewModel() {

    val currentCharacter = MutableStateFlow<CharacterEntity?>(null)
    val collection = MutableStateFlow<List<CharacterEntity>>(listOf())

    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            dataSource.getAllCharacters().collect {
                collection.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId: Int?) {
        characterId?.let {
            viewModelScope.launch {
                dataSource.getCharacter(it).collect {
                    currentCharacter.value = it
                }
            }
        }
    }

    fun addCharacter(character: CharacterResult) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.insertCharacter(CharacterEntity.fromCharacter(character))
        }
    }

    fun deleteCharacter(character: CharacterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.deleteCharacter(character)
        }
    }
}