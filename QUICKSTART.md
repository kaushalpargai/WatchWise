# WatchWise - Quick Start Guide

Get WatchWise up and running in 5 minutes! ⚡

## Prerequisites

- ✅ Android Studio installed
- ✅ Android SDK 29+ configured
- ✅ Watchmode API key ([Get one here](https://api.watchmode.com/))

## Step 1: Get Your API Key (2 minutes)

1. Visit https://api.watchmode.com/
2. Click "Sign Up" or "Get API Key"
3. Create a free account
4. Copy your API key from the dashboard

## Step 2: Configure the Project (1 minute)

1. **Open the project in Android Studio**
   ```
   File > Open > Select WatchWise folder
   ```

2. **Create `local.properties` file** in the project root (if it doesn't exist)

3. **Add your API key:**
   ```properties
   watchmode.api.key=YOUR_API_KEY_HERE
   ```
   
   Replace `YOUR_API_KEY_HERE` with your actual API key.

## Step 3: Sync and Build (1 minute)

1. **Sync Gradle:**
   - Click "Sync Now" in the notification bar
   - Or: File > Sync Project with Gradle Files

2. **Wait for sync to complete** (30-60 seconds)

## Step 4: Run the App (1 minute)

1. **Select a device:**
   - Connect a physical device via USB
   - Or start an Android emulator

2. **Run the app:**
   - Click the green "Run" button (▶️)
   - Or press `Shift + F10` (Windows/Linux) or `Control + R` (Mac)

3. **Wait for build and installation** (30-45 seconds first time)

## Step 5: Test the App

✅ **Home Screen:**
- See shimmer loading effect
- View movies grid
- Switch to TV Shows tab

✅ **Details Screen:**
- Tap any movie/show card
- View full details
- Navigate back

✅ **Error Handling:**
- Turn off internet
- See error message
- Tap retry button

## Troubleshooting

### Build Fails?

**Error:** "WATCHMODE_API_KEY not found"
```bash
# Solution: Check local.properties exists and has the API key
cat local.properties
# Should show: watchmode.api.key=YOUR_KEY
```

**Error:** "Gradle sync failed"
```bash
# Solution: Clean and rebuild
./gradlew clean
./gradlew build
```

### App Crashes?

**Check:**
1. API key is valid
2. Internet permission is granted
3. Device has internet connection

**View logs:**
```bash
adb logcat | grep WatchWise
```

## Next Steps

### Build APK
```bash
# Debug APK
./gradlew assembleDebug

# Find APK at:
# app/build/outputs/apk/debug/app-debug.apk
```

### Run Tests
```bash
./gradlew test
```

### Record Demo Video
Follow the guide in [DEMO_VIDEO_GUIDE.md](DEMO_VIDEO_GUIDE.md)

## Project Structure at a Glance

```
WatchWise/
├── app/src/main/java/com/example/watchwise/
│   ├── data/          # API, models, repository
│   ├── di/            # Koin dependency injection
│   ├── navigation/    # Navigation setup
│   └── ui/            # Compose screens & ViewModels
├── README.md          # Full documentation
├── SETUP.md           # Detailed setup guide
└── local.properties   # Your API key (create this!)
```

## Key Files

- **API Service:** `data/api/WatchModeApiService.kt`
- **Repository:** `data/repository/MediaRepositoryImpl.kt`
- **Home Screen:** `ui/home/HomeScreen.kt`
- **Details Screen:** `ui/details/MediaDetailsScreen.kt`
- **DI Module:** `di/AppModule.kt`

## Tech Stack Summary

- **UI:** Jetpack Compose + Material3
- **Networking:** Retrofit + OkHttp
- **Reactive:** RxJava3 + RxKotlin
- **DI:** Koin
- **Images:** Coil
- **Navigation:** Navigation Compose

## Common Commands

```bash
# Build
./gradlew build

# Run tests
./gradlew test

# Clean build
./gradlew clean build

# Install on device
./gradlew installDebug

# Check dependencies
./gradlew dependencies
```

## Need Help?

📖 **Full Documentation:** [README.md](README.md)  
🔧 **Setup Guide:** [SETUP.md](SETUP.md)  
🎬 **Demo Video Guide:** [DEMO_VIDEO_GUIDE.md](DEMO_VIDEO_GUIDE.md)  
🐛 **Issues:** Check existing issues or create a new one

## Success Checklist

- [ ] API key added to local.properties
- [ ] Gradle sync successful
- [ ] App builds without errors
- [ ] App runs on device/emulator
- [ ] Movies tab loads data
- [ ] TV Shows tab loads data
- [ ] Details screen works
- [ ] Error handling works

---

**You're all set! 🎉**

The app should now be running. Explore the features and check out the full documentation for more details.

**Happy coding! 🚀**
