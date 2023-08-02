package com.flo.albumproject.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.flo.albumproject.R
import com.flo.albumproject.presentation.composable.AlbumCard
import com.flo.albumproject.presentation.composable.TrackListItem
import com.flo.albumproject.presentation.theme.ProjectTheme
import com.flo.albumproject.presentation.viewmodel.AlbumDetailsViewModel

interface AlbumDetailsComponentCallback {
    fun leaveAlbumDetails()
}

@Composable
fun AlbumDetailsComponent(
    viewModel: AlbumDetailsViewModel,
    callback: AlbumDetailsComponentCallback
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ProjectTheme.colors.background
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            when {
                maxWidth < maxHeight -> {
                    AlbumDetailsComponentPortrait(
                        viewModel = viewModel,
                        callback = callback,
                        scope = this
                    )
                }

                else -> {
                    AlbumDetailsComponentLandscape(
                        viewModel = viewModel,
                        callback = callback,
                        scope = this
                    )
                }
            }
        }
    }
}

@Composable
private fun AlbumDetailsComponentPortrait(
    viewModel: AlbumDetailsViewModel,
    callback: AlbumDetailsComponentCallback,
    scope: BoxWithConstraintsScope
) {
    val album by viewModel.liveAlbum.observeAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        val (albumCover, textAlbumTitle, trackList, backButton) = createRefs()
        AnimatedVisibility(
            modifier = Modifier
                .size(scope.maxWidth * 0.4f)
                .constrainAs(albumCover) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 14.dp)
                },
            visible = album != null
        ) {
            AlbumCard(
                modifier = Modifier
                    .size(scope.maxWidth * 0.4f)
                    .testTag("album_details_cover"), album = album!!
            ) {
                // Callback
            }
        }

        IconButton(
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .testTag("back_button")
                .constrainAs(backButton) {
                    start.linkTo(parent.start)
                    top.linkTo(albumCover.top)
                },
            onClick = {
                callback.leaveAlbumDetails()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = LocalContext.current.getString(R.string.back_button),
                modifier = Modifier
                    .size(55.dp)
                    .padding(10.dp),
                tint = ProjectTheme.colors.onBackgroundHighEmphasis,
            )
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
                    TrackListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track_list_item_$index"),
                        track = track,
                        position = index
                    )
                }
            }
        }
    }
}

@Composable
private fun AlbumDetailsComponentLandscape(
    viewModel: AlbumDetailsViewModel,
    callback: AlbumDetailsComponentCallback,
    scope: BoxWithConstraintsScope
) {
    val album by viewModel.liveAlbum.observeAsState()
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight()
        ) {
            IconButton(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape)
                    .align(Alignment.TopStart)
                    .testTag("back_button"),
                onClick = {
                    callback.leaveAlbumDetails()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = LocalContext.current.getString(R.string.back_button),
                    modifier = Modifier.size(65.dp).padding(10.dp),
                    tint = ProjectTheme.colors.onBackgroundHighEmphasis,
                )
            }


            this@Row.AnimatedVisibility(
                modifier = Modifier.size(scope.maxWidth * 0.30f).align(Alignment.Center),
                visible = album != null
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                ) {
                    AlbumCard(
                        modifier = Modifier
                            .size(scope.maxWidth * 0.30f)
                            .align(CenterHorizontally)
                            .testTag("album_details_cover"),
                        album = album!!
                    ) {
                        // Callback
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(CenterHorizontally),
                        text = LocalContext.current.getString(
                            R.string.title_album_number,
                            album?.id
                        ),
                        color = ProjectTheme.colors.onBackgroundHighEmphasis,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(count = album?.tracks?.size ?: 0) { index ->
                album?.tracks?.get(index)?.let { track ->
                    TrackListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track_list_item_$index"),
                        track = track,
                        position = index
                    )
                }
            }
        }
    }
}