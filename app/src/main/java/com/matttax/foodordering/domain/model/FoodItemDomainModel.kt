package com.matttax.foodordering.domain.model

data class FoodItemDomainModel(
	val id: Long,
	val name: String,
	val category: CategoryDomainModel,
	val area: String,
	val description: String,
	val ingredients: List<String>,
	val thumbnailUri: String
)

typealias FoodItems = List<FoodItemDomainModel>
