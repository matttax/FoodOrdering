package com.matttax.foodordering.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Category(

	@SerializedName("idCategory")
	val id: Int,

	@SerializedName("strCategory")
	val name: String,

	@SerializedName("strCategoryDescription")
	val description: String,

	@SerializedName("strCategoryThumb")
	val thumbnailUri: String
)
