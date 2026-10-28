pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
val sdkReleasesUrl = providers.gradleProperty("sdkReleasesUrl")
    .orElse("https://github.com/optikosprime/OptikosPrimeSDK-Android/releases/download")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        exclusiveContent {
            forRepository {
                ivy {
                    name = "optikosPrimeReleases"
                    url = uri(sdkReleasesUrl.get())
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
rootProject.name = "OptikosPrimeSDK-PublicSample"
include(":app")
