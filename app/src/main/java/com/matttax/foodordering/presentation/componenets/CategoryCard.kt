package com.matttax.foodordering.presentation.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CategoryCard(
	categoryName: String,
	isSelected: Boolean,
	onClick: (String) -> Unit
) {
	if (isSelected) {
		Card(
			modifier = Modifier
				.clickable { onClick(categoryName) }
				.padding(7.dp),
			shape = MaterialTheme.shapes.small,
			elevation = CardDefaults.cardElevation(5.dp)
		) {
			Text(
				modifier = Modifier
					.background(color = MaterialTheme.colorScheme.secondaryContainer)
					.padding(5.dp),
				text = categoryName,
				style = MaterialTheme.typography.labelLarge,
				color = MaterialTheme.colorScheme.onSecondary
			)
		}
	} else {
		Card(
			modifier = Modifier
				.clickable { onClick(categoryName) }
				.padding(7.dp),
			shape = MaterialTheme.shapes.small,
			elevation = CardDefaults.cardElevation(5.dp),
			colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
		) {
			Text(
				modifier = Modifier.padding(5.dp),
				text = categoryName,
				style = MaterialTheme.typography.labelSmall,
				color = MaterialTheme.colorScheme.onPrimary
			)
		}
	}
}
