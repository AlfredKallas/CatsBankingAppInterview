# CatsBankingApp - Kotlin Multiplatform Interview Project

A modern, cross-platform banking application built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. This project demonstrates technical excellence in architecture, testing, and clean code practices.

## 🚀 AGP 9 Migration & Project Structure Update

This project has been migrated to Android Gradle Plugin 9.x, which introduces a new project structure and testing conventions.

### Key Changes:
- **`androidHostTest` Source Set**: Following the new convention, JVM-based tests (including Robolectric UI tests) are now located in `composeApp/src/androidHostTest/kotlin`. This replaces the previous `androidTest` folder for unit and instrumentation tests.
- **`iosTest` Source Set**: Shared tests between iOS and other platforms remain in `commonTest`.
- **Gradle Tasks**: The commands to run tests have been updated to reflect this new structure.

## 🏗️ Architecture

The project follows **Clean Architecture** principles, ensuring separation of concerns and high testability across platforms.

- **Data Layer**: Handles API communication (Ktor) and local persistence.
- **Domain Layer**: Contains the "Source of Truth" models, Business Logic (UseCases), and Mappers to transform raw data.
- **Presentation Layer**: Implements a hybrid **MVVM/MVI** pattern (Shared logic, platform-specific ViewModels).
- **Dependency Injection**: Powered by **Koin**, ensuring modularity and easy mocking for tests.
- **Reactive State**: Driven by **Kotlin Flows**, unified with **Turbine** for testing.

## 🛠️ Tech Stack

This project leverages a modern and robust set of libraries to provide a high-quality multiplatform experience.

### Core Libraries
- **Kotlin**: v2.3.0
- **Ktor** (Networking): `client-core`, `client-okhttp`, `client-darwin`, `client-logging`, `client-auth`, `client-content-negotiation`, `serialization-kotlinx-json`.
- **Koin** (Dependency Injection): `koin-bom`, `koin-core`, `koin-android`, `koin-compose`, `koin-compose-viewmodel`.
- **Kotlinx Serialization**: JSON-based data parsing.
- **Kotlinx Datetime**: Reliable cross-platform time handling.

### UI & Navigation
- **Compose Multiplatform**: `foundation`, `runtime`, `ui`, `material3`, `material-icons-extended`, `components-resources`.
- **Navigation**: `navigation-compose`, `navigation-event-compose`.

### Testing & Quality
- **Unit Testing**: `kotlin-test`, `kotlinx-coroutines-test`.
- **Mocking & DI Testing**: `koin-test`, `ktor-client-mock`.
- **Async Testing**: `app.cash.turbine:turbine` (Unlocking reliable Flow assertions).
- **JVM UI Testing**: `org.robolectric:robolectric`.
- **Coverage**: `org.jetbrains.kotlinx.kover` (Refined filters for core logic).

### Android Specifics
- **androidx-activity-compose**
- **androidx-lifecycle-runtime-compose**
- **androidx-appcompat**
- **androidx-core-ktx**

## 🌍 Environments & Build Variants

The project uses **Android Product Flavors** and **Build Types** to manage different environments conceptually:

### 🎨 Flavors
- **`dev`**: Targeted at development and QA. Uses a dedicated test Firebase RTDB.
- **`prod`**: Targeted at real users. Uses the production Firebase environment.

### 🏗️ Build Types
- **`debug`**: Optimized for development. Includes full logging, is debuggable, and skips code shrinking (R8) for faster builds.
- **`release`**: Optimized for performance. Enables R8 (obfuscation and shrinking) to reduce binary size and harden the code.

### 🧩 Combined Variants
- **`devDebug`** (Recommended): The primary variant for daily development and automated testing.
- **`prodRelease`**: The final artifact for distribution.

> [!NOTE]
> While both current flavors point to the same test URL for this interview demo, the architecture is fully prepared to swap configurations via `BuildConfig.BASE_URL`.

## 📦 Organization

```text
com.example.catsbankingapp
├── core          # Networking, Local Storage, Exceptions
├── data          # Repositories & DTOs
├── domain        # UseCases, Domain Models & Mappers
├── presentation  # Screens, ViewModels, Presenters & UI Models
└── utils         # Shared helpers (Date, String, Flow extensions)
```

## 💉 Dependency Injection (Koin)

The project leverages **Koin** for lightweight and idiomatic dependency injection across the multiplatform codebase:
- **Modular Design**: Each feature (Accounts, Operations) has its own Koin module.
- **Testable**: Koin allows for easy swapping of real implementations with mocks (e.g., `FakeAccountsPresenter`) during unit and UI testing.
- **Lifecycle Awareness**: ViewModels are injected using `koin-compose-viewmodel`, ensuring they follow the platform's lifecycle (especially on Android).

## 🚦 UI State Management & UX

The app uses a robust **State Machine** to manage the user experience across all screens:

- **Loading State**: Displays a tailored loading indicator or shimmer while data is being fetched.
- **Success State**: Renders the mapped domain data into a platform-agnostic UI model.
- **Error State**: Shows a user-friendly error screen with specific feedback and a **Retry** action.

### 🔄 Retry Mechanism
When an error occurs, the UI offers a "Retry" button. This triggers a specific event (e.g., `OnRetryClicked`) through the Presenter, which re-executes the data fetching Flow, providing a seamless way for users to recover from transient network issues.

## 🛡️ Error Handling & Exception Propagation

The project implements a centralized and type-safe exception handling system:

1. **Network Layer**: `NetworkClient` catches raw Ktor/Serialization exceptions and converts them into domain-specific **`CatsBankingException`** types (e.g., `NotFoundException`, `UnauthorizedException`).
2. **Result Wrapper**: Errors are propagated up through the Repository and UseCase layers as encapsulated **`Result<T>`** objects.
3. **Presenter Layer**: The Presenter handles the `onFailure` block, extracting the formatted message from the exception to update the UI State.
4. **UI Layer**: The Composable Screen observes the `Error` state and displays the relevant message to the user.

## 💾 Caching Strategy

The app implements a **Local First** synchronization strategy in the `BanksListRepository`:
1. Check **Local Data Source** for cached results.
2. If cache hit: Return local data immediately.
3. If cache miss: Fetch from **Network**, update the local cache, and emit the result.

This ensures a smooth offline experience and reduced network overhead.

## 🛠️ Utils & Helpers

- **StringProvider**: Multiplatform abstraction for localized string resources.
- **Date Utilities**: `DateTimeParser` and `DateFormatter` for reliable cross-platform time handling.
- **FlowExt**: Custom extensions for handling `Result` types within Kotlin Flows.

## 🧪 Testing Strategy

The project features a comprehensive test suite (Unit + UI) shared in `commonTest` and `androidHostTest`.

### 1. Unit Tests
Thoroughly covers:
- **Mappers**: Ensuring perfect data transformation.
- **UseCases**: Validating business rules.
- **Presenters/ViewModels**: Testing state transitions and event handling using **Turbine** to assert on reactive Flow emissions.

### 2. UI Tests (Compose Multiplatform)
Shared UI tests verify the rendering of:
- Loading states (shimmers/progress).
- Success states (mocked data).
- Error states (user-friendly messages).

### 🚀 How to Run Tests

| Platform | Command |
| :--- | :--- |
| **Android (Unit + UI Runtime)** | `./gradlew :composeApp:testAndroidHostTest` |
| **iOS (Unit + UI Native)** | `./gradlew :composeApp:iosSimulatorArm64Test` |
| **Coverage Report** | `./gradlew :composeApp:koverHtmlReportDevDebug` |

> [!IMPORTANT]
> **Android Instrumented Tests**: Avoid running `./gradlew connectedDevDebugAndroidTest` on an emulator. There is a known conflict between the Robolectric shadowing framework (used for high-speed JVM UI testing) and the real Android environment. Use the JVM task for 100% verification on Android.

## 📱 iOS Orientation Troubleshooting

If the iOS app appears locked in Portrait mode:
1. **Standardized Settings**: I've enabled `GENERATE_INFOPLIST_FILE` across all build configurations to ensure consistent behavior.
2. **Double-Checked Plist**: Orientation keys are explicitly added to `iosApp/Info.plist` as a secondary safeguard.
3. **CRITICAL: Clean Build**: iOS configuration can be extremely sticky. If rotation still fails:
    - **Delete the app** from the iOS Simulator.
    - Run `Build > Clean Project` in Android Studio.
    - Run the app again.

*Note: This is not a "known issue" in KMP itself; KMP-Compose supports rotation natively. It is a common configuration hurdle where Xcode's build settings and the physical `Info.plist` must be perfectly synchronized.*

## 📊 Test Coverage (Kover)

Test coverage is measured using **Kotlinx Kover**. You can generate a detailed HTML report to see exactly which lines are covered:

1. Run: `./gradlew :composeApp:koverHtmlReportDevDebug`
2. Open: `composeApp/build/reports/kover/htmlDevDebug/index.html`