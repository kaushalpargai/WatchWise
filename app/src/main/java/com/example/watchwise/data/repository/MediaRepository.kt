package com.example.watchwise.data.repository

import com.example.watchwise.data.model.Media
import com.example.watchwise.data.model.MediaDetails
import com.example.watchwise.data.model.MediaType
import io.reactivex.rxjava3.core.Single

interface MediaRepository {
    fun getMovies(page: Int = 1): Single<List<Media>>
    fun getTVShows(page: Int = 1): Single<List<Media>>
    fun getMediaDetails(mediaId: String): Single<MediaDetails>
    fun searchMedia(query: String, type: MediaType? = null): Single<List<Media>>
}
