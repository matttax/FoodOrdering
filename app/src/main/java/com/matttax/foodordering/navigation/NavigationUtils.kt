package com.matttax.foodordering.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart

object NavigationUtils {
	val items = listOf(
		BottomNavigationItem(
			title = "Menu",
			selectedIcon = Icons.Filled.Menu,
			unselectedIcon = Icons.Outlined.Menu
		),
		BottomNavigationItem(
			title = "Profile",
			selectedIcon = Icons.Filled.AccountCircle,
			unselectedIcon = Icons.Outlined.AccountCircle
		),
		BottomNavigationItem(
			title = "Cart",
			selectedIcon = Icons.Filled.ShoppingCart,
			unselectedIcon = Icons.Outlined.ShoppingCart
		)
	)
}
