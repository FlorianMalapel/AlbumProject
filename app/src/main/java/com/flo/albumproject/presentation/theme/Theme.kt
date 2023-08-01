package com.flo.albumproject.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object ProjectTheme {
    val colors: ProjectColors
        @Composable
        get() = LocalProjectColors.current

}

private val LocalProjectColors = staticCompositionLocalOf { commonAlignColors }

private val commonAlignColors = ProjectColors(
    primary = Color.Unspecified,
    primaryVariant = Color.Unspecified,
    secondary = Color.Unspecified,
    secondaryVariant = Color.Unspecified,
    background = Color.Unspecified,
    surface = Color.Unspecified,
    menuItem = Color.Unspecified,
    error = Color.Unspecified,
    onBackground = Color.Unspecified,
    onBackgroundDisable = Color.Unspecified,
    onBackgroundMediumEmphasis = Color.Unspecified,
    onBackgroundHighEmphasis = Color.Unspecified,
    onError = Color.Unspecified,
    white = Color(0xFFFFFFFF),
    whiteHighEmphasis = Color(0xDEFFFFFF),
    whiteMediumEmphasis = Color(0x99FFFFFF),
    whiteDisable = Color(0x5EFFFFFF),
    black = Color(0xFF000000),
    blackHighEmphasis = Color(0xDE000000),
    blackMediumEmphasis = Color(0x99000000),
    blackDisable = Color(0x5E000000),
    transparent = Color.Transparent,
)

private val lightProjectColors = with(commonAlignColors) {
    copy(
        primary = LightGray,
        primaryVariant = MiddleGray,
        secondary = DarkBlue,
        secondaryVariant = DarkGray,
        background = LightGray,
        surface = LightGray,
        menuItem = black,
        error = Red,
        onBackground = DarkBlue,
        onBackgroundDisable = DarkBlue.copy(alpha = 0.37f),
        onBackgroundMediumEmphasis = DarkBlue.copy(alpha = 0.60f),
        onBackgroundHighEmphasis = DarkBlue.copy(alpha = 0.87f),
        onError = whiteHighEmphasis,
    )
}

private val darkProjectColors = with(commonAlignColors) {
    copy(
        primary = DarkBlue,
        primaryVariant = DarkGray,
        secondary = DarkGray,
        secondaryVariant = MiddleGray,
        background = DarkBlue,
        surface = DarkBlue,
        menuItem = white,
        error = Red,
        onBackground = LightGray,
        onBackgroundDisable = LightGray.copy(alpha = 0.37f),
        onBackgroundMediumEmphasis = LightGray.copy(alpha = 0.60f),
        onBackgroundHighEmphasis = LightGray.copy(alpha = 0.87f),
        onError = whiteHighEmphasis,
    )
}

@Composable
fun AlbumProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) darkProjectColors else lightProjectColors
        }

        darkTheme -> darkProjectColors
        else -> lightProjectColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalProjectColors provides if (darkTheme) darkProjectColors else lightProjectColors,
        content = content
    )
}
