package com.example.watchwise.ui.home

import androidx.lifecycle.ViewModel
import com.example.watchwise.data.model.Media
import com.example.watchwise.data.model.MediaType
import com.example.watchwise.data.repository.MediaRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val repository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val disposables = CompositeDisposable()

    init {
        loadMedia()
    }

    fun onTabSelected(tab: HomeTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onRetry() {
        loadMedia()
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    private fun loadMedia() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        Single.zip(
            repository.getMovies(),
            repository.getTVShows()
        ) { movies, tvShows ->
            Pair(movies, tvShows)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (movies, tvShows) ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            moviesList = movies,
                            tvShowsList = tvShows,
                            error = null,
                            snackbarMessage = null
                        )
                    }
                },
                { error ->
                    val isNetworkError = error.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
                            error.message?.contains("No address associated", ignoreCase = true) == true ||
                            error.message?.contains("network", ignoreCase = true) == true ||
                            error is java.net.UnknownHostException ||
                            error is java.net.SocketTimeoutException ||
                            error is java.io.IOException
                    
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = if (!isNetworkError) error.message ?: "An unknown error occurred" else null,
                            snackbarMessage = if (isNetworkError) "Please check your network" else null
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

data class HomeUiState(
    val isLoading: Boolean = false,
    val moviesList: List<Media> = emptyList(),
    val tvShowsList: List<Media> = emptyList(),
    val selectedTab: HomeTab = HomeTab.MOVIES,
    val error: String? = null,
    val snackbarMessage: String? = null
) {
    val mediaList: List<Media>
        get() = when (selectedTab) {
            HomeTab.MOVIES -> moviesList
            HomeTab.TV_SHOWS -> tvShowsList
        }
}

enum class HomeTab {
    MOVIES, TV_SHOWS
}
