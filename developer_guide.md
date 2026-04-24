# MonStock — Developer Guide 🛠️

This guide covers architecture decisions, project structure, coding conventions, and how to extend the application.

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Getting Started](#getting-started)
3. [Tech Stack](#tech-stack)
4. [Project Structure](#project-structure)
5. [Architecture](#architecture)
6. [Data Layer](#data-layer)
7. [Domain Layer](#domain-layer)
8. [UI Layer](#ui-layer)
9. [Dependency Injection (Hilt)](#dependency-injection-hilt)
10. [Navigation](#navigation)
11. [Notifications & Background Work](#notifications--background-work)
12. [Barcode & Camera](#barcode--camera)
13. [Build Variants](#build-variants)
14. [Testing](#testing)
15. [Adding a New Feature — Checklist](#adding-a-new-feature--checklist)
16. [Dependency Versions](#dependency-versions)

---

## Prerequisites

| Tool | Version |
|------|---------|
| Android Studio | Ladybug (2024.2.1) or later |
| JDK | 17+ |
| Android SDK | 35 (Android 15) |
| Kotlin | 2.1.0 |
| Gradle | 8.x (via wrapper) |

---

## Getting Started

```bash
# Clone the repository
git clone https://github.com/Taccart/MonStock.git
cd MonStock

# Open in Android Studio, let Gradle sync automatically
```

The project uses a **Gradle version catalog** (`gradle/libs.versions.toml`) for centralised dependency management. All library versions are declared there.

---

## Tech Stack

| Concern | Library / Tool |
|---------|---------------|
| Language | Kotlin 2.1.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.53.1 |
| Database | Room 2.7.0 |
| Image loading | Coil 2.7.0 |
| Preferences | DataStore 1.1.2 |
| Camera | CameraX 1.4.1 |
| QR generation | ZXing Core 3.5.3 |
| Background work | WorkManager 2.10.0 |

---


## Deploying Over USB

To deploy MonStock to a physical Android device over USB:

1. Enable Developer Options and USB Debugging on your Android device:
    - Go to Settings > About phone > tap 'Build number' 7 times to unlock Developer Options.
    - In Developer Options, enable 'USB debugging'.
2. Connect your device to your computer via USB.
3. Authorize the computer on your device if prompted.
4. Verify your device is detected:

    ```bash
    adb devices
    ```

    Your device should appear in the list.
5. From the project root, run:

    ```bash
    ./gradlew installDebug
    ```

    This will build and install the app on your connected device.
6. The app should launch automatically. If not, open it from the app drawer.

You can also deploy over USB using Android Studio by selecting your device in the device dropdown and clicking 'Run'.

## Project Structure

```
app/src/main/kotlin/com/monstock/app/
├── MonStockApplication.kt          # Hilt @HiltAndroidApp entry point
├── data/
│   ├── local/
│   │   └── database/               # Room DB, entities, DAOs, converters
│   ├── model/                      # Data-layer models / DTOs
│   ├── remote/
│   │   └── api/                    # Retrofit service interfaces + response DTOs
│   └── repository/                 # Repository implementations
├── di/
│   ├── DatabaseModule.kt           # Room + DAO bindings
│   └── NetworkModule.kt            # Retrofit + OkHttp bindings
├── domain/
│   └── model/                      # Pure Kotlin domain models (no Android deps)
├── notification/
│   ├── NotificationHelper.kt       # Channel creation & notification dispatch
│   ├── NotificationPreferences.kt  # DataStore-backed notification settings
│   ├── NotificationScheduler.kt    # WorkManager scheduling helpers
│   └── worker/                     # Worker classes (expiry, low-stock, digest)
├── ui/
│   ├── MainActivity.kt             # Single Activity host
│   ├── components/                 # Shared Composable components
│   │   ├── ItemCard.kt
│   │   ├── SummaryCard.kt
│   │   ├── EmptyState.kt
│   │   └── ConfirmDeleteDialog.kt
│   ├── navigation/
│   │   └── NavHost.kt              # NavHost + all route definitions
│   ├── screens/
│   │   ├── home/                   # HomeScreen + HomeViewModel
│   │   ├── pantry/                 # PantryListScreen + PantryViewModel
│   │   ├── shelf/                  # ShelfScreen + ShelfViewModel
│   │   ├── itemlist/               # ItemListScreen + ItemListViewModel
│   │   ├── itemdetail/             # ItemDetailScreen + ItemDetailViewModel
│   │   ├── addedit/                # AddEditItemScreen + AddEditItemViewModel
│   │   ├── scanner/                # CameraScannerScreen, BarcodeConfirmScreen,
│   │   │                           # QrLabelScreen, RestockScanScreen + ViewModels
│   │   ├── inventory/              # Inventory session screens + ViewModels
│   │   └── statistics/             # StatisticsScreen + StatisticsViewModel
│   └── theme/
│       ├── Color.kt
│       ├── Type.kt
│       └── Theme.kt
└── util/                           # Generic utilities / extensions
```

---

## Architecture

MonStock follows **MVVM + Clean Architecture** with three distinct layers:

```
UI Layer  ←→  Domain Layer  ←→  Data Layer
```

### Rules
- **UI layer** depends on domain models only — never on Room entities or Retrofit DTOs.
- **Domain layer** has **zero Android dependencies** — pure Kotlin classes and interfaces.
- **Data layer** implements domain interfaces and maps between entities/DTOs and domain models.
- ViewModels call **use cases** (if present) or **repository interfaces** directly.
- **Hilt** wires everything together at runtime.

### Data flow (unidirectional)

```
User action
    │
    ▼
ViewModel  ──(suspends / collects Flow)──►  Repository interface (domain)
                                                    │
                                                    ▼
                                            RepositoryImpl (data)
                                            ├── Room DAO (local)
                                            └── Retrofit service (remote)
    ▲
    │
UI State (StateFlow / collectAsState)
```

---

## Data Layer

### Room Database — `MonStockDatabase`

Located in `data/local/database/`. Key classes:

| Class | Role |
|-------|------|
| `MonStockDatabase` | `@Database` class, version tracked under `app/schemas/` |
| `PantryEntity` | Room entity for pantries |
| `ShelfEntity` | Room entity for shelves (FK → `PantryEntity`) |
| `ItemEntity` | Room entity for items (FK → `ShelfEntity`) |
| `BarcodeCacheEntity` | Caches Open Food Facts lookups offline |
| `PantryDao`, `ShelfDao`, `ItemDao`, `BarcodeDao` | DAOs exposing `Flow` and `suspend` functions |
| Type converters | Handle `LocalDate`, enums (`ItemCategory`, `ItemUnit`) |

**Schema migrations** are exported automatically to `app/schemas/`. Add a `Migration` object to `MonStockDatabase` whenever you change an entity.

### Networking — Open Food Facts

Located in `data/remote/api/`. The `OpenFoodFactsService` Retrofit interface fetches product data by barcode. Responses are cached in `BarcodeCacheEntity` to avoid redundant network calls.

### Repositories

Each aggregate has a repository interface in `domain/` and an implementation in `data/repository/`. The implementation merges local and remote sources and exposes `Flow<List<T>>` for reactive UI updates.

---

## Domain Layer

`domain/model/` contains plain Kotlin data classes:

- `Pantry(id, name)`
- `Shelf(id, pantryId, name, capacity)`
- `Item(id, shelfId, name, brand, category, quantity, unit, purchaseDate, expiryDate, minimumStockThreshold, barcode, photoUri, notes)`

These models are the **single source of truth** for the UI — never pass Room entities or DTOs to Composables.

> The project currently places repository interfaces directly in `data/repository/` as Kotlin interfaces co-located with their implementations. If the project grows, move interfaces to `domain/repository/` and use cases to `domain/usecase/`.

---

## UI Layer

### ViewModels

Every screen has its own ViewModel annotated with `@HiltViewModel`. ViewModels:
- Hold a `StateFlow<ScreenUiState>` (a sealed class or data class modelling the screen state).
- Launch coroutines in `viewModelScope`.
- Never reference Android `Context` directly — pass only what is needed through constructor injection or method parameters.

### Composable screens

Each screen is a top-level `@Composable` function that:
- Receives a `ViewModel` via `hiltViewModel()`.
- Collects state with `collectAsState()`.
- Calls `navController.navigate(...)` or a callback lambda for navigation events.

### Shared components

Reusable Composables live in `ui/components/`:

| Component | Usage |
|-----------|-------|
| `ItemCard` | Displays a single item in a list with expiry colour coding |
| `SummaryCard` | Summary tile used on the dashboard |
| `EmptyState` | Shown when a list has no items |
| `ConfirmDeleteDialog` | Generic delete confirmation dialog |

### Theming

`ui/theme/` follows the standard Material 3 setup:
- `Color.kt` — light and dark colour schemes.
- `Type.kt` — typography scale.
- `Theme.kt` — `MonStockTheme` wrapper that applies dynamic colour on API 31+.

Dark mode is supported and follows the system setting automatically.

---

## Dependency Injection (Hilt)

`MonStockApplication` is annotated with `@HiltAndroidApp`.

Two Hilt modules cover all dependencies:

| Module | Provides |
|--------|---------|
| `DatabaseModule` | `MonStockDatabase`, all DAOs, all repository implementations |
| `NetworkModule` | `OkHttpClient`, `Retrofit`, `OpenFoodFactsService` |

All repository interfaces are bound to their implementations via `@Binds` in `DatabaseModule`.

---

## Navigation

`ui/navigation/NavHost.kt` defines the single `NavHost` with all screen routes. Routes are defined as string constants (or a sealed class) to avoid typo-related issues.

**Pattern for adding a new screen:**
1. Add a route constant.
2. Add a `composable(route) { ... }` entry in `NavHost.kt`.
3. Create the Screen Composable and ViewModel under `ui/screens/<feature>/`.

---

## Notifications & Background Work

`notification/` contains all notification logic:

| Class | Role |
|-------|------|
| `NotificationHelper` | Creates notification channels and dispatches notifications |
| `NotificationPreferences` | Reads/writes notification settings via DataStore |
| `NotificationScheduler` | Schedules `PeriodicWorkRequest`s via WorkManager |
| `worker/` | Individual `CoroutineWorker` subclasses for expiry, low-stock, and daily digest |

WorkManager tasks are enqueued at app startup and re-enqueued on device reboot via a `BroadcastReceiver` (if present) or on next launch.

### Adding a new worker
1. Create a `CoroutineWorker` in `notification/worker/`.
2. Inject dependencies via `HiltWorker` + `@AssistedInject`.
3. Register the worker factory in `MonStockApplication` or the Hilt `WorkerModule`.
4. Add scheduling logic in `NotificationScheduler`.

---

## Barcode & Camera

### Scanning (CameraX + ML Kit)
`ui/screens/scanner/` hosts the camera preview using **CameraX** (`camera-camera2`, `camera-lifecycle`, `camera-view`). Barcode analysis is performed by **ML Kit Barcode Scanning** in an `ImageAnalysis.Analyzer`.

Supported formats: EAN-13, EAN-8, UPC-A, UPC-E, QR Code.

### Product lookup
Scanned barcodes are looked up in:
1. **Local cache** (`BarcodeCacheEntity` via `BarcodeDao`) — instant, offline.
2. **Open Food Facts API** — only if not cached. Result is stored in the cache.

### QR label generation
**ZXing Core** generates `BitMatrix` objects from item data, which are then rendered to `Bitmap` and shared as PDF/PNG.

---

## Build Variants

| Variant | App ID suffix | Minify | Logging |
|---------|--------------|--------|---------|
| `debug` | `.debug` | No | Enabled |
| `release` | *(none)* | Yes (R8 + ProGuard) | Disabled |

ProGuard rules are in `app/proguard-rules.pro`.

---

## Testing

```
app/src/
├── test/           # JVM unit tests (ViewModels, repositories, use cases)
└── androidTest/    # Instrumented tests (Room, UI with Compose testing)
```

### Unit tests
- Use `kotlinx-coroutines-test` and `TestCoroutineDispatcher` to test suspend functions and Flows.
- Mock repositories with `Mockito` or manual fakes.

### Instrumented tests
- Room DAOs are tested with an in-memory database.
- UI screens are tested with `ComposeTestRule` and semantic matchers.

### Running tests
```bash
# Unit tests
./gradlew test

# Instrumented tests (requires a connected device/emulator)
./gradlew connectedAndroidTest
```

---

## Adding a New Feature — Checklist

1. **Domain model** — add or update a data class in `domain/model/`.
2. **Room entity & DAO** — add entity in `data/local/database/`, update `MonStockDatabase` version, add a `Migration`.
3. **Repository** — add interface method, implement in `data/repository/`, bind in `DatabaseModule`.
4. **ViewModel** — create `@HiltViewModel` in `ui/screens/<feature>/`.
5. **Screen** — create `@Composable` screen, collect state from ViewModel.
6. **Navigation** — add route to `NavHost.kt`, add navigation entry points.
7. **Tests** — add unit tests for ViewModel and repository, instrumented tests for the DAO and screen.
8. **Specifications** — update `Specifications.md` implementation status table.

---

## Dependency Versions

All versions are declared in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

| Key | Version |
|-----|---------|
| `agp` | 8.7.3 |
| `kotlin` | 2.1.0 |
| `ksp` | 2.1.0-1.0.29 |
| `composeBom` | 2024.12.01 |
| `hilt` | 2.53.1 |
| `room` | 2.7.0 |
| `retrofit` | 2.11.0 |
| `okhttp` | 4.12.0 |
| `coroutines` | 1.9.0 |
| `camerax` | 1.4.1 |
| `mlkitBarcode` | 17.3.0 |
| `workManager` | 2.10.0 |

To upgrade a dependency, update the version in `libs.versions.toml` and run **Gradle Sync**.
