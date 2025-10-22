# WatchWise Setup Guide

## Quick Start

### 1. Get Watchmode API Key

1. Visit [watchmode.com](https://watchmode.com)
2. Sign up for a free account
3. Navigate to your API settings
4. Copy your API key

### 2. Configure Local Properties

Create `local.properties` in the project root (if it doesn't exist):

```properties
sdk.dir=/path/to/android/sdk
watchmode.api.key=YOUR_API_KEY_HERE
```

Replace `YOUR_API_KEY_HERE` with your actual Watchmode API key.

### 3. Build and Run

**Using Android Studio:**
1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Click "Run" or press Shift+F10
4. Select your device/emulator

**Using Command Line:**
```bash
./gradlew build
./gradlew installDebug
```

## Gradle Sync Issues

If you encounter Gradle sync errors:

```bash
# Clean and rebuild
./gradlew clean
./gradlew build

# Or use the wrapper
./gradlew --version
```

## Dependency Management

The project uses version catalogs (`libs.versions.toml`) for dependency management. All dependencies are defined there and referenced in `build.gradle.kts`.

### Adding New Dependencies

1. Add to `gradle/libs.versions.toml`
2. Reference in `build.gradle.kts` using `libs.xxx`

Example:
```toml
[libraries]
my-library = "com.example:my-library:1.0.0"
```

Then in `build.gradle.kts`:
```kotlin
implementation(libs.my.library)
```

## IDE Setup

### Android Studio Plugins

Recommended plugins:
- Kotlin
- Compose Preview
- Database Navigator (optional)

### Code Style

The project follows Kotlin style guidelines:
- 4-space indentation
- Trailing commas in multiline collections
- Consistent naming conventions

## Running Tests

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
```bash
./gradlew testDebugUnitTestCoverage
```

## Debugging

### Enable Logging

The app logs network requests in debug builds. Check Logcat:

```
adb logcat | grep "WatchWise"
```

### Common Issues

**Issue**: App crashes on startup
- **Solution**: Check API key in BuildConfig
- **Solution**: Ensure internet permission is granted

**Issue**: Images not loading
- **Solution**: Check image URLs from API
- **Solution**: Verify Coil is properly initialized

**Issue**: API calls failing
- **Solution**: Verify API key is valid
- **Solution**: Check network connectivity
- **Solution**: Review API rate limits

## Building APK

### Debug Build
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### Signing Release APK

Create a keystore:
```bash
keytool -genkey -v -keystore watchwise.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias watchwise
```

Add to `local.properties`:
```properties
RELEASE_STORE_FILE=watchwise.keystore
RELEASE_STORE_PASSWORD=your_password
RELEASE_KEY_ALIAS=watchwise
RELEASE_KEY_PASSWORD=your_password
```

## Performance Profiling

### Using Android Profiler

1. Run app on device/emulator
2. Android Studio > View > Tool Windows > Profiler
3. Monitor CPU, Memory, Network, Energy

### Memory Leaks

Check for memory leaks using:
- Android Studio Profiler
- LeakCanary (can be added as dependency)

## Troubleshooting

### Gradle Build Fails

```bash
# Update Gradle wrapper
./gradlew wrapper --gradle-version=latest

# Clear Gradle cache
rm -rf ~/.gradle/caches
./gradlew clean build
```

### Emulator Issues

```bash
# List available emulators
emulator -list-avds

# Start emulator
emulator -avd emulator_name

# Kill all emulators
adb emu kill
```

### ADB Connection Issues

```bash
# Restart ADB
adb kill-server
adb start-server

# List connected devices
adb devices
```

## Development Workflow

1. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature
   ```

2. **Make Changes**
   - Follow code style guidelines
   - Write tests for new features
   - Update documentation

3. **Test Locally**
   ```bash
   ./gradlew test
   ./gradlew build
   ```

4. **Commit and Push**
   ```bash
   git add .
   git commit -m "feat: add your feature"
   git push origin feature/your-feature
   ```

5. **Create Pull Request**
   - Describe changes
   - Link related issues
   - Request review

## Resources

- [Android Developer Documentation](https://developer.android.com)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [RxJava Documentation](https://reactivex.io/documentation)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Koin Documentation](https://insert-koin.io/)

## Support

For issues or questions:
1. Check existing GitHub issues
2. Create a new issue with detailed information
3. Contact the development team

---

Happy coding! 🚀
