# Optikos Prime SDK for Android

Android SDK binaries and a sample app for integrating Optikos Prime vision checks.

Version **0.1.0-beta.6** is a development beta that has not completed production validation. Commercial license terms are being prepared.

## Install

Follow the [installation guide](docs/INSTALLATION.md) to add the SDK as a Gradle dependency. Gradle downloads the SDK from [GitHub Releases](https://github.com/optikosprime/OptikosPrimeSDK-Android/releases) and resolves its dependencies automatically.

Requires Android 11/API 30+, compile SDK 36+, Java 17, and a Kotlin 2.2-compatible toolchain.

## Sample app

Open `SampleApp` in Android Studio, sync, and run `app` on a supported physical phone. A development access license is included for the sample application ID. See the [sample instructions](SampleApp/README.md).

## Themes and custom fonts

Choose a predefined `OptikosPrimeStyle`, then use `copy` for any overrides. Pass it through `OptikosPrimeConfiguration` before launching the Activity contract or the Compose flow. Standalone SDK screens accept the same style through their `style` parameter.

### Predefined themes

| Style | Appearance | Colors and typography |
| --- | --- | --- |
| `OptikosPrimeStyle.Default` (also `OptikosPrimeStyle()`) | Follows the host's light/dark appearance | White pages and burgundy primary buttons in light mode; dark surfaces and muted red buttons in dark mode. Uses Android system typography. |
| `OptikosPrimeStyle.Red` | Fixed light palette, including in a dark host | White pages, dark text, red primary buttons with white labels, and pale pink secondary buttons with red labels. Uses Android system typography. |

The red theme is opt-in. The default style and default configuration are unchanged. Android's system font differs from iOS's rounded system font; supply a bundled custom font to use your own typeface.

The red palette uses these sRGB colors (Android configuration values include the opaque `FF` alpha prefix):

| Color role | Value |
| --- | --- |
| Page and card background | `#FFFFFF` |
| Primary and secondary text | `#1A1A1A` |
| Primary button background | `#BF2B34` |
| Primary button text | `#FFFFFF` |
| Secondary button and assessment tile background | `#FAEEEF` |
| Secondary button text | `#BF2B34` |

At standard font scale, `Red` uses 24 sp bold titles, 18 sp body text, and 20 sp semibold button labels. It supplies 32 dp horizontal padding, 12 dp corners, and a 56 dp minimum button height. Buttons can grow for larger or multiline text. The theme applies to the SDK's existing screens and artwork.

```kotlin
import com.optikosprime.sdk.OptikosPrimeConfiguration
import com.optikosprime.sdk.OptikosPrimeStyle

val configuration = OptikosPrimeConfiguration(
    isOlderThan43 = isOlderThan43, // Use the answer collected by your app.
    style = OptikosPrimeStyle.Red,
)
// After initialization, launch with the registered OptikosPrimeContract launcher:
visionCheck.launch(configuration)
```

### Apply a custom font to a predefined theme

Bundle a `.ttf` or `.otf` font file in the **host app's** `app/src/main/res/font` directory, for example `brand_sans_regular.ttf`. Resource filenames use lowercase letters, digits, and underscores. Android generates its resource ID as `R.font.brand_sans_regular`; no runtime font registration is needed. See Android's [font resources guidance](https://developer.android.com/develop/ui/compose/resources#fonts).

Copy either preset and set `fontFamilyResource` to the host's font resource ID:

```kotlin
import com.optikosprime.sdk.OptikosPrimeConfiguration
import com.optikosprime.sdk.OptikosPrimeStyle
// R must refer to your host app's generated resource class.

val style = OptikosPrimeStyle.Red.copy( // Or OptikosPrimeStyle.Default.copy(...).
    fontFamilyResource = R.font.brand_sans_regular,
)
val configuration = OptikosPrimeConfiguration(
    isOlderThan43 = isOlderThan43,
    style = style,
)
```

Changing only `fontFamilyResource` preserves the preset's colors, layout, sizes, and font scaling. `copy` creates a separate value; the preset itself is unchanged. Leave the property `null` to use the system font. The SDK accepts a bundled font file resource, not a font filename, asset path, iOS PostScript name, or Compose `FontFamily` object.

This setting supplies one loaded font entry across the SDK's configurable typography; it does not expose separate resources per text role or weight. Titles and buttons retain their requested weights; the renderer may synthesize those weights from the supplied face. To change base sizes, copy `titleSize`, `bodySize`, `buttonSize`, `captionSize`, or `displaySize`. These values are in sp, not maximum rendered sizes.

Camera guidance and Help use their built-in fonts and colors. Camera's rectangular guidance buttons do use the configured button radius. The bundled “Powered by OptikosPrime” artwork keeps its own lettering. Validation retains its dark media background.

### Limit the maximum font scale

Text follows the user's font-size preference by default. Set `maximumFontScale` to impose an optional upper limit:

```kotlin
val style = OptikosPrimeStyle.Red.copy(maximumFontScale = 1.3f)
val configuration = OptikosPrimeConfiguration(
    isOlderThan43 = isOlderThan43,
    style = style,
)
```

`null` leaves ordinary SDK text unrestricted. A finite positive value caps the Android font scale; `1f` is the standard scale. Smaller user preferences still apply. This controls the scale, not an absolute font size, and Android's scaling is not an exact mapping to iOS Dynamic Type categories.

Camera guidance, Help, and diopter chart labels retain their built-in `1.12f` ceiling. A configured maximum of `1.3f` cannot raise that ceiling; `1f` lowers it. The setting applies to the complete flow and standalone SDK screens, including validation. It does not change the host app's own text.

## Third-party licenses

See [third-party notices](THIRD_PARTY_NOTICES.md) and the [Apache-2.0 license text](APACHE-2.0.txt). These cover the applicable third-party components, not the Optikos Prime SDK itself.
