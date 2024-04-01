package com.matttax.foodordering.presentation.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.matttax.foodordering.R
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImagePager(
	imageDrawableIds: List<Int>,
	pagerState: PagerState,
	modifier: Modifier = Modifier
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		HorizontalPager(
			modifier = modifier,
			count = imageDrawableIds.size,
			state = pagerState,
			contentPadding = PaddingValues(horizontal = 16.dp)
		) { page ->
			Box(
				modifier = Modifier
					.graphicsLayer {
						val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
						lerp(
							start = 0.85f,
							stop = 1f,
							fraction = 1f - pageOffset.coerceIn(0f, 1f)
						).also { scale ->
							scaleX = scale
							scaleY = scale
						}
						alpha = lerp(
							start = 0.5f,
							stop = 1f,
							fraction = 1f - pageOffset.coerceIn(0f, 1f)
						)
					}.background(color = MaterialTheme.colorScheme.primaryContainer)
			) {
				Image(
					modifier = Modifier.fillMaxSize(),
					painter = painterResource(imageDrawableIds[page]),
					contentDescription = null,
					contentScale = ContentScale.Fit
				)
			}
		}
		HorizontalPagerIndicator(
			modifier = Modifier.padding(15.dp),
			pagerState = pagerState,
			activeColor = MaterialTheme.colorScheme.onSecondary,
			inactiveColor = MaterialTheme.colorScheme.onPrimary
		)
	}
}
