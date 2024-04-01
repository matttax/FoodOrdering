package com.matttax.foodordering.presentation.state

sealed interface LoadingState<out T> {
	data object Loading : LoadingState<Nothing>
	data class Error(val message: String) : LoadingState<Nothing>
	data class Result<T>(val list: T) : LoadingState<T>
}
