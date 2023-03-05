package com.example.voicenotes.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<S, E>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect.asSharedFlow()

    protected fun setState(reducer: S.() -> S) {
        _state.value = reducer(_state.value)
    }

    protected fun postEffect(effect: E) {
        viewModelScope.launch {
            _effect.subscriptionCount.first { it > 0 }
            _effect.emit(effect)
        }
    }
}