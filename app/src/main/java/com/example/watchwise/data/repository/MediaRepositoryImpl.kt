package com.example.watchwise.data.repository

import android.util.Log
import com.example.watchwise.data.api.WatchModeApiService
import com.example.watchwise.data.model.Media
import com.example.watchwise.data.model.MediaDetails
import com.example.watchwise.data.model.MediaType
import io.reactivex.rxjava3.core.Single

class MediaRepositoryImpl(
    private val apiService: WatchModeApiService
) : MediaRepository {

    override fun getMovies(page: Int): Single<List<Media>> {
        return apiService.getMediaList("movie", page)
            .map { response ->
                Log.d("MediaRepository", "Movies response: ${response.titles.size} items")
                response.titles.mapIndexed { index, mediaItem ->
                    if (index < 3) {
                        Log.d("MediaRepository", "Movie $index - ID: ${mediaItem.id}, Title: ${mediaItem.title}, Poster: ${mediaItem.posterUrl}")
                    }
                    Media(
                        id = mediaItem.id,
                        title = mediaItem.title,
                        posterUrl = mediaItem.posterUrl,
                        year = mediaItem.year,
                        rating = mediaItem.rating,
                        mediaType = MediaType.Movie
                    )
                }
            }
    }

    override fun getTVShows(page: Int): Single<List<Media>> {
        return apiService.getMediaList("tv_series", page)
            .map { response ->
                Log.d("MediaRepository", "TV Shows response: ${response.titles.size} items")
                response.titles.mapIndexed { index, mediaItem ->
                    if (index < 3) {
                        Log.d("MediaRepository", "TV Show $index - ID: ${mediaItem.id}, Title: ${mediaItem.title}, Poster: ${mediaItem.posterUrl}")
                    }
                    Media(
                        id = mediaItem.id,
                        title = mediaItem.title,
                        posterUrl = mediaItem.posterUrl,
                        year = mediaItem.year,
                        rating = mediaItem.rating,
                        mediaType = MediaType.TVShow
                    )
                }
            }
    }

    override fun getMediaDetails(mediaId: String): Single<MediaDetails> {
        return apiService.getMediaDetails(mediaId)
            .map { details ->
                Log.d("MediaRepository", "Details for $mediaId - Title: ${details.title}, Poster: ${details.posterUrl}, MediaType: ${details.mediaType}")
                MediaDetails(
                    id = details.id,
                    title = details.title,
                    overview = details.overview ?: "No overview available",
                    posterUrl = details.posterUrl,
                    year = details.year,
                    rating = details.rating,
                    releaseDate = details.releaseDate,
                    genres = details.genres,
                    mediaType = MediaType.fromString(details.mediaType)
                )
            }
    }

    override fun searchMedia(query: String, type: MediaType?): Single<List<Media>> {
        // This is a placeholder. The actual implementation would use a search endpoint if available
        // For now, we'll return an empty list
        return Single.just(emptyList())
    }
}
