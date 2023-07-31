package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.flo.albumproject.R
import com.flo.albumproject.presentation.theme.ProjectTheme
import com.flo.albumproject.presentation.viewmodel.AlbumDetailsViewModel

@Composable
fun AlbumDetailsComponent(viewModel: AlbumDetailsViewModel) {

    val album by viewModel.liveAlbum.observeAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ProjectTheme.colors.background
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val (albumCover, textAlbumTitle, trackList) = createRefs()
            AlbumCard(
                modifier = Modifier
                    .constrainAs(albumCover) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }, album = album!!
            ) {
                // Callback
            }
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(textAlbumTitle) {
                        start.linkTo(parent.start)
                        top.linkTo(albumCover.bottom, 14.dp)
                    },
                text = LocalContext.current.getString(R.string.title_album_number, album?.id),
                color = ProjectTheme.colors.onBackgroundHighEmphasis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            LazyColumn(
                modifier = Modifier.constrainAs(trackList) {
                    top.linkTo(textAlbumTitle.bottom, 14.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(count = album?.tracks?.size ?: 0) { index ->
                    album?.tracks?.get(index)?.let { track ->
                        TrackListItem(modifier = Modifier.fillMaxWidth(), track = track)
                    }
                }
            }
        }
    }
}