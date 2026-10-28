package com.optikosprime.sdk.sampleapp

import com.optikosprime.sdk.OptikosPrimeStyle

/** Mirrors the iOS sample's customized light and dark palette. */
internal object ExampleTheme {
    fun customizedStyle(dark: Boolean) = if (dark) OptikosPrimeStyle(
        background = 0xFF0F1429, surface = 0xFF1F2640,
        primaryText = 0xFFFFFFFF, secondaryText = 0xFFAAAAAA,
        primaryAction = 0xFF66D4CF, primaryActionText = 0xFF000000,
        secondaryAction = 0xFF333B5C, success = 0xFF66D4CF,
        warning = 0xFFFFD60A, error = 0xFFFF375F, cornerRadius = 18f,
    ) else OptikosPrimeStyle(
        background = 0xFFF5F7FF, surface = 0xFFFFFFFF,
        primaryText = 0xFF1A2447, secondaryText = 0xFF555555,
        primaryAction = 0xFF056654, primaryActionText = 0xFFFFFFFF,
        secondaryAction = 0xFFE0E3F7, success = 0xFF146E47,
        warning = 0xFF995205, error = 0xFFB81F38, cornerRadius = 18f,
    )
}
