# WatchWise - Pre-Submission Checklist

Use this checklist to ensure everything is ready before submitting your assignment.

## 📋 Project Setup

- [ ] Project opens in Android Studio without errors
- [ ] Gradle sync completes successfully
- [ ] All dependencies are properly configured
- [ ] `local.properties` contains valid Watchmode API key
- [ ] BuildConfig generates WATCHMODE_API_KEY correctly

## 🏗️ Architecture & Code

- [ ] MVVM architecture implemented
- [ ] Clean architecture with Data/Domain/UI layers
- [ ] Koin dependency injection configured
- [ ] Repository pattern implemented
- [ ] ViewModels use StateFlow for state management
- [ ] RxJava used for parallel API calls (Single.zip)
- [ ] Proper error handling throughout the app
- [ ] No memory leaks (CompositeDisposable cleared)

## 🎨 UI & UX

- [ ] Jetpack Compose UI with Material3
- [ ] Home screen with Movies/TV Shows tabs
- [ ] Grid layout displays media items correctly
- [ ] Shimmer loading effects work properly
- [ ] Images load correctly with Coil
- [ ] Details screen shows full information
- [ ] Navigation between screens works smoothly
- [ ] Error states display with retry button
- [ ] Empty states handled gracefully

## 🌐 API Integration

- [ ] Retrofit configured with RxJava adapter
- [ ] OkHttp client with logging interceptor
- [ ] API key injected via interceptor
- [ ] Movies endpoint works (`/list-titles?types=movie`)
- [ ] TV Shows endpoint works (`/list-titles?types=tv_series`)
- [ ] Details endpoint works (`/title/{id}/details/`)
- [ ] DTOs map correctly to domain models
- [ ] Error responses handled properly

## 🧪 Testing

- [ ] Unit tests for MediaRepository
- [ ] Unit tests for HomeViewModel
- [ ] Unit tests for DetailsViewModel (if applicable)
- [ ] All tests pass (`./gradlew test`)
- [ ] Test coverage is reasonable (>50%)
- [ ] Mock objects used correctly
- [ ] Both success and error scenarios tested

## 📱 App Functionality

### Home Screen
- [ ] App launches successfully
- [ ] Movies tab loads and displays data
- [ ] TV Shows tab loads and displays data
- [ ] Tab switching works smoothly
- [ ] Shimmer shows while loading
- [ ] Grid layout is responsive
- [ ] Scroll works properly
- [ ] Tapping a card navigates to details

### Details Screen
- [ ] Details screen loads correctly
- [ ] Poster image displays
- [ ] Title, year, rating shown
- [ ] Overview/description displays
- [ ] Genres listed (if available)
- [ ] Release date shown (if available)
- [ ] Back button returns to home
- [ ] Shimmer shows while loading

### Error Handling
- [ ] Network errors show friendly message
- [ ] Retry button works correctly
- [ ] Invalid API key handled gracefully
- [ ] Empty responses handled
- [ ] App doesn't crash on errors

## 📦 Build & APK

- [ ] Debug build succeeds (`./gradlew assembleDebug`)
- [ ] Release build succeeds (`./gradlew assembleRelease`)
- [ ] APK installs on device/emulator
- [ ] APK size is reasonable (<15MB)
- [ ] App runs without crashes
- [ ] No ProGuard/R8 issues (release build)

## 📄 Documentation

- [ ] README.md is comprehensive
- [ ] SETUP.md has clear instructions
- [ ] API_DOCUMENTATION.md explains endpoints
- [ ] QUICKSTART.md for quick setup
- [ ] PROJECT_SUMMARY.md summarizes the project
- [ ] DEMO_VIDEO_GUIDE.md for video recording
- [ ] CONTRIBUTING.md for contributors
- [ ] Code has meaningful comments
- [ ] Complex logic is documented

## 🎬 Demo Video (4-6 minutes)

### Content
- [ ] Introduction (30 seconds)
- [ ] App demo - Home screen (45 seconds)
- [ ] App demo - Details screen (45 seconds)
- [ ] App demo - Error handling (30 seconds)
- [ ] Code walkthrough - Project structure (30 seconds)
- [ ] Code walkthrough - API integration (45 seconds)
- [ ] Code walkthrough - Repository (30 seconds)
- [ ] Code walkthrough - ViewModel (45 seconds)
- [ ] Code walkthrough - DI with Koin (30 seconds)
- [ ] Code walkthrough - UI components (30 seconds)
- [ ] Testing demonstration (30 seconds)
- [ ] Build process (30 seconds)
- [ ] Conclusion (30 seconds)

### Quality
- [ ] Video is 1080p or higher
- [ ] Audio is clear and audible
- [ ] Screen is readable (font size adequate)
- [ ] No background noise
- [ ] Smooth transitions
- [ ] Proper pacing (not too fast/slow)
- [ ] All features demonstrated
- [ ] Code is explained clearly

### Format
- [ ] Video format is MP4
- [ ] Duration is 4-6 minutes
- [ ] File size is reasonable (<500MB)
- [ ] Video plays without issues

## 📤 Submission Package

### Required Files
- [ ] Source code (entire project)
- [ ] README.md
- [ ] SETUP.md
- [ ] APK file (debug or release)
- [ ] Demo video (MP4)
- [ ] Screenshots (optional but recommended)

### Optional but Recommended
- [ ] QUICKSTART.md
- [ ] API_DOCUMENTATION.md
- [ ] PROJECT_SUMMARY.md
- [ ] DEMO_VIDEO_GUIDE.md
- [ ] CONTRIBUTING.md
- [ ] local.properties.example

### GitHub Repository (if applicable)
- [ ] Repository is public or accessible
- [ ] README.md is visible on repo home
- [ ] .gitignore excludes sensitive files
- [ ] local.properties is NOT committed
- [ ] API keys are NOT in source code
- [ ] Commits have meaningful messages
- [ ] Repository is well-organized

## 🔒 Security

- [ ] API key in local.properties (not in code)
- [ ] local.properties is gitignored
- [ ] No hardcoded credentials
- [ ] BuildConfig used for API key
- [ ] HTTPS used for all API calls
- [ ] No sensitive data in logs (release)

## ⚡ Performance

- [ ] App launches in <3 seconds
- [ ] Screen transitions are smooth
- [ ] Images load efficiently
- [ ] No ANR (Application Not Responding)
- [ ] Memory usage is reasonable
- [ ] No memory leaks detected
- [ ] API calls are optimized
- [ ] UI is responsive

## 🎯 Assignment Requirements

### Core Features
- [x] Home screen with tabs (Movies | TV Shows)
- [x] Parallel API calls with RxJava Single.zip
- [x] Display lists with poster, title, year/rating
- [x] Shimmer/skeleton while loading
- [x] Error state with retry
- [x] Details screen with full information
- [x] Navigation on item click

### Architecture
- [x] MVVM + Koin DI
- [x] Data layer (Retrofit/OkHttp + DTOs + Repository)
- [x] Domain layer (models/use-cases)
- [x] UI layer (ViewModels + Compose screens + Navigation)
- [x] Image loading with Coil
- [x] Error handling with friendly messages

### Tech Stack
- [x] Kotlin
- [x] Jetpack Compose (Material3)
- [x] Navigation-Compose
- [x] Retrofit + OkHttp + Gson + RxJava3 adapter
- [x] RxJava3 + RxKotlin
- [x] Koin for DI
- [x] Coil for images
- [x] Accompanist Placeholder for shimmer

### API Integration
- [x] Watchmode API base URL
- [x] list-titles?types=movie endpoint
- [x] list-titles?types=tv_series endpoint
- [x] title/{id}/details endpoint
- [x] API key via query param or header

### State Management
- [x] UiState sealed class or similar
- [x] HomeViewModel with separate states
- [x] DetailsViewModel with UiState
- [x] Loading/Success/Error states

### Deliverables
- [x] GitHub repo with README
- [x] APK (debug/release)
- [x] 4-6 min code walkthrough video
- [x] Basic unit tests

## 🚀 Final Checks

- [ ] Everything compiles without errors
- [ ] All tests pass
- [ ] App runs on emulator/device
- [ ] All features work as expected
- [ ] Documentation is complete
- [ ] Demo video is recorded
- [ ] APK is generated
- [ ] Submission package is ready

## 📝 Pre-Submission Notes

**Date:** _______________  
**Tested On:**  
- [ ] Emulator (API Level: ___)
- [ ] Physical Device (Model: ___, Android: ___)

**Known Issues:**
- _______________________________
- _______________________________

**Additional Notes:**
- _______________________________
- _______________________________

---

## ✅ Final Sign-Off

- [ ] I have reviewed all items in this checklist
- [ ] All core features are implemented and working
- [ ] Documentation is complete and accurate
- [ ] Code is clean and well-organized
- [ ] Tests are passing
- [ ] Demo video is ready
- [ ] APK is generated and tested
- [ ] Project is ready for submission

**Completed By:** _______________  
**Date:** _______________  
**Signature:** _______________

---

**🎉 Congratulations! Your WatchWise project is ready for submission!**
