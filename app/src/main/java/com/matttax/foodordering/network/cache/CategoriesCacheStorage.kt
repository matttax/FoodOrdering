package com.matttax.foodordering.network.cache

import android.content.Context
import androidx.datastore.dataStore
import com.matttax.foodordering.network.model.CategoriesResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesCacheStorage @Inject constructor(
	@ApplicationContext private val context: Context
) {

	private val Context.dataStore by dataStore(
		fileName = "categories.json",
		serializer = CategoriesSerializer
	)

	fun getAll(): Flow<CategoriesResponse> {
		return context.dataStore.data
	}

	suspend fun updateCategories(categoriesResponse: CategoriesResponse) {
		context.dataStore.updateData { categoriesResponse }
	}
}
