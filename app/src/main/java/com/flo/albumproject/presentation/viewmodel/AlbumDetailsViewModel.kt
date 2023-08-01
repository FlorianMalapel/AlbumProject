package com.flo.albumproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.domain.usecases.AlbumUseCase
import com.flo.albumproject.domain.usecases.TrackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class AlbumDetailsViewModel(
    val trackUseCase: TrackUseCase,
    val albumUseCase: AlbumUseCase,
): ViewModel() {

    val liveAlbum: MutableLiveData<Album> = MutableLiveData()

    fun setAlbum(album: Album) {
        liveAlbum.postValue(album)
        loadDetails(album)
    }

    fun loadDetails(album: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            trackUseCase.getLocalTracksByAlbumId(album.id).firstOrNull()?.let { tracks ->
                liveAlbum.postValue(
                    Album(album.id, album.color, tracks.toMutableList())
                )
            }
        }
    }




    class AlbumDetailsViewModelFactory(
        private val trackUseCase: TrackUseCase,
        private val albumUseCase: AlbumUseCase
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return AlbumDetailsViewModel(
                trackUseCase = trackUseCase,
                albumUseCase = albumUseCase
            ) as T
        }

    }
}