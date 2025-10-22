package com.example.watchwise.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.watchwise.data.model.MediaDetails
import com.example.watchwise.data.repository.MediaRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailsViewModel(
    private val repository: MediaRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mediaId: String = savedStateHandle.get<String>("mediaId") ?: ""

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val disposables = CompositeDisposable()

    init {
        loadMediaDetails()
    }

    fun onRetry() {
        loadMediaDetails()
    }

    private fun loadMediaDetails() {
        if (mediaId.isEmpty()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = "Invalid media ID"
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        repository.getMediaDetails(mediaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { details ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            mediaDetails = details,
                            error = null
                        )
                    }
                },
                { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "An unknown error occurred"
                        )
                    }
                }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

data class DetailsUiState(
    val isLoading: Boolean = false,
    val mediaDetails: MediaDetails? = null,
    val error: String? = null
)
