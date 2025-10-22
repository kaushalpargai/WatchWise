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
                
                // Fetch details for each movie to get poster URLs
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
                            // If details fetch fails, return media without poster
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
                
                // Combine all detail requests
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
                
                // Fetch details for each TV show to get poster URLs
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
                            // If details fetch fails, return media without poster
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
                
                // Combine all detail requests
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
        // This is a placeholder. The actual implementation would use a search endpoint if available
        // For now, we'll return an empty list
        return Single.just(emptyList())
    }
}
