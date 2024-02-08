package com.andrepassos.marvelheroes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id")
    fun getAllNote(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE characterId == :characterId ORDER BY id ASC")
    fun getNotes(characterId: Int): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM note_table WHERE characterId = :characterId")
    fun deleteAllNotes(characterId: Int)
}