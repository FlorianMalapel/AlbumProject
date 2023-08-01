package com.flo.albumproject.presentation.composable.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flo.albumproject.R
import com.flo.albumproject.presentation.theme.ProjectTheme
import com.flo.albumproject.utils.network.NetworkStatus

@Composable
fun OfflineCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(containerColor = ProjectTheme.colors.secondary)
    ) {
        Row(modifier = Modifier.wrapContentSize().padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_network_error),
                contentDescription = LocalContext.current.getString(R.string.offline_issue),
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .align(CenterVertically),
                tint = ProjectTheme.colors.error
            )
            Text(
                text = LocalContext.current.getString(R.string.offline_card),
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically),
                color = ProjectTheme.colors.error,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}