# Optikos Prime Android sample

Demonstrates camera support checks, a full vision check, light and dark themes, and result handling. Gradle downloads the SDK binary automatically.

## Run

Use JDK 17, Android SDK 36, and a supported physical Android 11/API 30+ phone with internet access.

1. Clone this repository.
2. Open `SampleApp` in Android Studio and allow Gradle to sync.
3. Select `app` and run it on the phone.

To build the debug APK from this folder:

```sh
./gradlew :app:assembleDebug
```

On Windows, use `gradlew.bat :app:assembleDebug`.

The app offers **Check camera support** and **Start full vision check**, then displays completion, cancellation, or error details. Android version support alone does not guarantee camera support.

## Configuration

`sdkVersion` in `gradle.properties` selects the SDK version. To update, choose an exact published version and sync.

`sdkEnvironment` in `gradle.properties` selects `development` (default) or `production`. Both debug and release builds use that setting and the matching license. After changing it, sync and rebuild.

Both included sample licenses in `app/sample-licenses.properties` are bound to `com.optikosprime.sdk.sampleapp`. Set `sdkEnvironment=production` to use production, or select it for a single build:

```sh
./gradlew :app:assembleDebug -PsdkEnvironment=production
```

Changing the application ID requires matching access licenses.

The example uses `isOlderThan43 = true`. Use the user's actual answer when adapting the integration.
