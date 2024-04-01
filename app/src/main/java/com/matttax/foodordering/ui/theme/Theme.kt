package com.matttax.foodordering.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
	primary = Purple700,
	primaryContainer = Color.Black,
	secondaryContainer = Color.White,
	onPrimary = Color.White,
	onSecondary = Color.LightGray,
	onTertiary = Color.Gray,
	error = Color.Red
)

private val LightColorScheme = lightColorScheme(
	primary = BrightRed,
	primaryContainer = Color.White,
	secondaryContainer = LightRed,
	onPrimary = Color.Black,
	onSecondary = BrightRed,
	onTertiary = Color.Gray,
	surfaceContainerLowest = SuperLightGray,
	error = Color.Red,
	onSurface = Color.White
)

@Composable
fun FoodOrderingTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.primary.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
		}
	}
	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}