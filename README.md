# MonStock 📦

An Android stock management application built with modern Android development practices.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt |
| Database | Room |
| Networking | Retrofit + OkHttp |
| Navigation | Navigation Compose |
| Async | Kotlin Coroutines + Flow |
| Image Loading | Coil |
| Preferences | DataStore |

## Requirements

- Android Studio Ladybug (2024.2.1) or later
- JDK 17+
- Android SDK 35 (Android 15)
- Min SDK: 26 (Android 8.0)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── kotlin/com/monstock/app/
│   │   │   ├── MonStockApplication.kt      # Hilt Application class
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   └── database/           # Room DB, DAOs, entities
│   │   │   │   ├── remote/
│   │   │   │   │   └── api/                # Retrofit API services
│   │   │   │   └── repository/             # Repository implementations
│   │   │   ├── di/
│   │   │   │   ├── DatabaseModule.kt       # Room DI
│   │   │   │   └── NetworkModule.kt        # Retrofit DI
│   │   │   ├── domain/
│   │   │   │   ├── model/                  # Domain models
│   │   │   │   ├── repository/             # Repository interfaces
│   │   │   │   └── usecase/                # Use cases
│   │   │   └── ui/
│   │   │       ├── MainActivity.kt
│   │   │       ├── navigation/             # NavHost & Screen routes
│   │   │       ├── screens/
│   │   │       │   └── home/               # HomeScreen + HomeViewModel
│   │   │       └── theme/                  # Color, Type, Theme
│   │   └── res/
│   ├── test/                               # Unit tests
│   └── androidTest/                        # Instrumented tests
```

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device (API 26+)

## Build Variants

| Variant | Description |
|---------|-------------|
| `debug` | Debug build with logging, app ID suffix `.debug` |
| `release` | Minified, obfuscated production build |
