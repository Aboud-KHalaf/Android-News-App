# Android News App — Learning Android Development with Kotlin & Jetpack Compose

## About This Repository

This repository is not only about building a News App.

It is my personal journey of learning modern Android development after working with Flutter.

The main goal is to understand how Android Native applications are built using Kotlin and Jetpack Compose while documenting everything I learn along the way.

Instead of simply finishing an application, I want to deeply understand:

* Android Architecture
* State Management
* Dependency Injection
* Local Caching
* Offline-First Concepts
* Pagination
* Networking
* Clean Architecture Principles
* Testing Strategies

Throughout this project, I will compare Android Native concepts with their Flutter equivalents whenever possible.

---

## Project Goal

Build a production-ready News Application using modern Android development practices while documenting:

1. What each technology does.
2. Why it is used.
3. Alternative approaches.
4. Comparison with Flutter.
5. Real-world implementation examples.

---

# Tech Stack

## UI

* Jetpack Compose
* Material 3
* Navigation Compose

### What I Want to Learn

* Declarative UI
* Recomposition
* State Hoisting
* Navigation
* Side Effects

### Flutter Comparison

| Android              | Flutter              |
| -------------------- | -------------------- |
| Jetpack Compose      | Flutter Widgets      |
| Composable Functions | Widget Classes       |
| State Hoisting       | Lifting State Up     |
| Navigation Compose   | GoRouter / Navigator |

---

## Architecture

### Clean Architecture

Project layers:

```
presentation
domain
data
```

### Learning Goals

Understand:

* Separation of concerns
* Dependency rule
* Use Cases
* Repository pattern
* Scalability

### Flutter Comparison

The architecture is very similar to:

```
presentation
domain
data
```

commonly used in Flutter projects following Clean Architecture.

---

## Presentation Layer

### MVVM

Responsibilities:

* Expose UI state
* Handle UI events
* Communicate with domain layer

Components:

* ViewModel
* UiState
* UiEvent

### Flutter Comparison

| Android   | Flutter                |
| --------- | ---------------------- |
| ViewModel | Cubit / StateNotifier  |
| StateFlow | Stream / StateNotifier |
| UI State  | State Class            |

---

## MVI Pattern

### Why MVI?

Provides:

* Predictable state management
* Single source of truth
* Easier debugging

### Core Concepts

#### Intent

Actions sent from UI.

Examples:

* Refresh
* Search
* Load More

#### State

Current screen state.

Examples:

* Loading
* Success
* Error

#### Effect

One-time events.

Examples:

* Show Toast
* Navigate
* Snackbar

### Flutter Comparison

Very similar to:

* Bloc
* Riverpod State Notifier
* Redux

---

## Networking

### Retrofit

Used for:

* REST API communication
* Request handling
* Response mapping

### Learning Goals

* API integration
* DTOs
* Serialization
* Error handling
* Interceptors

### Flutter Comparison

| Android  | Flutter          |
| -------- | ---------------- |
| Retrofit | Dio              |
| OkHttp   | Dio Interceptors |
| DTO      | Model            |

---

## Local Database

### Room

Used for:

* Offline caching
* Persistent storage

### Learning Goals

* Entities
* DAO
* Queries
* Relationships
* Migrations

### Flutter Comparison

| Android | Flutter                |
| ------- | ---------------------- |
| Room    | Drift / Hive / Sqflite |

---

## Preferences Storage

### DataStore Preferences

Used for:

* Theme settings
* User preferences
* Lightweight local data

### Learning Goals

* Reactive preferences
* Flow integration
* Persistence

### Flutter Comparison

| Android   | Flutter           |
| --------- | ----------------- |
| DataStore | SharedPreferences |

---

## Dependency Injection

### Dagger Hilt

Used for:

* Dependency management
* Object creation
* Lifecycle-aware injection

### Learning Goals

* Modules
* Providers
* Scopes
* Singleton pattern

### Flutter Comparison

| Android | Flutter              |
| ------- | -------------------- |
| Hilt    | GetIt                |
| Modules | Service Registration |

---

## Pagination

### Paging 3

Used for:

* Infinite scrolling
* Efficient data loading

### Learning Goals

* PagingSource
* RemoteMediator
* LoadState
* Offline-first pagination

### Flutter Comparison

Flutter does not have a direct official equivalent.

Common alternatives:

* infinite_scroll_pagination
* Custom pagination logic

---

## Reactive Programming

### Kotlin Flow

Used heavily across the project.

Learning topics:

* Flow
* StateFlow
* SharedFlow
* Flow Operators

### Flutter Comparison

| Android    | Flutter          |
| ---------- | ---------------- |
| Flow       | Stream           |
| StateFlow  | StateNotifier    |
| SharedFlow | Broadcast Stream |

---

## Offline-First Strategy

Planned workflow:

```
UI
 ↓
ViewModel
 ↓
Repository
 ↓
Local Database (Room)
 ↓
Network (Retrofit)
```

Goals:

* Faster loading
* Better user experience
* Network resilience

---


# Learning Notes

This repository will contain notes and explanations about:

* Why a specific solution was chosen.
* Trade-offs.
* Android concepts.
* Architecture decisions.
* Comparison with Flutter.

The goal is to create a resource that future me can revisit when building Android applications.

---

# What I Hope To Gain

By the end of this project, I aim to:

* Build Android applications confidently.
* Understand Jetpack Compose deeply.
* Understand modern Android architecture.
* Learn how Android differs from Flutter.
* Create a documented learning resource for future projects.

---

# Disclaimer

This project is primarily a learning-focused repository.

The code, documentation, architecture decisions, and notes are intended to demonstrate the learning process rather than provide a perfect production implementation.
