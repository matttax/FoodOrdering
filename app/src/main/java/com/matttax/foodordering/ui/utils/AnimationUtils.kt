package com.matttax.foodordering.ui.utils

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically

object AnimationUtils {
	private const val DEFAULT_SLIDE_DURATION = 500
	private const val DEFAULT_FADE_DURATION = 350
	private const val DEFAULT_SCALE_DURATION = 400

	val scaleIn = scaleIn(
		animationSpec = tween(DEFAULT_SCALE_DURATION)
	)

	val scaleOut = scaleOut(
		animationSpec = tween(DEFAULT_SCALE_DURATION)
	)

	fun popUpEnter() = fadeIn(
		animationSpec = tween(
			durationMillis = DEFAULT_FADE_DURATION,
			easing = LinearOutSlowInEasing
		)
	)

	fun popUpExit() = shrinkVertically(
		animationSpec = tween(
			delayMillis = DEFAULT_SLIDE_DURATION
		)
	)
}
