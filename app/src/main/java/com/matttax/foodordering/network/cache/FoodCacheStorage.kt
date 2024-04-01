package com.matttax.foodordering.network.cache

import android.content.Context
import androidx.datastore.dataStore
import com.matttax.foodordering.network.model.FoodResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodCacheStorage @Inject constructor(
	@ApplicationContext private val context: Context
) {

	private val Context.dataStore by dataStore(
		fileName = "food.json",
		serializer = FoodItemsSerializer
	)

	fun getAll(): Flow<FoodResponse> {
		return context.dataStore.data
	}

	suspend fun updateFoodData(foodResponse: FoodResponse) {
		context.dataStore.updateData { foodResponse }
	}
}
