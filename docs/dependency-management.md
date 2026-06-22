# Dependency Management in Android (Gradle) vs Flutter (pubspec.yaml)

# Why Do We Need Dependencies?

Modern applications rarely use only the standard libraries provided by the framework.

Instead, we add external libraries to gain additional functionality such as:

* Networking
* Local databases
* Dependency Injection
* Image loading
* Pagination
* Analytics
* Testing

These external libraries are called **Dependencies**.

---

# Flutter Approach

In Flutter, adding a package is very straightforward.

Example:

```yaml
dependencies:
  dio: ^5.8.0
  flutter_bloc: ^9.1.0
```

After saving:

```bash
flutter pub get
```

Flutter downloads the packages and makes them available to the project.

You usually manage everything from a single file:

```text
pubspec.yaml
```

---

# Android Approach

Android uses a build system called **Gradle**.

Gradle is much more powerful than Flutter's package system because it does more than install libraries.

Gradle is responsible for:

* Dependency management
* Building APKs
* Running tests
* Code generation
* Signing applications
* Managing build variants
* Multi-module projects

Because of this, dependency management is slightly more complex than Flutter.

---

# Version Catalogs (Modern Android)

Modern Android projects often use:

```text
gradle/libs.versions.toml
```

instead of writing versions directly inside Gradle files.

This file acts as a central dependency registry.

Think of it as Android's equivalent of:

```text
pubspec.yaml
```

although it is much more powerful.

---

# Example

Suppose we want to use Retrofit.

Inside:

```text
gradle/libs.versions.toml
```

we add:

```toml
[versions]
retrofit = "3.0.0"

[libraries]
retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
```

Now the project knows:

* Package name
* Package version

---

# Installing The Dependency

Inside:

```text
app/build.gradle.kts
```

we add:

```kotlin
dependencies {
    implementation(libs.retrofit)
}
```

This tells Gradle:

> Download Retrofit and make it available to the app module.

---

# Flutter Comparison

Flutter:

```yaml
dependencies:
  dio: ^5.8.0
```

Android:

```kotlin
dependencies {
    implementation(libs.retrofit)
}
```

Same idea.

Different syntax.

---

# What Is "implementation"?

You will frequently see:

```kotlin
implementation(...)
```

This is the most common dependency type.

It means:

> This library is available inside the current module.

Example:

```kotlin
implementation(libs.retrofit)
implementation(libs.room.runtime)
implementation(libs.coil.compose)
```

Most dependencies use `implementation`.

---

# Common Dependency Types

## implementation

```kotlin
implementation(libs.retrofit)
```

Normal dependency.

Used most of the time.

---

## testImplementation

```kotlin
testImplementation(libs.junit)
```

Available only during unit testing.

Not included in the final APK.

---

## androidTestImplementation

```kotlin
androidTestImplementation(libs.espresso)
```

Used for Android UI tests.

Not included in production builds.

---

## debugImplementation

```kotlin
debugImplementation(libs.leakcanary)
```

Available only in debug builds.

Automatically removed from release builds.

---

## ksp

```kotlin
ksp(libs.hilt.compiler)
```

Used by code-generation libraries.

Examples:

* Hilt
* Room
* Moshi
* Compose Destinations

Flutter developers can think of this as something similar to:

```bash
build_runner
```

which generates code automatically.

---

# Why Do We Have Two Hilt Dependencies?

Example:

```kotlin
implementation(libs.hilt.android)
ksp(libs.hilt.compiler)
```

The first dependency contains the Hilt framework itself.

The second dependency generates the required boilerplate code behind the scenes.

Without the compiler dependency, Hilt cannot generate the code it needs.

---

# Why Does Android Feel More Complex?

Flutter hides much of the build system.

Android exposes it.

This gives Android developers:

* More flexibility
* More control
* Better scalability

but also introduces additional complexity.

---

# Dependency Resolution Process

When Android Studio builds the project:

```text
Developer adds dependency
            ↓
Gradle reads build.gradle.kts
            ↓
Gradle reads libs.versions.toml
            ↓
Gradle downloads libraries
            ↓
Gradle resolves transitive dependencies
            ↓
Project compiles
```

---

# Transitive Dependencies

Suppose:

```kotlin
implementation(libs.retrofit)
```

Retrofit itself depends on other libraries.

Gradle automatically downloads those libraries as well.

This process is called:

**Transitive Dependency Resolution**

Flutter performs something similar internally.

---

# Typical News App Dependencies

For this project we will eventually add:

```kotlin
implementation(libs.androidx.navigation.compose)

implementation(libs.hilt.android)
ksp(libs.hilt.compiler)

implementation(libs.retrofit)
implementation(libs.retrofit.gson)

implementation(libs.room.runtime)
implementation(libs.room.ktx)
ksp(libs.room.compiler)

implementation(libs.paging.runtime)
implementation(libs.paging.compose)

implementation(libs.coil.compose)

implementation(libs.androidx.datastore)
```

---

# Flutter vs Android Summary

| Flutter            | Android            |
| ------------------ | ------------------ |
| pubspec.yaml       | libs.versions.toml |
| flutter pub get    | Gradle Sync        |
| Package            | Dependency         |
| build_runner       | KSP                |
| dio                | Retrofit           |
| hive / drift       | Room               |
| shared_preferences | DataStore          |
| go_router          | Navigation Compose |
| get_it             | Hilt               |

---

# Key Takeaways

* Flutter manages packages through `pubspec.yaml`.
* Android manages dependencies through Gradle.
* Modern Android projects centralize versions in `libs.versions.toml`.
* Most libraries are added using `implementation(...)`.
* Code-generation libraries often require `ksp(...)`.
* Gradle is more complex than Flutter's package manager because it is a complete build system, not just a package installer.
