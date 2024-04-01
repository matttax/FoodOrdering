package com.matttax.foodordering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.matttax.foodordering.presentation.FoodListScreen
import com.matttax.foodordering.presentation.FoodListViewModel
import com.matttax.foodordering.ui.theme.FoodOrderingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel by viewModels<FoodListViewModel>()
		setContent {
			FoodOrderingTheme {
				FoodListScreen(viewModel)
			}
		}
	}
}
