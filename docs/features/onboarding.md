# Onboarding Feature

## Overview
A 4-screen onboarding flow that introduces new users to the NewsIt app. Built with Jetpack Compose following Clean Architecture + MVVM.

## Architecture Decisions

### Clean Architecture Layers
The feature is split across three layers to enforce separation of concerns:

- **`domain/`** — Contains business logic only (models, repository interfaces, use cases). No Android framework dependencies.
- **`data/`** — Implements the repository contract. Currently uses mock data; can be swapped with a real backend/data source without touching other layers.
- **`presentation/`** — Jetpack Compose UI + ViewModel. Follows MVVM with StateFlow-based state management.

### Why not single-layer?
Keeping the domain layer independent means:
- Use cases and models can be unit-tested without Android instrumentation.
- The repository interface allows swapping data sources (mock -> API -> cache) via DI without changing the ViewModel or UI.
- Future features follow the same structure, making the codebase consistent and predictable.

## Folder Structure
```
com.example.newsit/
├── data/
│   └── onboarding/
│       └── OnboardingRepositoryImpl.kt    # Mock implementation
├── domain/
│   └── onboarding/
│       ├── model/
│       │   └── OnboardingPage.kt          # Data class (title, desc, icon)
│       ├── repository/
│       │   └── OnboardingRepository.kt    # Interface contract
│       └── usecase/
│           └── GetOnboardingPagesUseCase.kt
└── presentation/
    ├── navigation/
    │   ├── AppNavigation.kt              # NavHost with routes
    │   └── HomePlaceholder.kt            # Temporary home screen
    └── onboarding/
        ├── OnboardingViewModel.kt        # State management
        └── OnboardingScreen.kt           # Compose UI
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

## Navigation Approach

**Chosen: Jetpack Navigation Compose (`NavHost` + `composable()` routes)**

The `OnboardingScreen` receives a lambda `onComplete: () -> Unit` rather than a `NavController` reference. This keeps the screen reusable and testable — navigation logic lives at the `NavHost` level in `AppNavigation.kt`, not in the ViewModel or UI.

```
AppNavigation
├── "onboarding" → OnboardingScreen(onComplete → navigate("home"))
└── "home"       → HomePlaceholder (future: news feed)
```

Inside the onboarding screen, `HorizontalPager` handles swipe between the 4 pages, while Next/Back/Skip buttons provide alternative navigation. The ViewModel tracks the current page index; the Pager's scroll position is synced via `rememberCoroutineScope`.

## Why This Design Is Scalable

### 1. Repository Pattern
The `OnboardingRepository` interface in domain is implemented by `OnboardingRepositoryImpl` in data. To connect real data:
- Swap the implementation in DI (Hilt/Koin).
- The ViewModel and UI never change.

### 2. Stateless UI
All UI state derives from `OnboardingUiState` exposed by the ViewModel. Screens are pure functions of state — no business logic leaks into composables.

### 3. Feature Isolation
The entire onboarding module lives under `onboarding/` packages. Adding a new feature (e.g., `auth/`, `home/`, `search/`) follows the same structure without risk of collisions.

### 4. Navigation Extensibility
Routes are centralized in `AppNavigation.kt`. Adding a new screen is a single `composable("route") { ... }` block. The `onComplete` callback pattern means screens don't need to know about the navigation graph.

### 5. Testability
- ViewModel: inject mock use case → verify state flow.
- UI: snapshot/preview composables with sample state.
- Use cases: pure Kotlin → fast unit tests.
- Navigation: test independently by passing mock callbacks.

## Future Improvements
- Add DataStore to persist onboarding completion so the flow only shows once.
- Add Lottie animations or illustrations for each page.
- Connect to a CMS for dynamic onboarding content.
- Add accessibility (content descriptions, font scaling).
