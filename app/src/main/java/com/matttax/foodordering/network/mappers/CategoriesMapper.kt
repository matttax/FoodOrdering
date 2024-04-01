package com.matttax.foodordering.network.mappers

import com.matttax.foodordering.domain.model.Categories
import com.matttax.foodordering.domain.model.CategoryDomainModel
import com.matttax.foodordering.network.model.Category

object CategoriesMapper {

	fun List<Category>.toDomainCategories(): Categories {
		return map { it.toDomainModel() }
	}

	fun Category.toDomainModel(): CategoryDomainModel {
		return CategoryDomainModel(name)
	}
}
