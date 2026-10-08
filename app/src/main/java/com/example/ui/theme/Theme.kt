package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val QuestDarkColorScheme = darkColorScheme(
    primary = QuestPrimary,
    onPrimary = QuestBaseDark,
    primaryContainer = QuestPrimaryContainer,
    onPrimaryContainer = QuestBlueTint,
    secondary = QuestGold,
    onSecondary = QuestBaseDark,
    secondaryContainer = QuestGoldContainer,
    onSecondaryContainer = QuestGoldText,
    tertiary = QuestTeal,
    onTertiary = QuestBaseDark,
    tertiaryContainer = QuestTealContainer,
    onTertiaryContainer = QuestTealText,
    background = QuestBaseDark,
    onBackground = QuestTextPrimaryDark,
    surface = QuestSurfaceDark,
    onSurface = QuestTextPrimaryDark,
    surfaceVariant = QuestSurfaceSunken,
    onSurfaceVariant = QuestTextSecondaryDark,
    outline = QuestOutline,
    outlineVariant = QuestOutlineVariant,
    error = QuestError,
    onError = QuestBaseDark,
    errorContainer = QuestErrorContainer,
    onErrorContainer = QuestErrorText
)

private val QuestLightColorScheme = lightColorScheme(
    primary = QuestPrimaryDeep,
    onPrimary = QuestTextPrimaryDark,
    primaryContainer = QuestBlueTint,
    onPrimaryContainer = QuestBaseDark,
    secondary = QuestGold,
    onSecondary = QuestBaseDark,
    secondaryContainer = QuestGoldContainer,
    onSecondaryContainer = QuestGoldText,
    tertiary = QuestTeal,
    onTertiary = QuestBaseDark,
    tertiaryContainer = QuestTealContainer,
    onTertiaryContainer = QuestTealText,
    background = QuestTextPrimaryDark,
    onBackground = QuestBaseDark,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = QuestBaseDark,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE2E8F0),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF475569),
    outline = QuestOutline,
    outlineVariant = QuestOutlineVariant,
    error = QuestError,
    onError = QuestTextPrimaryDark
)

@Composable
fun QuestLearnTheme(
    darkTheme: Boolean = true, // Default dark per Design Brief
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) QuestDarkColorScheme else QuestLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
