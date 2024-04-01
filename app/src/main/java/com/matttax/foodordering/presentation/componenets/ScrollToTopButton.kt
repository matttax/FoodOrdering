package com.matttax.foodordering.presentation.componenets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.matttax.foodordering.ui.utils.AnimationUtils
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopButton(
	listState: LazyListState
) {
	val scope = rememberCoroutineScope()
	val showScrollToTopButton by remember {
		derivedStateOf {
			listState.firstVisibleItemIndex >= 5
		}
	}
	AnimatedVisibility(
		visible = showScrollToTopButton,
		enter = AnimationUtils.scaleIn,
		exit = AnimationUtils.scaleOut
	) {
		FloatingActionButton(
			shape = CircleShape,
			containerColor = MaterialTheme.colorScheme.primary,
			contentColor = MaterialTheme.colorScheme.onSurface,
			onClick = {
				scope.launch {
					listState.scrollToItem(0)
				}
			}
		) {
			Icon(
				imageVector = Icons.Default.KeyboardArrowUp,
				contentDescription = null
			)
		}
	}
}
