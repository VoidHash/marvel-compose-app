package com.andrepassos.marvelheroes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andrepassos.marvelheroes.model.Note

@Entity(tableName = "note_table")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val characterId: Int,
    val title: String,
    val text: String
) {
    companion object {
        fun fromNote(note: Note): NoteEntity {
            return NoteEntity (
                id = 0,
                characterId = note.characterId,
                title = note.title,
                text = note.text
            )
        }
    }
}