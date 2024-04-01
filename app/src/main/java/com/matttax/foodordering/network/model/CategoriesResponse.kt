package com.matttax.foodordering.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
	@SerializedName("categories")
	val list: List<Category>
)
