package com.example.watchwise.data.model

import com.google.gson.annotations.SerializedName

data class MediaListResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("titles")
    val titles: List<MediaItem> = emptyList(),
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("total_pages")
    val totalPages: Int = 1
)

data class MediaItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster")
    val posterUrl: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("imdb_rating")
    val rating: Float?,
    @SerializedName("media_type")
    val mediaType: String
)

data class MediaDetailsResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("plot_overview")
    val overview: String?,
    @SerializedName("poster")
    val posterUrl: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("imdb_rating")
    val rating: Float?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("genre_names")
    val genres: List<String> = emptyList(),
    @SerializedName("media_type")
    val mediaType: String
)

// Domain Models
sealed class MediaType {
    object Movie : MediaType()
    object TVShow : MediaType()
    
    companion object {
        fun fromString(type: String): MediaType {
            return when (type.lowercase()) {
                "movie" -> Movie
                "tv_series" -> TVShow
                else -> Movie // Default to Movie if type is unknown
            }
        }
    }
}

data class Media(
    val id: String,
    val title: String,
    val posterUrl: String?,
    val year: Int?,
    val rating: Float?,
    val mediaType: MediaType
)

data class MediaDetails(
    val id: String,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val year: Int?,
    val rating: Float?,
    val releaseDate: String?,
    val genres: List<String>,
    val mediaType: MediaType
)
