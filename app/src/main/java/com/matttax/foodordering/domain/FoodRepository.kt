package com.matttax.foodordering.domain

import com.matttax.foodordering.domain.model.Categories
import com.matttax.foodordering.domain.model.FoodItems
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

	suspend fun loadCategories(forceNetwork: Boolean = false): Flow<Result<Categories>>

	suspend fun getFoodItems(forceNetwork: Boolean = false): Flow<Result<FoodItems>>
}
