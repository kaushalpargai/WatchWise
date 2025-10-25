package com.example.watchwise.data.repository

import com.example.watchwise.data.api.WatchModeApiService
import com.example.watchwise.data.model.MediaItem
import com.example.watchwise.data.model.MediaListResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MediaRepositoryTest {

    @Mock
    private lateinit var apiService: WatchModeApiService

    private lateinit var repository: MediaRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        io.reactivex.rxjava3.plugins.RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off")
        
        repository = MediaRepositoryImpl(apiService)
    }
    
    @After
    fun tearDown() {
        io.reactivex.rxjava3.plugins.RxJavaPlugins.reset()
    }

    @Test
    fun `getMovies returns list of movies`() {
        val mockMovies = listOf(
            MediaItem(
                id = "1",
                title = "Movie 1",
                posterUrl = null,
                year = 2023,
                rating = 8.5f,
                mediaType = "movie"
            ),
            MediaItem(
                id = "2",
                title = "Movie 2",
                posterUrl = null,
                year = 2024,
                rating = 7.8f,
                mediaType = "movie"
            )
        )
        val mockResponse = MediaListResponse(
            title = "Movies",
            titles = mockMovies,
            page = 1,
            totalPages = 1
        )
        
        val mockDetails1 = com.example.watchwise.data.model.MediaDetailsResponse(
            id = "1",
            title = "Movie 1",
            overview = "Overview 1",
            posterUrl = "https://example.com/poster1.jpg",
            year = 2023,
            rating = 8.5f,
            releaseDate = "2023-01-01",
            genres = listOf("Action"),
            mediaType = "movie"
        )
        
        val mockDetails2 = com.example.watchwise.data.model.MediaDetailsResponse(
            id = "2",
            title = "Movie 2",
            overview = "Overview 2",
            posterUrl = "https://example.com/poster2.jpg",
            year = 2024,
            rating = 7.8f,
            releaseDate = "2024-01-01",
            genres = listOf("Drama"),
            mediaType = "movie"
        )

        whenever(apiService.getMediaList("movie", 1)).thenReturn(Single.just(mockResponse))
        whenever(apiService.getMediaDetails("1")).thenReturn(Single.just(mockDetails1))
        whenever(apiService.getMediaDetails("2")).thenReturn(Single.just(mockDetails2))

        val testObserver = repository.getMovies(1).test()

        testObserver.assertNoErrors()
        testObserver.assertValue { mediaList ->
            mediaList.size == 2 &&
                    mediaList[0].title == "Movie 1" &&
                    mediaList[1].title == "Movie 2" &&
                    mediaList[0].posterUrl == "https://example.com/poster1.jpg" &&
                    mediaList[1].posterUrl == "https://example.com/poster2.jpg"
        }
    }

    @Test
    fun `getTVShows returns list of TV shows`() {
        val mockShows = listOf(
            MediaItem(
                id = "1",
                title = "Show 1",
                posterUrl = null,
                year = 2023,
                rating = 8.5f,
                mediaType = "tv_series"
            )
        )
        val mockResponse = MediaListResponse(
            title = "TV Shows",
            titles = mockShows,
            page = 1,
            totalPages = 1
        )
        
        val mockDetails = com.example.watchwise.data.model.MediaDetailsResponse(
            id = "1",
            title = "Show 1",
            overview = "Overview",
            posterUrl = "https://example.com/poster1.jpg",
            year = 2023,
            rating = 8.5f,
            releaseDate = "2023-01-01",
            genres = listOf("Drama"),
            mediaType = "tv_series"
        )

        whenever(apiService.getMediaList("tv_series", 1)).thenReturn(Single.just(mockResponse))
        whenever(apiService.getMediaDetails("1")).thenReturn(Single.just(mockDetails))

        val testObserver = repository.getTVShows(1).test()

        testObserver.assertNoErrors()
        testObserver.assertValue { mediaList ->
            mediaList.size == 1 && 
            mediaList[0].title == "Show 1" &&
            mediaList[0].posterUrl == "https://example.com/poster1.jpg"
        }
    }

    @Test
    fun `getMediaDetails returns media details`() {
        // Arrange
        val mockDetails = com.example.watchwise.data.model.MediaDetailsResponse(
            id = "1",
            title = "Movie Title",
            overview = "Movie overview",
            posterUrl = "https://example.com/poster.jpg",
            year = 2023,
            rating = 8.5f,
            releaseDate = "2023-01-01",
            genres = listOf("Action", "Drama"),
            mediaType = "movie"
        )

        whenever(apiService.getMediaDetails("1")).thenReturn(Single.just(mockDetails))

        // Act
        val testObserver = repository.getMediaDetails("1").test()

        // Assert
        testObserver.assertNoErrors()
        testObserver.assertValue { details ->
            details.title == "Movie Title" &&
                    details.overview == "Movie overview" &&
                    details.genres.size == 2
        }
    }

    @Test
    fun `getMovies handles error`() {
        // Arrange
        val error = Exception("Network error")
        whenever(apiService.getMediaList("movie", 1)).thenReturn(Single.error(error))

        // Act
        val testObserver = repository.getMovies(1).test()

        // Assert
        testObserver.assertError(Exception::class.java)
    }
}
