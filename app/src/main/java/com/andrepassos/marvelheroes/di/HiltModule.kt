package com.andrepassos.marvelheroes.di

import android.content.Context
import androidx.room.Room
import com.andrepassos.marvelheroes.db.CharacterDao
import com.andrepassos.marvelheroes.db.CharacterEntity
import com.andrepassos.marvelheroes.db.IMarvelDataSource
import com.andrepassos.marvelheroes.db.MarvelDataSource
import com.andrepassos.marvelheroes.db.MarvelDatabase
import com.andrepassos.marvelheroes.db.NoteDao
import com.andrepassos.marvelheroes.network.api.MarvelApiRepository
import com.andrepassos.marvelheroes.network.api.MarvelService
import com.andrepassos.marvelheroes.network.connectivity.ConnectivityMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideApiRepository() = MarvelApiRepository(MarvelService.api)

    @Provides
    fun provideMarvelDatabase(@ApplicationContext context: Context): MarvelDatabase {
        return Room.databaseBuilder(context, MarvelDatabase::class.java, "marvel_database")
            .build()
    }

    @Provides
    fun provideCharacterDao(marvelDatabase: MarvelDatabase): CharacterDao {
        return marvelDatabase.characterDao()
    }

    @Provides
    fun provideNoteDao(marvelDatabase: MarvelDatabase): NoteDao {
        return marvelDatabase.noteDao()
    }

    @Provides
    fun provideMarvelDataSource(characterDao: CharacterDao, noteDao: NoteDao): IMarvelDataSource {
        return MarvelDataSource(characterDao, noteDao)
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityMonitor {
        return ConnectivityMonitor.getInstance(context)
    }
}