package com.flo.albumproject.presentation.composable.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flo.albumproject.R
import com.flo.albumproject.presentation.theme.ProjectTheme

@Composable
fun EmptyStateComponent(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .align(CenterHorizontally),
                color = ProjectTheme.colors.secondary,
                shape = CircleShape
            ) {
                Surface(modifier = Modifier
                    .size(200.dp)
                    .padding(50.dp).blur(radius = 75.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                    color = ProjectTheme.colors.whiteMediumEmphasis) {}
                Icon(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(50.dp),
                    painter = painterResource(id = R.drawable.ic_network_error),
                    tint = ProjectTheme.colors.whiteHighEmphasis,
                    contentDescription = LocalContext.current.getString(R.string.offline_issue)
                )

            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = LocalContext.current.getString(R.string.offline_issue),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = ProjectTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            ThemeButton(modifier = Modifier.align(CenterHorizontally), textRes = R.string.offline_retry) {
                onClick()
            }
        }
    }
}