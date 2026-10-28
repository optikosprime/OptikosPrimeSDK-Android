import java.util.Properties

plugins { id("com.android.application"); id("org.jetbrains.kotlin.android"); id("org.jetbrains.kotlin.plugin.compose") }
val sdkVersion = providers.gradleProperty("sdkVersion").get()
val sdkEnvironment = providers.gradleProperty("sdkEnvironment").orNull
    ?: error("Set sdkEnvironment in gradle.properties to development or production.")
val sdkEnvironmentEnum = when (sdkEnvironment) {
    "development" -> "Development"
    "production" -> "Production"
    else -> error("sdkEnvironment must be development or production.")
}
val sampleLicenses = Properties().apply {
    project.file("sample-licenses.properties").inputStream().use { load(it) }
}
fun sampleLicense(name: String): String {
    val value = sampleLicenses.getProperty(name) ?: error("Missing $name. Add it to app/sample-licenses.properties.")
    require(value.matches(Regex("[A-Za-z0-9_.-]+"))) { "Invalid sample license format: $name" }
    return "\"$value\""
}

android {
    namespace = "com.optikosprime.sdk.sampleapp"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.optikosprime.sdk.sampleapp"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = sdkVersion
        buildConfigField("String", "SDK_LICENSE", sampleLicense("sample.license.$sdkEnvironment"))
        buildConfigField(
            "com.optikosprime.sdk.OptikosPrimeEnvironment",
            "SDK_ENVIRONMENT",
            "com.optikosprime.sdk.OptikosPrimeEnvironment.$sdkEnvironmentEnum",
        )
    }
    buildFeatures { compose = true; buildConfig = true }
    androidResources { noCompress += "task" }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}
kotlin { compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) } }
dependencies {
    implementation("com.optikosprime:sdk:$sdkVersion")
    implementation(platform("androidx.compose:compose-bom:2025.10.01"))
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.compose.material3:material3")
}
