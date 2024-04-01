package com.matttax.foodordering.presentation.state

sealed interface LoadingSingleEvent {
	data object NoConnection : LoadingSingleEvent
	data class CategoryUpdated(val categorySelected: Boolean) : LoadingSingleEvent
}
