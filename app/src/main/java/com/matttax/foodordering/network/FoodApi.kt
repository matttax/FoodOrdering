package com.matttax.foodordering.network

import com.matttax.foodordering.network.model.CategoriesResponse
import com.matttax.foodordering.network.model.FoodResponse
import retrofit2.http.GET

interface FoodApi {

	@GET("/api/json/v1/1/search.php?s")
	suspend fun getFood() : FoodResponse

	@GET("/api/json/v1/1/categories.php")
	suspend fun getCategories() : CategoriesResponse

	companion object {
		const val BASE_URL = "https://themealdb.com"
	}
}
