package com.andrepassos.marvelheroes.db

import kotlinx.coroutines.flow.Flow

class MarvelDataSource(private val characterDao: CharacterDao): IMarvelDataSource {

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
}