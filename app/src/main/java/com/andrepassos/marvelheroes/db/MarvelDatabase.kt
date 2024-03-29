package com.andrepassos.marvelheroes.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CharacterEntity::class, NoteEntity::class], version = 1, exportSchema = false)
abstract class MarvelDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun noteDao(): NoteDao
}