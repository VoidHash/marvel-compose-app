package com.andrepassos.marvelheroes.db

import kotlinx.coroutines.flow.Flow

class MarvelDataSource(
    private val characterDao: CharacterDao,
    private val noteDao: NoteDao
): IMarvelDataSource {

    override suspend fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

    override suspend fun getCharacter(characterId: Int): Flow<CharacterEntity> {
        return characterDao.getCharacter(characterId)
    }

    override suspend fun insertCharacter(characterEntity: CharacterEntity) {
        return characterDao.addCharacter(characterEntity)
    }

    override suspend fun updateCharacter(characterEntity: CharacterEntity) {
        return characterDao.updateCharacter(characterEntity)
    }

    override suspend fun deleteCharacter(characterEntity: CharacterEntity) {
        return characterDao.deleteCharacter(characterEntity)
    }


    override suspend fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNote()
    }

    override suspend fun getNote(characterId: Int): Flow<NoteEntity> {
        return noteDao.getNotes(characterId)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        return noteDao.addNote(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        return noteDao.updateNote(noteEntity)
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        return noteDao.deleteNote(noteEntity)
    }

    override suspend fun deleteAllNotes(characterEntity: CharacterEntity) {
        return noteDao.deleteAllNotes(characterEntity.id)
    }
}