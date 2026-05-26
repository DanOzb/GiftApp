# GiftApp

An Android app for sending and receiving multimedia gifts with bundles of text, images, video, and audio that recipients open and swipe through one block at a time.

## Gift

Each gift is a list of ordered **content blocks**:

- **Header** - large title text
- **Text** - body message
- **Image** - picture with optional caption
- **Video** - playable clip
- **Audio** - playable voice/sound message
- **Footer** - closing text

## Tech stack

- **UI** - Jetpack Compose, Material 3, dynamic color (Android 12+), Compose Navigation
- **DI** - Hilt
- **Local storage** - Room
- **Remote backend** - Firebase 
- **Media playback** - Media3 / ExoPlayer 
- **Image loading** - Coil
- **Language** - Kotlin

## Project structure

```
app/src/main/java/com/example/giftapp/
├── MyApplication.kt                  # @HiltAndroidApp
├── MyFirebaseMessagingService.kt     # FCM token and message handling 
├── data/
│   ├── local/                        # Room database and DAO
│   └── repository/GiftRepositoryImpl # Repository implementation
├── di/
│   ├── AppModule.kt                  # Hilt bindings
│   ├── CacheModule.kt                # SimpleCache and DownloadManager
│   └── MediaModule.kt                # ExoPlayer 
├── domain/
│   ├── model/                        # Data models and JSON converter
│   └── repository/GiftRepository     # Repository interface
├── ui/
│   ├── main/MainActivity.kt          # @AndroidEntryPoint
│   ├── navigation/MainNavigation.kt  # Bottom navigation
│   ├── screen/                       # Screen composables
│   │   └── components/               # Composables
│   └── theme/                        # Color, typography, AppTheme
└── viewmodel/
    ├── GiftViewModel.kt              # Main viewmodel 
    └── PlayerViewModel.kt            # ExoPlayer viemodel
```

## Setup

1. **Clone** the repo and open it in Android Studio (JDK 11+).
2. **Firebase** - create a Firebase project and add an Android app with package name `com.example.giftapp`. Enable:
   - Authentication Anonymous
   - Cloud Firestore
   - Storage
   - Cloud Messaging (optional, only used for push notifications)
3. Download the `google-services.json` from the Firebase console and drop it into `app/`.
4. **Firestore composite index** - Firestore will print a console link the first time it fails, follow it to create the required composite index.
5. **Security rules** - Restrict reads on `gifts` to documents where `sender != request.auth.uid`, and restrict writes/deletes appropriately.
7. Build and run on a device or emulator with API 31+.

## Notes
Project still in progress
