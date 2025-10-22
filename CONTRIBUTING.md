# Contributing to WatchWise

Thank you for your interest in contributing to WatchWise! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment for all contributors.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- Clear description of the bug
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)
- Device/emulator information
- Android version

### Suggesting Features

Feature requests are welcome! Please include:
- Clear description of the feature
- Use case and benefits
- Potential implementation approach
- Mockups or examples (if applicable)

### Pull Requests

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow the code style guidelines
   - Write tests for new features
   - Update documentation

4. **Commit your changes**
   ```bash
   git commit -m "feat: add your feature description"
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request**
   - Provide a clear description
   - Link related issues
   - Include screenshots/videos if UI changes

## Development Setup

### Prerequisites
- Android Studio (latest stable version)
- JDK 11 or higher
- Android SDK 29+
- Git

### Setup Steps

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/WatchWise.git
   cd WatchWise
   ```

2. Create `local.properties`
   ```properties
   watchmode.api.key=YOUR_API_KEY_HERE
   ```

3. Open in Android Studio
   - File > Open > Select project directory
   - Wait for Gradle sync

4. Run the app
   - Select device/emulator
   - Click Run or press Shift+F10

## Code Style Guidelines

### Kotlin

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused
- Add comments for complex logic

### Compose

- Use `@Composable` functions for UI components
- Extract reusable components
- Use `remember` and `rememberSaveable` appropriately
- Follow Material Design 3 guidelines

### Architecture

- Follow MVVM pattern
- Keep ViewModels free of Android framework dependencies
- Use repository pattern for data access
- Separate concerns clearly

### Example Code Style

```kotlin
// Good
@Composable
fun MediaCard(
    media: Media,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        // Card content
    }
}

// Bad
@Composable
fun card(m: Media, c: () -> Unit) {
    Card(modifier = Modifier.clickable { c() }) {
        // Card content
    }
}
```

## Commit Message Guidelines

Follow [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, etc.)
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Maintenance tasks

### Examples

```
feat: add search functionality to home screen
fix: resolve crash when loading details without internet
docs: update README with new setup instructions
refactor: extract media card into separate composable
test: add unit tests for HomeViewModel
```

## Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Test coverage
./gradlew testDebugUnitTestCoverage
```

### Writing Tests

- Write tests for new features
- Maintain test coverage above 70%
- Test both success and error scenarios
- Use descriptive test names

```kotlin
@Test
fun `getMovies returns list of movies when API call succeeds`() {
    // Arrange
    val mockMovies = listOf(/* mock data */)
    whenever(apiService.getMediaList("movie")).thenReturn(Single.just(mockMovies))
    
    // Act
    val result = repository.getMovies().test()
    
    // Assert
    result.assertNoErrors()
    result.assertValue { it.size == mockMovies.size }
}
```

## Documentation

- Update README.md for significant changes
- Add inline comments for complex logic
- Update API documentation if endpoints change
- Include KDoc for public APIs

```kotlin
/**
 * Fetches a list of movies from the API.
 *
 * @param page The page number to fetch (default: 1)
 * @return A Single emitting a list of Media objects
 */
fun getMovies(page: Int = 1): Single<List<Media>>
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/watchwise/
│   │   │   ├── data/           # Data layer
│   │   │   ├── di/             # Dependency injection
│   │   │   ├── navigation/     # Navigation
│   │   │   └── ui/             # UI layer
│   │   └── res/                # Resources
│   └── test/                   # Unit tests
└── build.gradle.kts
```

## Adding Dependencies

1. Add to `build.gradle.kts`
2. Sync Gradle
3. Update documentation if needed
4. Ensure compatibility with existing dependencies

## Code Review Process

All pull requests require:
- At least one approval
- Passing CI/CD checks
- No merge conflicts
- Updated documentation

### Review Checklist

- [ ] Code follows style guidelines
- [ ] Tests are included and passing
- [ ] Documentation is updated
- [ ] No breaking changes (or properly documented)
- [ ] Performance considerations addressed
- [ ] Security best practices followed

## Release Process

1. Update version in `build.gradle.kts`
2. Update CHANGELOG.md
3. Create release branch
4. Test thoroughly
5. Create release tag
6. Build and sign APK
7. Create GitHub release

## Getting Help

- **Issues**: Check existing issues or create a new one
- **Discussions**: Use GitHub Discussions for questions
- **Email**: [Your email for project-related queries]

## Recognition

Contributors will be recognized in:
- README.md Contributors section
- Release notes
- Project documentation

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

---

Thank you for contributing to WatchWise! 🎬
