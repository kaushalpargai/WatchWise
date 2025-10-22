package com.example.watchwise.data.repository

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
                response.titles.map { mediaItem ->
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
                response.titles.map { mediaItem ->
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
