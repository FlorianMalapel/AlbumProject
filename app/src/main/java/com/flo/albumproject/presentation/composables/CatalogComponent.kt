package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.viewmodel.LoadingState

interface CatalogComponentCallback {
    fun select(album: Album)
    fun refresh()
}

@Composable
fun CatalogComponent(
    liveAlbums: MutableLiveData<List<Album>?>,
    callback: CatalogComponentCallback,
    topBarPaddingValues: PaddingValues,
    liveLoadingState: MutableLiveData<LoadingState?>
) {
    val albums by liveAlbums.observeAsState()
    val state by liveLoadingState.observeAsState()

    if (state == LoadingState.NO_DATA) {
        EmptyStateComponent {
            callback.refresh()
        }
    } else if (state == LoadingState.LOADING) {
        Box(modifier = Modifier.fillMaxSize()) {
            ThemeLoader(Modifier.size(50.dp).align(Alignment.Center))
        }
    } else {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val padding = 15.dp
            val gridCellSize = (maxWidth / 2) - (padding * 3)
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = padding,
                    end = padding,
                    top = topBarPaddingValues.calculateTopPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(padding),
                horizontalArrangement = Arrangement.spacedBy(padding),
                columns = GridCells.Adaptive(minSize = gridCellSize)
            ) {
                items(count = albums?.size ?: 0, key = { index ->
                    albums!![index].id
                }) { index ->
                    AlbumCard(
                        modifier = Modifier.size(gridCellSize),
                        album = albums!![index]
                    ) {
                        callback.select(albums!![index])
                    }
                }
            }
        }
    }
}