package com.andrepassos.marvelheroes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andrepassos.marvelheroes.model.CharacterResult
import com.andrepassos.marvelheroes.util.DataUtil.comicsToString

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
) {

    companion object {
        fun fromCharacter(character: CharacterResult) =
            CharacterEntity(
                id = 0,
                apiId = character.id,
                name = character.name,
                thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
                comics = character.comics?.items?.mapNotNull { it?.name }?.comicsToString() ?: "no comics",
                description = character.description
            )
    }
}