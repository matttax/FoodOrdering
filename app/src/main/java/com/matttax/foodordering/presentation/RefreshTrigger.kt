package com.matttax.foodordering.presentation

import kotlinx.coroutines.flow.MutableSharedFlow

object RefreshTrigger {
	operator fun invoke(): MutableSharedFlow<Boolean> {
		return MutableSharedFlow(replay = 1)
	}
}

fun MutableSharedFlow<Boolean>.pull(forceNetwork: Boolean = false) {
	tryEmit(forceNetwork)
}
