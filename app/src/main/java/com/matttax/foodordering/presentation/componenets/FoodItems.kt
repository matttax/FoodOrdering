package com.matttax.foodordering.presentation.componenets

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.matttax.foodordering.domain.model.FoodItemDomainModel

@Composable
fun FoodItem(product: FoodItemDomainModel) {
	val configuration = LocalConfiguration.current
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 5.dp),
		shape = MaterialTheme.shapes.small,
		elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
		colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
	) {
		Row(
			modifier = Modifier.padding(5.dp),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			ItemThumbnail(
				modifier = Modifier.weight(
					when(configuration.orientation){
						Configuration.ORIENTATION_PORTRAIT -> 0.4f
						else -> 0.25f
					}
				),
				uri = product.thumbnailUri
			)
			Spacer(
				modifier = Modifier.weight(0.05f)
			)
			Column(
				modifier = Modifier.weight(
					when(configuration.orientation){
						Configuration.ORIENTATION_PORTRAIT -> 0.55f
						else -> 0.7f
					}
				)
			) {
				Text(
					text = product.name,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onPrimary
				)
				Text(
					text = product.ingredients.joinToString(", "),
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onTertiary
				)
				Spacer(
					modifier = Modifier.height(10.dp)
				)
				Surface(
					modifier = Modifier
						.align(Alignment.End)
						.padding(15.dp),
					border = BorderStroke(
						width = 1.dp,
						color = MaterialTheme.colorScheme.primary
					),
					shape = MaterialTheme.shapes.small,
					contentColor = MaterialTheme.colorScheme.primaryContainer,
					color = MaterialTheme.colorScheme.primaryContainer
				) {
					Text(
						modifier = Modifier.padding(10.dp),
						text = "From ${product.id % 100}",
						style = MaterialTheme.typography.labelSmall,
						color = MaterialTheme.colorScheme.onSecondary
					)
				}
			}
		}
	}
}

@Composable
fun ItemThumbnail(
	modifier: Modifier,
	uri: String
) {
	Card(
		modifier = modifier,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	) {
		AsyncImage(
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.clip(MaterialTheme.shapes.medium),
			model = uri,
			contentDescription = null,
			contentScale = ContentScale.FillBounds
		)
	}
}
