package com.example.surosowancyber.implement.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
	@field:SerializedName("genres")
	val genres: List<GenresItem>
)

data class GenresItem(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
