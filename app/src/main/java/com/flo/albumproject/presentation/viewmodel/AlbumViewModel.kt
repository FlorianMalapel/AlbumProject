package com.flo.albumproject.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.usecases.AlbumUseCase
import com.flo.albumproject.domain.usecases.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

enum class LoadingState {
    LOADING, LOADED, NO_DATA
}

//enum class NetworkState {
//    OFFLINE, ONLINE
//}

class AlbumViewModel(
    val useCase: AlbumUseCase
): ViewModel() {

    val liveLoadingState: MutableLiveData<LoadingState?> = MutableLiveData()
    val liveAlbums: MutableLiveData<List<Track>?> = MutableLiveData()

    fun load() {
        liveLoadingState.postValue(LoadingState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.getRemote()
            if (result is NetworkResult.Success) {
                liveAlbums.postValue(result.albums)
                return@launch
            }
            liveAlbums.postValue(useCase.getLocal().first())
        }
    }



    class AlbumModelFactory(
        private val albumUseCase: AlbumUseCase
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return AlbumViewModel(
                useCase = albumUseCase
            ) as T
        }

    }

}