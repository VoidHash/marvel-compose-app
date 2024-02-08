package com.andrepassos.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrepassos.marvelheroes.db.CharacterEntity
import com.andrepassos.marvelheroes.db.IMarvelDataSource
import com.andrepassos.marvelheroes.db.NoteEntity
import com.andrepassos.marvelheroes.model.CharacterResult
import com.andrepassos.marvelheroes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val dataSource: IMarvelDataSource)
    : ViewModel() {

    val currentCharacter = MutableStateFlow<CharacterEntity?>(null)
    val collection = MutableStateFlow<List<CharacterEntity>>(listOf())
    val notes = MutableStateFlow<List<NoteEntity>>(listOf())

    init {
        getCollection()
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch {
            dataSource.getAllNotes().collect{
                notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.insertNote(NoteEntity.fromNote(note))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.updateNote(NoteEntity.fromNote(note))
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.deleteNote(note)
        }
    }

    fun getCollection() {
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
            dataSource.deleteAllNotes(character)
            dataSource.deleteCharacter(character)
        }
    }
}