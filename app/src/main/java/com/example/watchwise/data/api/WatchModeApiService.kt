package com.example.watchwise.data.api

import com.example.watchwise.data.model.MediaDetailsResponse
import com.example.watchwise.data.model.MediaListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchModeApiService {
    @GET("list-titles")
    fun getMediaList(
        @Query("types") types: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Single<MediaListResponse>

    @GET("title/{id}/details/")
    fun getMediaDetails(
        @Path("id") id: String
    ): Single<MediaDetailsResponse>
}
