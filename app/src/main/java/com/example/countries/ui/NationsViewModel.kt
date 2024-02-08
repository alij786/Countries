package com.example.countries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countries.usecases.GetNationsInteractor
import com.example.countries.entities.Nations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NationsViewModel(getNations: GetNationsInteractor) : ViewModel() {
    data class UIState(
        val loading: Boolean = false,
        val nations: Nations = emptyList()
    )

    sealed class Effect {
        data object CompleteMessage : Effect()
        data class ErrorMessage(val message: String) : Effect()
    }

    private val _state: MutableStateFlow<UIState> by lazy { MutableStateFlow(UIState()) }
    val state = _state.asStateFlow()

    private fun setState(reduce: UIState.() -> UIState) {
        _state.value = state.value.reduce()
    }

    private val _effects: Channel<Effect> by lazy { Channel() }
    val effects = _effects.receiveAsFlow()

    private fun setEffect(effect: () -> Effect) {
        _effects.trySend(effect())
    }

    init {
        viewModelScope.launch {
            setState { copy(loading = true) }
            withContext(Dispatchers.IO) {
                getNations().run {
                    withContext(Dispatchers.Main) {
                        onSuccess { nations ->
                            setState { copy(loading = false, nations = nations) }
                            setEffect { Effect.CompleteMessage }
                        }
                        onFailure { e ->
                            setState { copy(loading = false) }
                            setEffect {
                                Effect.ErrorMessage(e.message ?: "Error loading nations.")
                            }
                        }
                    }
                }
            }
        }
    }
}
