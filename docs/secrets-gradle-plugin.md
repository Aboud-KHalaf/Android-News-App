# Securing API Keys with Secrets Gradle Plugin

## Why Do We Need This?

Hardcoding API keys directly in Kotlin code or XML files is a security risk.

Anyone with access to the repository can read them.

Even if the repository is private, keys can be leaked through:

- Git history
- Decompiled APKs
- Shared screenshots

The **Secrets Gradle Plugin** solves this by:

- Storing keys outside the source code
- Never committing keys to Git
- Injecting keys safely at build time

---

## Step 1: Add the Key to `local.properties`

The file `local.properties` is already ignored by Git (check `.gitignore`).

Open `local.properties` in the project root and add:

```properties
MAPS_API_KEY=your_actual_api_key_here
```

Replace `your_actual_api_key_here` with your real API key.

> This file is never committed to version control.

---

## Step 2: Register the Plugin in the Version Catalog

Open `gradle/libs.versions.toml`.

Add the plugin version under `[versions]`:

```toml
[versions]
secrets = "2.0.1"
```

Then declare the plugin under `[plugins]`:

```toml
[plugins]
secrets = { id = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin", version.ref = "secrets" }
```

---

## Step 3: Apply the Plugin in `build.gradle.kts` Files

### Root `build.gradle.kts`

Add the plugin with `apply false` (makes it available to subprojects):

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.secrets) apply false
}
```

### App-level `app/build.gradle.kts`

Apply the plugin (activates it for the app module):

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets)
}
```

---

## Step 4: Access the Key in Kotlin

After syncing the project, the plugin automatically generates a `BuildConfig` class.

Access the key in any Kotlin file:

```kotlin
val apiKey = BuildConfig.MAPS_API_KEY
```

The property name matches the key name defined in `local.properties`.

---

## How It Works

```text
Developer adds key to local.properties
                  ↓
Gradle reads local.properties at build time
                  ↓
Secrets plugin generates BuildConfig with the key
                  ↓
Kotlin code accesses BuildConfig.MAPS_API_KEY
                  ↓
Key is available in the app, but never committed to Git
```

---

## Important Notes

- `local.properties` is listed in `.gitignore` — confirm it is never committed.
- The same approach works for any key (e.g., `NEWS_API_KEY`, `CLIENT_SECRET`).
- Each developer must add their own keys to their local `local.properties`.
- For CI/CD pipelines, inject the same properties via environment variables.

---

## Comparison with Other Approaches

| Approach               | Security Level | Risk                          |
| ---------------------- | -------------- | ----------------------------- |
| Hardcoded in source    | None           | Visible in Git and APK        |
| `BuildConfig` (manual) | Low            | Still visible in Git history  |
| `local.properties`     | High           | Never committed               |
| Environment variables  | Higher         | Requires CI/CD configuration  |
| Backend proxy          | Highest        | Key never reaches the device  |

---

## Key Takeaways

- Never hardcode API keys in source files.
- Use `local.properties` to store keys locally.
- The Secrets Gradle Plugin exposes keys via `BuildConfig` at build time.
- Always verify that `local.properties` is in `.gitignore`.
