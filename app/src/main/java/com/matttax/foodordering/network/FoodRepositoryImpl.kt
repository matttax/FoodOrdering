package com.matttax.foodordering.network

import android.util.Log
import com.matttax.foodordering.domain.FoodRepository
import com.matttax.foodordering.domain.model.Categories
import com.matttax.foodordering.domain.model.FoodItems
import com.matttax.foodordering.network.cache.CategoriesCacheStorage
import com.matttax.foodordering.network.cache.FoodCacheStorage
import com.matttax.foodordering.network.mappers.CategoriesMapper.toDomainCategories
import com.matttax.foodordering.network.mappers.FoodItemMapper.toDomainFoodItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
	private val foodApi: FoodApi,
	private val localFoodCache: FoodCacheStorage,
	private val localCategoriesCache: CategoriesCacheStorage
) : FoodRepository {

	@OptIn(ExperimentalCoroutinesApi::class)
	override suspend fun loadCategories(forceNetwork: Boolean): Flow<Result<Categories>> {
		if (forceNetwork) return getCategoriesFromApi()
		return localCategoriesCache.getAll()
			.flatMapLatest {
				if (it.list.isEmpty()) {
					getCategoriesFromApi()
				} else {
					flow { emit(Result.success(it.list.toDomainCategories())) }
				}
			}
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	override suspend fun getFoodItems(forceNetwork: Boolean): Flow<Result<FoodItems>> {
		Log.i("data", forceNetwork.toString())
		if (forceNetwork) return getFoodFromApi()
		return localFoodCache.getAll()
			.flatMapLatest {
				if (it.list.isEmpty()) {
					getFoodFromApi()
				} else {
					flow { emit(Result.success(it.list.toDomainFoodItems())) }
				}
			}
	}

	private fun getFoodFromApi(): Flow<Result<FoodItems>> {
		return resultFlow {
			foodApi.getFood().let {
				localFoodCache.updateFoodData(it)
				it.list.toDomainFoodItems()
			}
		}
	}

	private fun getCategoriesFromApi(): Flow<Result<Categories>> {
		return resultFlow {
			foodApi.getCategories().let {
				localCategoriesCache.updateCategories(it)
				it.list.toDomainCategories()
			}
		}
	}

	private inline fun <T> resultFlow(
		crossinline action: suspend () -> T
	): Flow<Result<T>> {
		return flow {
			try {
				emit(Result.success(action()))
			} catch (ex: Throwable) {
				emit(Result.failure(ex))
			}
		}
	}
}
