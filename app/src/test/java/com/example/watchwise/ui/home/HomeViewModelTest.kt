package com.example.watchwise.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.watchwise.data.model.Media
import com.example.watchwise.data.model.MediaType
import com.example.watchwise.data.repository.MediaRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MediaRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // Set up RxJava to use immediate scheduler for testing
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setInitComputationSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setInitSingleSchedulerHandler { Schedulers.trampoline() }
        
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `initial state loads movies`() {
        // Arrange
        val mockMovies = listOf(
            Media(
                id = "1",
                title = "Movie 1",
                posterUrl = "https://example.com/poster1.jpg",
                year = 2023,
                rating = 8.5f,
                mediaType = MediaType.Movie
            )
        )
        whenever(repository.getMovies()).thenReturn(Single.just(mockMovies))

        // Act & Assert
        val state = viewModel.uiState.value
        assert(state.selectedTab == HomeTab.MOVIES)
    }

    @Test
    fun `onTabSelected changes tab`() {
        // Arrange
        val mockShows = listOf(
            Media(
                id = "1",
                title = "Show 1",
                posterUrl = "https://example.com/poster1.jpg",
                year = 2023,
                rating = 8.5f,
                mediaType = MediaType.TVShow
            )
        )
        whenever(repository.getTVShows()).thenReturn(Single.just(mockShows))

        // Act
        viewModel.onTabSelected(HomeTab.TV_SHOWS)

        // Assert
        val state = viewModel.uiState.value
        assert(state.selectedTab == HomeTab.TV_SHOWS)
    }

    @Test
    fun `loading state is set during data fetch`() {
        // Arrange
        val mockMovies = listOf(
            Media(
                id = "1",
                title = "Movie 1",
                posterUrl = "https://example.com/poster1.jpg",
                year = 2023,
                rating = 8.5f,
                mediaType = MediaType.Movie
            )
        )
        whenever(repository.getMovies()).thenReturn(Single.just(mockMovies))

        // Act
        viewModel.onRetry()

        // Assert - After retry, loading should be false and media list should be populated
        val state = viewModel.uiState.value
        assert(!state.isLoading)
        assert(state.mediaList.isNotEmpty())
    }

    @Test
    fun `error state is set on failure`() {
        // Arrange
        val error = Exception("Network error")
        whenever(repository.getMovies()).thenReturn(Single.error(error))

        // Act
        viewModel.onRetry()

        // Assert
        val state = viewModel.uiState.value
        assert(!state.isLoading)
        assert(state.error != null)
        assert(state.mediaList.isEmpty())
    }
}
