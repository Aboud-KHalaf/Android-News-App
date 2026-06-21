# MainActivity.kt

## What is MainActivity?

In modern Android development with Jetpack Compose, `MainActivity` is the entry point of your application's user interface.

Unlike traditional Android development where you might have many Activities, Compose apps typically use a **Single Activity Architecture**. This single Activity serves as the host for all your Compose screens and navigation.

---

# Class Declaration

```kotlin
class MainActivity : ComponentActivity() { ... }
```

### ComponentActivity
`MainActivity` inherits from `ComponentActivity`. This is a base class that provides:
* Lifecycle management.
* Support for Compose via `setContent`.
* Modern Android features while remaining lightweight.

---

# The onCreate Method

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
        NewsItTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
```

### super.onCreate(savedInstanceState)
Calls the parent class implementation to perform standard activity setup.

### enableEdgeToEdge()
A modern Android feature that allows the app to draw behind the system bars (status bar and navigation bar), providing a truly immersive experience.

### setContent { ... }
This is the most critical part of a Compose Activity. It defines the root Composable of your UI. This replaces the old `setContentView(R.layout.activity_main)` used in XML-based layouts.

---

# Inside setContent

### NewsItTheme { ... }
This is a wrapper Composable that applies your app's branding (colors, typography, shapes). It ensures consistency across all UI components.

### Scaffold
A high-level layout Composable that provides a standard structure for Material Design screens. It handles common UI elements like:
* Top App Bars
* Bottom Navigation
* Floating Action Buttons
* **Padding management** (using `innerPadding` to avoid overlapping with system UI or Scaffold elements).

---

# Composables

```kotlin
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
```

Composables are the building blocks of Compose UI.
* **@Composable**: An annotation that tells the compiler this function is for building UI.
* **Functions, not Classes**: UI components are written as functions, making them highly reusable and easy to test.

---

# Previews

```kotlin
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsItTheme {
        Greeting("Android")
    }
}
```

### @Preview
Allows you to see your UI components directly inside Android Studio without running the app on a physical device or emulator. It speeds up the development loop significantly.

---

# Flutter Comparison

| Feature | Android (Compose) | Flutter |
| :--- | :--- | :--- |
| Entry Point | `MainActivity` | `main()` function |
| UI Container | `setContent { }` | `runApp(MyApp())` |
| UI Building Block | `Composable` function | `Widget` class |
| Theme | `Theme` Composable | `ThemeData` |
| Screen Structure | `Scaffold` | `Scaffold` |
| Root Component | `ComponentActivity` | `FlutterActivity` (Internal) |

---

# Summary

* `MainActivity` is the bridge between the Android OS and your Compose UI.
* `setContent` is where your UI starts.
* `Scaffold` provides the basic layout structure.
* UI is built using reusable `@Composable` functions.
* Previews allow for fast UI iteration.
