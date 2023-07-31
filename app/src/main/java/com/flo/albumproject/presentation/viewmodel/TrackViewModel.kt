package com.flo.albumproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.domain.usecases.AlbumUseCase
import com.flo.albumproject.domain.usecases.TrackUseCase
import com.flo.albumproject.domain.usecases.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

enum class LoadingState {
    LOADING, LOADED, NO_DATA
}

//enum class NetworkState {
//    OFFLINE, ONLINE
//}

class TrackViewModel(
    val trackUseCase: TrackUseCase,
    val albumUseCase: AlbumUseCase,
) : ViewModel() {

    val liveLoadingState: MutableLiveData<LoadingState?> = MutableLiveData()
    val liveAlbums: MutableLiveData<List<Album>?> = MutableLiveData()

    fun load() {
        liveLoadingState.postValue(LoadingState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = trackUseCase.getRemote()
            if (result is NetworkResult.Success) {
                result.tracks?.let { tracks ->
//                    liveAlbums.postValue(buildAlbumList(tracks))
                    liveAlbums.postValue(albumUseCase.getLocal().firstOrNull())
                    return@launch
                }
            }
            liveAlbums.postValue(albumUseCase.getLocal().firstOrNull())
//            liveAlbums.postValue(buildAlbumList(trackUseCase.getLocal().first()))
        }
    }

//    private fun buildAlbumList(tracks: List<Track>): List<Album> {
//        val albums = mutableListOf<Album>()
//        for (track in tracks) {
//            if (!albums.any { it.id == track.albumId }) {
//                albums.add(Album(track.albumId, mutableListOf(track)))
//                continue
//            }
//            val albumIndex = albums.indexOfFirst { it.id == track.albumId }
//            albums[albumIndex].tracks?.add(track)
//        }
//        return albums
//    }


    class TrackViewModelFactory(
        private val trackUseCase: TrackUseCase,
        private val albumUseCase: AlbumUseCase
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return TrackViewModel(
                trackUseCase = trackUseCase,
                albumUseCase = albumUseCase
            ) as T
        }

    }

}