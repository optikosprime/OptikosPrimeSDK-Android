# Third-party components

The SDK and sample use the following third-party components. Their licenses and notices remain applicable when distributing an application or the SDK package.

| Component | Upstream | License |
| --- | --- | --- |
| AndroidX / Compose / Media3 | https://android.googlesource.com/platform/frameworks/support/ | Apache-2.0 |
| MediaPipe Tasks | https://github.com/google-ai-edge/mediapipe | Apache-2.0 |
| Kotlin / kotlinx.coroutines / kotlinx.serialization | https://github.com/JetBrains/kotlin | Apache-2.0 |
| OkHttp / Okio | https://github.com/square/okhttp | Apache-2.0 |
| Gradle wrapper | https://github.com/gradle/gradle | Apache-2.0 |

The bundled `optikos_face_landmarker.task` is byte-identical to Google's face-landmarker float16 model, version 1:

https://storage.googleapis.com/mediapipe-models/face_landmarker/face_landmarker/float16/1/face_landmarker.task

SHA-256: `64184e229b263107bc2b804c6625db1341ff2bb731874b0bcc2fe6544e0bc9ff`.

Model documentation: https://ai.google.dev/edge/mediapipe/solutions/vision/face_landmarker
