# WatchWise - Movie & TV Discovery App

**Watch smarter.** 🎬

WatchWise is a modern Android app built with Jetpack Compose that helps you discover movies and TV shows using the Watchmode API. The app features a clean architecture with MVVM pattern, reactive programming with RxJava, and beautiful UI with shimmer loading effects.

## Features

- **Dual Tab Navigation**: Switch between Movies and TV Shows with a sleek tab interface
- **Parallel API Calls**: Fetch movies and TV shows concurrently using RxJava's `Single.zip`
- **Shimmer Loading**: Beautiful skeleton loading states while data is being fetched
- **Error Handling**: User-friendly error messages with retry functionality
- **Image Loading**: Efficient image loading with Coil
- **Material Design 3**: Modern UI with Material Design 3 components
- **Clean Architecture**: Separation of concerns with Data, Domain, and UI layers

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Navigation**: Navigation Compose
- **Networking**: Retrofit + OkHttp
- **Reactive Programming**: RxJava3 + RxKotlin
- **Dependency Injection**: Koin
- **Image Loading**: Coil
- **Loading Effects**: Accompanist Placeholder (Shimmer)
- **Testing**: JUnit, Mockito, MockK

## Project Structure

```
app/src/main/java/com/example/watchwise/
├── data/
│   ├── api/
│   │   └── WatchModeApiService.kt       # Retrofit API interface
│   ├── model/
│   │   └── MediaModels.kt               # Data and domain models
│   └── repository/
│       ├── MediaRepository.kt           # Repository interface
│       └── MediaRepositoryImpl.kt        # Repository implementation
├── di/
│   └── AppModule.kt                     # Koin DI configuration
├── navigation/
│   └── NavGraph.kt                      # Navigation setup
├── ui/
│   ├── MainActivity.kt                  # Main activity
│   ├── WatchWiseApp.kt                  # Main composable
│   ├── home/
│   │   ├── HomeScreen.kt                # Home screen UI
│   │   └── HomeViewModel.kt             # Home screen ViewModel
│   ├── details/
│   │   ├── MediaDetailsScreen.kt        # Details screen UI
│   │   └── DetailsViewModel.kt          # Details screen ViewModel
│   └── theme/
│       ├── Color.kt                     # Color palette
│       ├── Theme.kt                     # Theme configuration
│       └── Typography.kt                # Typography styles
└── WatchWiseApp.kt                      # Application class
```

## Setup Instructions

### Prerequisites

- Android Studio (latest version)
- Android SDK 29+
- Watchmode API Key (get one from [watchmode.com](https://watchmode.com))

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd WatchWise
   ```

2. **Add your Watchmode API Key**
   
   Create or edit `local.properties` in the project root:
   ```properties
   watchmode.api.key=YOUR_API_KEY_HERE
   ```

3. **Build and Run**
   ```bash
   # Using Android Studio: File > Open > Select project
   # Or using command line:
   ./gradlew build
   ./gradlew installDebug
   ```

## Architecture

### MVVM Pattern

The app follows the Model-View-ViewModel (MVVM) architecture:

- **Model**: Data models and repository layer
- **View**: Compose UI components
- **ViewModel**: State management and business logic

### Reactive Programming

The app uses RxJava3 for reactive data streams:

```kotlin
// Parallel API calls using Single.zip
Single.zip(
    repository.getMovies(),
    repository.getTVShows()
) { movies, shows -> Pair(movies, shows) }
```

### Dependency Injection

Koin is used for dependency injection:

```kotlin
val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
    single<MediaRepository> { MediaRepositoryImpl(get()) }
}
```

## API Integration

### Watchmode API Endpoints

- **List Titles**: `GET /list-titles?types=movie|tv_series`
- **Media Details**: `GET /title/{id}/details/`

### Authentication

The API key is automatically added to all requests via an OkHttp interceptor:

```kotlin
.addInterceptor { chain ->
    val original = chain.request()
    val url = original.url.newBuilder()
        .addQueryParameter("apiKey", BuildConfig.WATCHMODE_API_KEY)
        .build()
    val request = original.newBuilder().url(url).build()
    chain.proceed(request)
}
```

## State Management

### UI State

The app uses sealed classes for type-safe state management:

```kotlin
sealed class UiState<T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### ViewModel State Flow

ViewModels expose `StateFlow` for reactive UI updates:

```kotlin
private val _uiState = MutableStateFlow(HomeUiState())
val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
```

## Testing

### Unit Tests

The project includes unit tests for:

- **Repository**: Testing API calls and data mapping
- **ViewModel**: Testing state transitions and user interactions

Run tests:
```bash
./gradlew test
```

### Test Coverage

- Repository tests with MockWebServer
- ViewModel state transition tests
- Error handling tests

## Building APK

### Debug APK

```bash
./gradlew assembleDebug
# APK location: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK

```bash
./gradlew assembleRelease
# APK location: app/build/outputs/apk/release/app-release.apk
```

## Error Handling

The app implements comprehensive error handling:

- **Network Errors**: Displayed with user-friendly messages
- **Retry Mechanism**: Users can retry failed requests
- **Graceful Degradation**: App continues functioning with partial data

## Performance Optimizations

- **Image Caching**: Coil handles image caching automatically
- **Lazy Loading**: Grid items are loaded lazily
- **Efficient Recomposition**: Compose optimizes UI redraws
- **Connection Pooling**: OkHttp manages connection pooling

## Future Enhancements

- [ ] Pull-to-refresh functionality
- [ ] Pagination with endless scroll
- [ ] Search functionality
- [ ] Favorites/Watchlist feature
- [ ] Dark/Light theme toggle
- [ ] Offline support with local caching
- [ ] Share functionality
- [ ] User reviews and ratings

## Troubleshooting

### API Key Issues

If you see "YOUR_API_KEY_HERE" in the app, ensure:
1. `local.properties` exists in the project root
2. It contains `watchmode.api.key=YOUR_ACTUAL_KEY`
3. Rebuild the project

### Build Issues

```bash
# Clean build
./gradlew clean build

# Update dependencies
./gradlew dependencies --refresh-dependencies
```

### Runtime Issues

- Check logcat for detailed error messages
- Ensure internet permission is granted
- Verify API key is valid

## Support

For issues, questions, or suggestions, please open an issue on GitHub or contact the development team.

---

**Made with ❤️ by the WatchWise Team**
