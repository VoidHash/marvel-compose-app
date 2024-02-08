package com.andrepassos.marvelheroes.model

import com.google.gson.annotations.SerializedName

data class CharacterApiResponse(

	@field:SerializedName("copyright")
	val copyright: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val characterData: CharacterData? = null,

	@field:SerializedName("attributionHTML")
	val attributionHTML: String? = null,

	@field:SerializedName("attributionText")
	val attributionText: String? = null,

	@field:SerializedName("etag")
	val etag: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CharacterData(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<CharacterResult?>? = null
)

data class CharacterResult(

	@field:SerializedName("thumbnail")
	val thumbnail: CharacterThumbnail? = null,

	@field:SerializedName("urls")
	val urls: List<CharacterResultUrl?>? = null,

	@field:SerializedName("stories")
	val stories: CharacterStories? = null,

	@field:SerializedName("series")
	val series: CharacterSeries? = null,

	@field:SerializedName("comics")
	val comics: CharacterComics? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null,

	@field:SerializedName("events")
	val events: CharacterEvents? = null
)

data class CharacterThumbnail(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("extension")
	val extension: String? = null
)

data class CharacterResultUrl(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class CharacterStories(

	@field:SerializedName("collectionURI")
	val collectionURI: String? = null,

	@field:SerializedName("available")
	val available: Int? = null,

	@field:SerializedName("returned")
	val returned: Int? = null,

	@field:SerializedName("items")
	val items: List<CharacterItem?>? = null
)

data class CharacterSeries(

	@field:SerializedName("collectionURI")
	val collectionURI: String? = null,

	@field:SerializedName("available")
	val available: Int? = null,

	@field:SerializedName("returned")
	val returned: Int? = null,

	@field:SerializedName("items")
	val items: List<CharacterItem?>? = null
)

data class CharacterComics(

	@field:SerializedName("collectionURI")
	val collectionURI: String? = null,

	@field:SerializedName("available")
	val available: Int? = null,

	@field:SerializedName("returned")
	val returned: Int? = null,

	@field:SerializedName("items")
	val items: List<CharacterItem?>? = null
)

data class CharacterEvents(

	@field:SerializedName("collectionURI")
	val collectionURI: String? = null,

	@field:SerializedName("available")
	val available: Int? = null,

	@field:SerializedName("returned")
	val returned: Int? = null,

	@field:SerializedName("items")
	val items: List<CharacterItem?>? = null
)


data class CharacterItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
