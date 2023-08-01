package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flo.albumproject.presentation.theme.ProjectTheme

@Composable
fun ThemeLoader(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = ProjectTheme.colors.secondary,
        strokeWidth = 2.dp,
        strokeCap = StrokeCap.Round,
        trackColor = Color.Transparent
    )
}