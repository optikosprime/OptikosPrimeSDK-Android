# Installation

## Add the SDK

Add the SDK repository to your existing `dependencyResolutionManagement.repositories` in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        exclusiveContent {
            forRepository {
                ivy {
                    name = "optikosPrimeReleases"
                    url = uri("https://github.com/optikosprime/OptikosPrimeSDK-Android/releases/download")
                    patternLayout {
                        artifact("v[revision]/[artifact]-[revision](-[classifier]).[ext]")
                    }
                    metadataSources { gradleMetadata() }
                }
            }
            filter { includeModule("com.optikosprime", "sdk") }
        }
        google()
        mavenCentral()
    }
}
```

Add the dependency in your app's `build.gradle.kts` and sync:

```kotlin
dependencies {
    implementation("com.optikosprime:sdk:0.1.0-beta.2")
}
```

Use an exact published version. Keep the dependency without `@aar` so Gradle also resolves its dependencies. To update, change the version and sync again.

## Android configuration

Use Android 11/API 30+, compile SDK 36+, Java 17, and a toolchain compatible with Kotlin 2.2 metadata. Keep the bundled face model uncompressed:

```kotlin
android {
    compileSdk = 36
    defaultConfig { minSdk = 30 }
    androidResources { noCompress += "task" }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
```

For Kotlin hosts, also target Java 17:

```kotlin
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
```

## Initialize and launch

You need an SDK access license matching your app's final `applicationId` and development or production environment.

```kotlin
import com.optikosprime.sdk.*

// Initialize before launching a vision check.
OptikosPrimeSDK.initialize(
    applicationContext,
    licenseKey,
    OptikosPrimeEnvironment.Development,
)

// Register during Activity creation.
val visionCheck = registerForActivityResult(OptikosPrimeContract()) { outcome ->
    // Handle outcome.status, outcome.result, and outcome.error.
}

// Use the user's actual age-group answer.
visionCheck.launch(OptikosPrimeConfiguration(isOlderThan43 = isOlderThan43))
```

The callback reports completion, cancellation, or failure. Initialization can throw `OptikosPrimeException` for an invalid access license.

The SDK declares camera and internet permissions and requests camera access when capture begins. Use a supported physical phone; Android version support alone does not guarantee camera support. Call `OptikosPrimeSDK.getCameraInfo()` from a coroutine to check support.

See [SampleApp](../SampleApp/README.md) for a complete integration with camera support checks, themes, and result handling.
