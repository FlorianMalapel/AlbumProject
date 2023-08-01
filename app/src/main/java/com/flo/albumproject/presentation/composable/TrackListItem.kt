package com.flo.albumproject.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flo.albumproject.R
import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.presentation.theme.ProjectTheme

@Composable
fun TrackListItem(modifier: Modifier, track: Track, position: Int) {
    ConstraintLayout(modifier = modifier) {
        val (image, texts) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(track.thumbnailUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.shape_placeholder),
            error = painterResource(R.drawable.shape_placeholder),
            contentDescription = track.thumbnailUrl,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(5.dp))
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Column(modifier = Modifier
            .wrapContentSize()
            .constrainAs(texts) {
                start.linkTo(image.end, 14.dp)
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
                end.linkTo(parent.end, 14.dp)
                width = Dimension.fillToConstraints
            }) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = track.title,
                color = ProjectTheme.colors.onBackgroundHighEmphasis,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = LocalContext.current.getString(R.string.subtitle_track_number, position + 1),
                color = ProjectTheme.colors.onBackgroundDisable,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
        }
    }
}