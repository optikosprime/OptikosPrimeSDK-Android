package com.optikosprime.sdk.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.optikosprime.sdk.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var message by mutableStateOf("Ready to start a vision check.")
    private var presenting by mutableStateOf(false)

    private fun outcome(value: OptikosPrimeOutcome) {
        presenting = false
        message = when (value.status) {
            OptikosPrimeStatus.completed -> value.result?.let { result ->
                val assessment = if (result.questionnaire == OptikosPrimeQuestionResult.notAnswered)
                    "Camera assessment" else "Questionnaire assessment: ${result.questionnaire.name}"
                "Completed: ${result.results.joinToString(", ") { it.title(this) }}\n$assessment\nMeasurement: ${result.measurementID}"
            } ?: "The test returned no result."
            OptikosPrimeStatus.cancelled -> "The test was cancelled."
            OptikosPrimeStatus.failed -> errorMessage(value.error)
        }
    }

    private fun errorMessage(error: Exception?): String =
        if (error is OptikosPrimeException && error.code == OptikosPrimeErrorCode.deviceNotSupported)
            "This device is not supported." else error?.message ?: "The test failed."

    private fun initialize() {
        if (!OptikosPrimeSDK.isInitialized) {
            OptikosPrimeSDK.initialize(
                applicationContext,
                BuildConfig.SDK_LICENSE,
                BuildConfig.SDK_ENVIRONMENT,
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val style = ExampleTheme.customizedStyle(isSystemInDarkTheme())
            val colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
            MaterialTheme(colorScheme = colors.copy(
                background = Color(style.background), surface = Color(style.surface),
                primary = Color(style.primaryAction), onPrimary = Color(style.primaryActionText),
                onBackground = Color(style.primaryText), onSurface = Color(style.primaryText),
            )) {
                var checking by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                val launcher = rememberLauncherForActivityResult(OptikosPrimeContract(), ::outcome)
                val enabled = !checking && !presenting
                Surface(Modifier.fillMaxSize(), color = Color(style.background)) {
                    Column(
                        Modifier.safeDrawingPadding().padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState()).padding(vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TextButton(enabled = enabled, onClick = {
                            try {
                                initialize()
                                checking = true
                                message = "Checking camera support…"
                                scope.launch {
                                    try {
                                        message = if (OptikosPrimeSDK.getCameraInfo(forceRefresh = true).supported)
                                            "Camera is supported." else "This device is not supported."
                                    } catch (e: CancellationException) { throw e }
                                    catch (e: Exception) { message = errorMessage(e) }
                                    finally { checking = false }
                                }
                            } catch (e: Exception) { checking = false; message = errorMessage(e) }
                        }) { Text("Check camera support") }
                        Button(
                            modifier = Modifier.fillMaxWidth(), enabled = enabled,
                            shape = RoundedCornerShape(style.cornerRadius.dp),
                            onClick = {
                                try {
                                    initialize()
                                    // Same sample age and complete questionnaire-enabled flow as iOS.
                                    val configuration = OptikosPrimeConfiguration(isOlderThan43 = true, style = style)
                                    presenting = true
                                    launcher.launch(configuration)
                                } catch (e: Exception) { presenting = false; message = errorMessage(e) }
                            },
                        ) { Text("Start full vision check") }
                        Text(message, color = Color(style.secondaryText), textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
