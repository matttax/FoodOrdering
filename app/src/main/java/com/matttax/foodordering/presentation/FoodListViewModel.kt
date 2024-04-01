package com.matttax.foodordering.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matttax.foodordering.connectivity.ConnectionState
import com.matttax.foodordering.connectivity.NetworkConnectivityProvider
import com.matttax.foodordering.domain.FoodRepository
import com.matttax.foodordering.domain.model.Categories
import com.matttax.foodordering.domain.model.FoodItemDomainModel
import com.matttax.foodordering.domain.model.FoodItems
import com.matttax.foodordering.presentation.state.LoadingSingleEvent
import com.matttax.foodordering.presentation.state.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
	private val foodRepository: FoodRepository,
	private val networkConnectivityProvider: NetworkConnectivityProvider
) : ViewModel() {

	private val _foodListState = MutableStateFlow<LoadingState<FoodItems>>(value = LoadingState.Loading)
	val foodState = _foodListState.asStateFlow()

	private val _categoriesListState = MutableStateFlow<LoadingState<Categories>>(value = LoadingState.Loading)
	val categoriesListState = _categoriesListState.asStateFlow()

	private val _selectedCategory = MutableStateFlow<String?>(null)
	val selectedCategory = _selectedCategory.asStateFlow()

	private val loadingSingleEventChanel = Channel<LoadingSingleEvent>()
	val eventFlow = loadingSingleEventChanel.receiveAsFlow()

	private val refreshTrigger = RefreshTrigger()
	private val networkState = networkConnectivityProvider.networkStatus
		.stateIn(viewModelScope, SharingStarted.Eagerly, ConnectionState.UNAVAILABLE)
	private var fullList = listOf<FoodItemDomainModel>()
	private var isUpToDate = false

	init {
		observeCategories()
		observeFoodList()
		observeNetwork()
		observeFilter()
		refreshTrigger.pull(
			forceNetwork = networkConnectivityProvider.hasInternet()
		)
		if (networkConnectivityProvider.hasInternet().not()) {
			loadingSingleEventChanel.trySend(LoadingSingleEvent.NoConnection)
		}
	}

	fun selectCategory(categoryName: String) {
		_selectedCategory.update {  current ->
			if (current == categoryName)
				null
			else categoryName
		}
	}

	fun refresh() {
		refreshTrigger.pull(forceNetwork = true)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	private fun observeCategories() {
		refreshTrigger
			.flatMapLatest {
				_categoriesListState.value = LoadingState.Loading
				foodRepository.loadCategories(it)
			}
			.flowOn(Dispatchers.IO)
			.onEach {
				_categoriesListState.value = it.toLoadingState()
			}.launchIn(viewModelScope)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	private fun observeFoodList() {
		refreshTrigger
			.flatMapLatest {
				_foodListState.value = LoadingState.Loading
				foodRepository.getFoodItems(it)
			}
			.flowOn(Dispatchers.IO)
			.onEach {
				_foodListState.value = it.toLoadingState()
					.also { state ->
						if (state is LoadingState.Result) {
							fullList = state.list
						}
					}
			}.launchIn(viewModelScope)
	}

	private fun observeFilter() {
		_selectedCategory.onEach { category ->
			(_foodListState.value as? LoadingState.Result)?.let {
				Log.i("data_cat", category.toString())
				_foodListState.update {
					if (category == null) {
						LoadingState.Result(fullList)
					} else {
						LoadingState.Result(
							fullList.filter { item ->
								item.category.name == category
							}
						)
					}
				}
				loadingSingleEventChanel.send(
					LoadingSingleEvent.CategoryUpdated(
						categorySelected = category != null
					)
				)
			}
		}.launchIn(viewModelScope)
	}

	private fun observeNetwork() {
		networkState
			.filter { it == ConnectionState.AVAILABLE }
			.onEach {
				if (!isUpToDate) {
					refreshTrigger.pull(forceNetwork = true)
					isUpToDate = true
				}
			}.launchIn(viewModelScope)
	}

	private fun <T : Any> Result<T>.toLoadingState(): LoadingState<T> {
		return when {
			isSuccess -> LoadingState.Result(getOrThrow())
			isFailure -> LoadingState.Error(exceptionOrNull()?.message ?: UNKNOWN_ERROR_MESSAGE)
			else -> LoadingState.Error(UNKNOWN_ERROR_MESSAGE)
		}
	}

	companion object {
		const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
	}

}
