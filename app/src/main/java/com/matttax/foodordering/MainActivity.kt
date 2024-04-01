package com.matttax.foodordering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.matttax.foodordering.domain.model.FoodItems
import com.matttax.foodordering.navigation.BottomNavigationBar
import com.matttax.foodordering.presentation.FoodListViewModel
import com.matttax.foodordering.presentation.state.LoadingSingleEvent
import com.matttax.foodordering.presentation.state.LoadingState
import com.matttax.foodordering.presentation.componenets.CategoriesPanel
import com.matttax.foodordering.presentation.componenets.ErrorMessage
import com.matttax.foodordering.presentation.componenets.FoodItem
import com.matttax.foodordering.presentation.componenets.ImagePager
import com.matttax.foodordering.presentation.componenets.ProgressBar
import com.matttax.foodordering.presentation.componenets.ScrollToTopButton
import com.matttax.foodordering.presentation.componenets.TopPanel
import com.matttax.foodordering.ui.theme.FoodOrderingTheme
import com.matttax.foodordering.ui.utils.AnimationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel by viewModels<FoodListViewModel>()
		setContent {
			FoodOrderingTheme {
				MainScreen(viewModel)
			}
		}
	}
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: FoodListViewModel) {
	val pagerState = rememberPagerState()
	val listState = rememberLazyListState()
	val snackbarHostState = remember { SnackbarHostState() }
	val foodState by viewModel.foodState.collectAsState()
	var bannerIsVisible by rememberSaveable {
		mutableStateOf(true)
	}
	LaunchedEffect(true) {
		viewModel.eventFlow.collectLatest {
			when (it) {
				is LoadingSingleEvent.NoConnection -> {
					snackbarHostState.showSnackbar("No internet")
				}
				is LoadingSingleEvent.CategoryUpdated -> {
					bannerIsVisible = it.categorySelected.not()
					if (bannerIsVisible)
						listState.scrollToItem(1)
				}
			}
		}
	}
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		containerColor = MaterialTheme.colorScheme.primaryContainer,
		topBar = { TopPanel() },
		snackbarHost = { SnackbarHost(snackbarHostState) },
		floatingActionButton = { ScrollToTopButton(listState) },
		bottomBar = { BottomNavigationBar() }
	) {
		LazyColumn(
			modifier = Modifier
				.padding(
					top = it.calculateTopPadding(),
					bottom = it.calculateBottomPadding()
				),
			state = listState
		) {
			item {
				AnimatedVisibility(
					visible = bannerIsVisible,
					enter = AnimationUtils.popUpEnter(),
					exit = AnimationUtils.popUpExit()
				) {
					ImagePager(
						modifier = Modifier
							.fillMaxWidth()
							.fillMaxHeight(0.25f),
						imageDrawableIds = listOf(
							R.drawable.banner_3,
							R.drawable.banner_2,
							R.drawable.banner_1
						),
						pagerState = pagerState
					)
				}
			}
			stickyHeader {
				CategoriesPanel(
					categories = viewModel.categoriesListState,
					selectedCategoryName = viewModel.selectedCategory,
					onSelect = viewModel::selectCategory
				)
			}
			when (val state = foodState) {
				is LoadingState.Result<FoodItems> -> items(state.list.size) {
					FoodItem(state.list[it])
				}
				is LoadingState.Loading -> item {
					ProgressBar(
						modifier = Modifier.fillMaxWidth(),
						size = 50.dp
					)
				}
				is LoadingState.Error -> item {
					ErrorMessage(
						message = state.message,
						onClickRetry = viewModel::refresh
					)
				}
			}
		}
	}
}