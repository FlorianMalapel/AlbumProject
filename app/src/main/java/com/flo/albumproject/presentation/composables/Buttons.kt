package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flo.albumproject.presentation.theme.ProjectTheme
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

@Composable
fun ThemeButton(
    modifier: Modifier,
    textRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, modifier = modifier.wrapContentSize().padding(all = 5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ProjectTheme.colors.secondary)
    ) {
        Text(
            text = LocalContext.current.getString(textRes),
            modifier = Modifier
                .wrapContentSize()
                .align(CenterVertically),
            color = ProjectTheme.colors.whiteHighEmphasis,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}