package com.matttax.foodordering.network.mappers

import com.matttax.foodordering.domain.model.CategoryDomainModel
import com.matttax.foodordering.domain.model.FoodItemDomainModel
import com.matttax.foodordering.domain.model.FoodItems
import com.matttax.foodordering.network.model.FoodItem

object FoodItemMapper {

	fun List<FoodItem>.toDomainFoodItems(): FoodItems {
		return map { it.toDomainModel() }
	}

	fun FoodItem.toDomainModel(): FoodItemDomainModel {
		return FoodItemDomainModel(
			id = id,
			name = name,
			area = area,
			description = instructions,
			thumbnailUri = thumbnailUri,
			category = CategoryDomainModel(category),
			ingredients = getIngredients()
		)
	}

	fun FoodItem.getIngredients(): List<String> {
		return listOfNotNull(
			ingredient1,
			ingredient2,
			ingredient3,
			ingredient4,
			ingredient5,
			ingredient6,
			ingredient7,
			ingredient8,
			ingredient9,
			ingredient10,
			ingredient11,
			ingredient12,
			ingredient13,
			ingredient14,
			ingredient15,
			ingredient16,
			ingredient17,
			ingredient18,
			ingredient19,
			ingredient20
		).filterNot { it.isBlank() }
	}
}