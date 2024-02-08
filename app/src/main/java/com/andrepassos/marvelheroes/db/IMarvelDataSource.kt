package com.andrepassos.marvelheroes.db

import kotlinx.coroutines.flow.Flow

interface IMarvelDataSource {

    suspend fun getAllCharacters(): Flow<List<CharacterEntity>>

    suspend fun getCharacter(characterId: Int): Flow<CharacterEntity>

    suspend fun insertCharacter(characterEntity: CharacterEntity)

    suspend fun updateCharacter(characterEntity: CharacterEntity)

    suspend fun deleteCharacter(characterEntity: CharacterEntity)


    suspend fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun getNote(characterId: Int): Flow<NoteEntity>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun deleteNote(noteEntity: NoteEntity)

    suspend fun deleteAllNotes(characterId: CharacterEntity)
}