package com.matttax.foodordering.presentation.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matttax.foodordering.domain.model.Categories
import com.matttax.foodordering.presentation.state.LoadingState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoriesPanel(
	categories: StateFlow<LoadingState<Categories>>,
	selectedCategoryName: StateFlow<String?>,
	onSelect: (String) -> Unit
) {
	val categoriesState by categories.collectAsState()
	val selectedCategory by selectedCategoryName.collectAsState()
	when (val state = categoriesState) {
		is LoadingState.Result<Categories> -> {
			LazyRow(
				modifier = Modifier
					.background(
						color = MaterialTheme.colorScheme.primaryContainer
					).padding(
						vertical = 10.dp,
						horizontal = 5.dp
					)
			) {
				with(state.list) {
					items(size) {
						CategoryCard(
							categoryName = get(it).name,
							isSelected = get(it).name == selectedCategory,
							onClick = onSelect
						)
					}
				}
			}
		}
		else -> {}
	}
}
