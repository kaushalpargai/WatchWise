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
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.android.plugins.RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        
        val emptyMovies = emptyList<Media>()
        val emptyShows = emptyList<Media>()
        whenever(repository.getMovies()).thenReturn(Single.just(emptyMovies))
        whenever(repository.getTVShows()).thenReturn(Single.just(emptyShows))
        
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `initial state loads movies`() {
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
        val mockShows = listOf(
            Media(
                id = "2",
                title = "Show 1",
                posterUrl = "https://example.com/poster2.jpg",
                year = 2023,
                rating = 7.5f,
                mediaType = MediaType.TVShow
            )
        )
        whenever(repository.getMovies()).thenReturn(Single.just(mockMovies))
        whenever(repository.getTVShows()).thenReturn(Single.just(mockShows))
        
        viewModel.onRetry()

        val state = viewModel.uiState.value
        assert(state.selectedTab == HomeTab.MOVIES)
        assert(state.moviesList.size == 1)
        assert(state.tvShowsList.size == 1)
    }

    @Test
    fun `onTabSelected changes tab`() {
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
        val mockShows = listOf(
            Media(
                id = "2",
                title = "Show 1",
                posterUrl = "https://example.com/poster2.jpg",
                year = 2023,
                rating = 8.5f,
                mediaType = MediaType.TVShow
            )
        )
        whenever(repository.getMovies()).thenReturn(Single.just(mockMovies))
        whenever(repository.getTVShows()).thenReturn(Single.just(mockShows))
        
        viewModel.onRetry()
        viewModel.onTabSelected(HomeTab.TV_SHOWS)

        val state = viewModel.uiState.value
        assert(state.selectedTab == HomeTab.TV_SHOWS)
        assert(state.mediaList.size == 1)
        assert(state.mediaList[0].title == "Show 1")
    }

    @Test
    fun `loading state is set during data fetch`() {
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
        val mockShows = listOf(
            Media(
                id = "2",
                title = "Show 1",
                posterUrl = "https://example.com/poster2.jpg",
                year = 2023,
                rating = 7.5f,
                mediaType = MediaType.TVShow
            )
        )
        whenever(repository.getMovies()).thenReturn(Single.just(mockMovies))
        whenever(repository.getTVShows()).thenReturn(Single.just(mockShows))

        viewModel.onRetry()

        val state = viewModel.uiState.value
        assert(!state.isLoading)
        assert(state.moviesList.isNotEmpty())
        assert(state.tvShowsList.isNotEmpty())
    }

    @Test
    fun `error state is set on failure`() {
        val error = Exception("Network error")
        whenever(repository.getMovies()).thenReturn(Single.error(error))
        whenever(repository.getTVShows()).thenReturn(Single.error(error))

        viewModel.onRetry()

        val state = viewModel.uiState.value
        assert(!state.isLoading)
        assert(state.snackbarMessage == "Please check your network")
        assert(state.moviesList.isEmpty())
        assert(state.tvShowsList.isEmpty())
    }
}
