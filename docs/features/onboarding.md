# Onboarding Feature

## Overview
A 4-screen onboarding flow that introduces new users to the NewsIt app. Built with Jetpack Compose following Clean Architecture + MVVM. Now includes local storage persistence to remember user completion state and skip the flow on subsequent app launches.

## Key Features
- Local storage persistence to show onboarding only once per device
- Continue from last page when returning to the app
- Skip entire onboarding flow when already completed

## Architecture Decisions

### Clean Architecture Layers
The feature is split across three layers to enforce separation of concerns:

- **`domain/`** — Contains business logic only (models, repository interfaces, use cases). No Android framework dependencies.
- **`data/`** — Implements the repository contract. Uses SharedPreferences to persist onboarding completion state.
- **`presentation/`** — Jetpack Compose UI + ViewModel. Follows MVVM with StateFlow-based state management.

### Why Single Layer?
Keeping the domain layer independent means:
- Use cases and models can be unit-tested without Android instrumentation.
- The repository interface allows swapping data sources (mock -> API -> cache) via DI without changing the ViewModel or UI.
- Future features follow the same structure, making the codebase consistent and predictable.

## Folder Structure
```
com.example.newsit/
├── NewsItApplication.kt                 # @HiltAndroidApp entry point
├── di/
│   └── RepositoryModule.kt              # Hilt bindings (interface → impl)
├── data/
│   └── onboarding/
│       └── OnboardingRepositoryImpl.kt  # Mock implementation
├── domain/
│   └── onboarding/
│       ├── model/
│       │   └── OnboardingPage.kt        # Data class (title, desc, icon)
│       ├── repository/
│       │   └── OnboardingRepository.kt  # Interface contract
│       └── usecase/
│           ├── GetOnboardingPagesUseCase.kt
│           └── CheckOnboardingCompletionUseCase.kt
└── presentation/
    ├── navigation/
    │   ├── AppNavigation.kt             # NavHost with routes
    │   └── HomePlaceholder.kt           # Temporary home screen
    └── onboarding/
        ├── OnboardingViewModel.kt       # @HiltViewModel state management
        ├── OnboardingScreen.kt          # Compose UI with pager + buttons
        ├── BottomButtons.kt             # Reusable button component
        ├── PageIndicator.kt             # Page dot indicator
        └── OnboardingPageContent.kt     # Individual page content
```

## State Management Choice

**Chosen: `StateFlow` in ViewModel**

Alternatives considered:
- **`MutableState` in ViewModel** — Simpler, but loses the lifecycle-awareness of Flow and is harder to test with `StateFlow`.
- **`LiveData`** — Works but is less idiomatic in Compose (requires `observeAsState()` instead of `collectAsState()`). `StateFlow` is the Kotlin-native equivalent with better coroutine integration.
- **MVI with `SharedFlow` events** — Overkill for a 4-screen flow; adds ceremony without benefit at this scale.

`StateFlow` provides:
- Cold-start safety (new collectors get the latest state immediately).
- Simple `update {}` for atomic state mutations.
- Straightforward testing via `viewModel.state.test {}`.

### Lifecycle-Aware Collection
State is collected with `collectAsStateWithLifecycle()` (from `lifecycle-runtime-compose`) instead of `collectAsState()`. This ensures the flow subscription is paused when the app goes to the background, saving resources and preventing unnecessary recompositions.

## Navigation Approach

**Chosen: Jetpack Navigation Compose (`NavHost` + `composable()` routes)**

The `OnboardingScreen` receives a lambda `onComplete: () -> Unit` rather than a `NavController` reference. This keeps the screen reusable and testable — navigation logic lives at the `NavHost` level in `AppNavigation.kt`, not in the ViewModel or UI.

```
AppNavigation
├── "onboarding" → OnboardingScreen(onComplete → navigate("home"))
└── "home"       → HomePlaceholder (future: news feed)
```

Inside the onboarding screen, `HorizontalPager` handles swipe between the 4 pages (`userScrollEnabled = true`), while Next/Back/Skip buttons provide alternative navigation. A `LaunchedEffect(pagerState.currentPage)` syncs the pager's scroll position back to the ViewModel whenever the user swipes — this prevents the PageIndicator and buttons from going out of sync with the visible page.

Button-based navigation (Next/Back/Skip) animates the pager via `animateScrollToPage()`, which also triggers the same `LaunchedEffect`, keeping a single source of truth for the current page.

## Why This Design Is Scalable

### 1. Dependency Injection (Hilt)
The project uses **Hilt** for dependency injection with KSP annotation processing. The `OnboardingViewModel` is annotated with `@HiltViewModel` and receives its use case via `@Inject constructor`. A `RepositoryModule` in `di/` binds `OnboardingRepository` to `OnboardingRepositoryImpl`, keeping the presentation layer decoupled from the data layer.

Three key Hilt components:
- `NewsItApplication` (`@HiltAndroidApp`) — application-level DI container
- `MainActivity` (`@AndroidEntryPoint`) — activity-level injection
- `OnboardingViewModel` (`@HiltViewModel`) — scoped ViewModel with injected use case

### 2. Repository Pattern
The `OnboardingRepository` interface in domain is implemented by `OnboardingRepositoryImpl` in data. To connect real data:
- Swap the implementation inside `RepositoryModule` (no changes to ViewModel or UI).
- The ViewModel and UI never change.

### 3. Stateless UI
All UI state derives from `OnboardingUiState` exposed by the ViewModel. Screens are pure functions of state — no business logic leaks into composables.

### 4. Feature Isolation
The entire onboarding module lives under `onboarding/` packages. Adding a new feature (e.g., `auth/`, `home/`, `search/`) follows the same structure without risk of collisions.

### 5. Navigation Extensibility
Routes are centralized in `AppNavigation.kt`. Adding a new screen is a single `composable("route") { ... }` block. The `onComplete` callback pattern means screens don't need to know about the navigation graph.

### 6. Testability
- ViewModel: inject mock use case → verify state flow.
- UI: snapshot/preview composables with sample state.
- Use cases: pure Kotlin → fast unit tests.
- Navigation: test independently by passing mock callbacks.

## Future Improvements
- Add DataStore to persist onboarding completion so the flow only shows once.
- Add Lottie animations or illustrations for each page.
- Connect to a CMS for dynamic onboarding content.
- Add accessibility (content descriptions, font scaling).
