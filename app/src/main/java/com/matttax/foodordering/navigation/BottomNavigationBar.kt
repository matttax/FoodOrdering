package com.matttax.foodordering.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun BottomNavigationBar() {
	var selectedItemIndex by rememberSaveable {
		mutableIntStateOf(0)
	}
	NavigationBar(
		containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
	) {
		NavigationUtils.items.forEachIndexed { index, item ->
			NavigationBarItem(
				selected = selectedItemIndex == index,
				onClick = { selectedItemIndex = index },
				label = {
					Text(
						text = item.title,
						color = if (index == selectedItemIndex) {
							MaterialTheme.colorScheme.primary
						} else MaterialTheme.colorScheme.onPrimary
					)
				},
				alwaysShowLabel = true,
				icon = {
					Icon(
						imageVector = if (index == selectedItemIndex) {
							item.selectedIcon
						} else item.unselectedIcon,
						tint = if (index == selectedItemIndex) {
							MaterialTheme.colorScheme.primary
						} else MaterialTheme.colorScheme.onPrimary,
						contentDescription = item.title
					)
				},
				colors = NavigationBarItemDefaults.colors(
					indicatorColor = MaterialTheme.colorScheme.surfaceContainerLowest
				)
			)
		}
	}
}