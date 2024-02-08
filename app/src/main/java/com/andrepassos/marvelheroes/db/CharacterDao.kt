package com.andrepassos.marvelheroes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character_table WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCharacter(character: CharacterEntity)

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacter(character: CharacterEntity)
}