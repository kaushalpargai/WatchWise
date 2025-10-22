# WatchMode API Documentation

## Overview

WatchWise uses the [Watchmode API](https://api.watchmode.com/) to fetch movie and TV show data. This document provides details about the API endpoints used in the app.

## Base URL

```
https://api.watchmode.com/v1/
```

## Authentication

All API requests require an API key, which is passed as a query parameter:

```
?apiKey=YOUR_API_KEY
```

The API key is automatically added to all requests via an OkHttp interceptor in the app.

## Endpoints

### 1. List Titles

**Endpoint:** `GET /list-titles`

**Description:** Retrieves a list of movies or TV shows.

**Query Parameters:**
- `types` (required): Type of media to fetch
  - `movie` - Movies only
  - `tv_series` - TV shows only
  - `movie,tv_series` - Both movies and TV shows
- `page` (optional): Page number for pagination (default: 1)
- `limit` (optional): Number of results per page (default: 20, max: 250)

**Example Request:**
```
GET https://api.watchmode.com/v1/list-titles?types=movie&page=1&limit=20&apiKey=YOUR_API_KEY
```

**Response:**
```json
{
  "title": "Movies",
  "titles": [
    {
      "id": "12345",
      "title": "The Matrix",
      "poster": "https://cdn.watchmode.com/posters/12345_poster.jpg",
      "year": 1999,
      "imdb_rating": 8.7,
      "media_type": "movie"
    }
  ],
  "page": 1,
  "total_pages": 100
}
```

### 2. Title Details

**Endpoint:** `GET /title/{id}/details/`

**Description:** Retrieves detailed information about a specific movie or TV show.

**Path Parameters:**
- `id` (required): The Watchmode ID of the title

**Example Request:**
```
GET https://api.watchmode.com/v1/title/12345/details/?apiKey=YOUR_API_KEY
```

**Response:**
```json
{
  "id": "12345",
  "title": "The Matrix",
  "plot_overview": "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
  "poster": "https://cdn.watchmode.com/posters/12345_poster.jpg",
  "year": 1999,
  "imdb_rating": 8.7,
  "release_date": "1999-03-31",
  "genre_names": ["Action", "Sci-Fi"],
  "media_type": "movie"
}
```

## Rate Limits

- **Free Tier**: 1,000 requests per day
- **Paid Tiers**: Higher limits available

## Error Handling

The API returns standard HTTP status codes:

- `200 OK` - Request successful
- `400 Bad Request` - Invalid parameters
- `401 Unauthorized` - Invalid or missing API key
- `404 Not Found` - Resource not found
- `429 Too Many Requests` - Rate limit exceeded
- `500 Internal Server Error` - Server error

**Error Response Example:**
```json
{
  "error": "Invalid API key",
  "status_code": 401
}
```

## Implementation in WatchWise

### API Service Interface

```kotlin
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
```

### Repository Implementation

```kotlin
class MediaRepositoryImpl(
    private val apiService: WatchModeApiService
) : MediaRepository {

    override fun getMovies(page: Int): Single<List<Media>> {
        return apiService.getMediaList("movie", page)
            .map { response ->
                response.titles.map { it.toDomain() }
            }
    }

    override fun getTVShows(page: Int): Single<List<Media>> {
        return apiService.getMediaList("tv_series", page)
            .map { response ->
                response.titles.map { it.toDomain() }
            }
    }

    override fun getMediaDetails(mediaId: String): Single<MediaDetails> {
        return apiService.getMediaDetails(mediaId)
            .map { it.toDomain() }
    }
}
```

### Parallel API Calls

The app uses RxJava's `Single.zip` to fetch movies and TV shows in parallel:

```kotlin
Single.zip(
    repository.getMovies(),
    repository.getTVShows()
) { movies, shows -> 
    Pair(movies, shows) 
}
```

## Best Practices

1. **Caching**: Implement caching to reduce API calls
2. **Error Handling**: Always handle network errors gracefully
3. **Rate Limiting**: Monitor your API usage to avoid hitting rate limits
4. **Pagination**: Use pagination for large datasets
5. **Image Loading**: Use efficient image loading libraries like Coil

## Additional Resources

- [Watchmode API Documentation](https://api.watchmode.com/docs/)
- [API Key Management](https://api.watchmode.com/account/)
- [API Status Page](https://status.watchmode.com/)

## Support

For API-related issues:
- Email: support@watchmode.com
- Documentation: https://api.watchmode.com/docs/

For WatchWise app issues:
- GitHub Issues: [Your Repository URL]
