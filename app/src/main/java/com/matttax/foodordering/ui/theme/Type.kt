package com.matttax.foodordering.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
	labelLarge = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
	),
	labelSmall = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Thin,
		fontSize = 16.sp,
	)
)