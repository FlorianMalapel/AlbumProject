package com.flo.albumproject.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.flo.albumproject.domain.entities.Album

interface CatalogComponentCallback {
    fun select(album: Album)
}

@Composable
fun CatalogComponent(
    liveAlbums: MutableLiveData<List<Album>?>,
    callback: CatalogComponentCallback
) {
    val albums by liveAlbums.observeAsState()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val padding = 10.dp
        val gridCellSize = (maxWidth / 3) - (padding * 3)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(padding),
            verticalArrangement = Arrangement.spacedBy(padding),
            horizontalArrangement = Arrangement.spacedBy(padding),
            columns = GridCells.Adaptive(minSize = gridCellSize)
        ) {
            items(count = albums?.size ?: 0) { index ->
                AlbumCard(
                    modifier = Modifier
                        .size(gridCellSize),
//                    .animateItemPlacement(),
                    album = albums!![index]
                ) {
                    callback.select(albums!![index])
                }
            }
        }
    }
}