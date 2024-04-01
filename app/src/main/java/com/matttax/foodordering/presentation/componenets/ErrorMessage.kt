package com.matttax.foodordering.presentation.componenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
	message: String,
	modifier: Modifier = Modifier,
	spaceBetween: Dp = 25.dp,
	onClickRetry: () -> Unit
) {
	Column(
		modifier = modifier.padding(10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(
			text = message,
			color = MaterialTheme.colorScheme.error,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center,
			maxLines = 2
		)
		Spacer(modifier = Modifier.height(spaceBetween))
		OutlinedButton(
			onClick = onClickRetry
		) {
			Text(
				text = "Retry",
				style = MaterialTheme.typography.labelLarge
			)
		}
	}
}
