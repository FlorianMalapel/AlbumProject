package com.flo.albumproject.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ProjectColors(
    val primary: Color,
    val primaryVariant: Color,

    val secondary: Color,
    val secondaryVariant: Color,

    val background: Color,
    val surface: Color,
    val menuItem: Color,
    val error: Color,

    val onBackground: Color,
    val onBackgroundHighEmphasis: Color,
    val onBackgroundMediumEmphasis: Color,
    val onBackgroundDisable: Color,
    val onError: Color,

    val white: Color,
    val whiteHighEmphasis: Color,
    val whiteMediumEmphasis: Color,
    val whiteDisable: Color,
    val black: Color,
    val blackHighEmphasis: Color,
    val blackMediumEmphasis: Color,
    val blackDisable: Color,

    val transparent: Color
)

val DarkBlue = Color(0xFF2B2D42)
val LightGray = Color(0xFFEDF2F4)
val MiddleGray = Color(0xFFD1D1D6)
val DarkGray = Color(0xFF8D99AE)
val Red = Color(0xFFFE3333)

