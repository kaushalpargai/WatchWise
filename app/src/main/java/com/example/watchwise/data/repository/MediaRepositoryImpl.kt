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
        Log.d("MediaRepository", "Fetching movies - page: $page")
        return apiService.getMediaList("movie", page)
            .doOnSubscribe { Log.d("MediaRepository", "Starting movies API call") }
            .doOnError { error -> Log.e("MediaRepository", "Error fetching movies: ${error.message}", error) }
            .flatMap { response ->
                Log.d("MediaRepository", "Movies response: ${response.titles.size} items")
                
                val detailsSingles = response.titles.map { mediaItem ->
                    apiService.getMediaDetails(mediaItem.id)
                        .map { details ->
                            Media(
                                id = mediaItem.id,
                                title = mediaItem.title,
                                posterUrl = details.posterUrl,
                                year = mediaItem.year,
                                rating = mediaItem.rating,
                                mediaType = MediaType.Movie
                            )
                        }
                        .onErrorReturn {
                            Log.w("MediaRepository", "Failed to fetch details for ${mediaItem.id}: ${it.message}")
                            Media(
                                id = mediaItem.id,
                                title = mediaItem.title,
                                posterUrl = null,
                                year = mediaItem.year,
                                rating = mediaItem.rating,
                                mediaType = MediaType.Movie
                            )
                        }
                }
                
                Single.zip(detailsSingles) { results ->
                    results.map { it as Media }
                }
            }
    }

    override fun getTVShows(page: Int): Single<List<Media>> {
        Log.d("MediaRepository", "Fetching TV shows - page: $page")
        return apiService.getMediaList("tv_series", page)
            .doOnSubscribe { Log.d("MediaRepository", "Starting TV shows API call") }
            .doOnError { error -> Log.e("MediaRepository", "Error fetching TV shows: ${error.message}", error) }
            .flatMap { response ->
                Log.d("MediaRepository", "TV Shows response: ${response.titles.size} items")
                
                val detailsSingles = response.titles.map { mediaItem ->
                    apiService.getMediaDetails(mediaItem.id)
                        .map { details ->
                            Media(
                                id = mediaItem.id,
                                title = mediaItem.title,
                                posterUrl = details.posterUrl,
                                year = mediaItem.year,
                                rating = mediaItem.rating,
                                mediaType = MediaType.TVShow
                            )
                        }
                        .onErrorReturn {
                            Log.w("MediaRepository", "Failed to fetch details for ${mediaItem.id}: ${it.message}")
                            Media(
                                id = mediaItem.id,
                                title = mediaItem.title,
                                posterUrl = null,
                                year = mediaItem.year,
                                rating = mediaItem.rating,
                                mediaType = MediaType.TVShow
                            )
                        }
                }
                
                Single.zip(detailsSingles) { results ->
                    results.map { it as Media }
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
        return Single.just(emptyList())
    }
}
