package com.matttax.foodordering.presentation.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.matttax.foodordering.R

@Composable
fun TopPanel() {
	Row(
		modifier = Modifier
			.background(color = MaterialTheme.colorScheme.primaryContainer)
			.fillMaxWidth()
			.padding(
				horizontal = 7.dp,
				vertical = 15.dp
			),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Row(
			modifier = Modifier.clickable {  }
		) {
			Text("Moscow")
			Icon(
				painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
				contentDescription = null
			)
		}
		Icon(
			modifier = Modifier.clickable {  },
			painter = painterResource(id = R.drawable.ic_qr_code),
			contentDescription = null
		)
	}
}
