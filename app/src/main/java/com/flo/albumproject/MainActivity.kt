package com.flo.albumproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.composable.CatalogComponentCallback
import com.flo.albumproject.presentation.screen.AlbumDetailsComponentCallback
import com.flo.albumproject.presentation.screen.MainScreen
import com.flo.albumproject.presentation.viewmodel.AlbumDetailsViewModel
import com.flo.albumproject.presentation.viewmodel.TrackViewModel
import com.flo.albumproject.utils.network.NetworkStatus
import com.flo.albumproject.utils.network.NetworkStatusListener

class MainActivity : ComponentActivity(), CatalogComponentCallback, AlbumDetailsComponentCallback {

    private val trackViewModel: TrackViewModel by viewModels {
        TrackViewModel.TrackViewModelFactory(
            (application as AppDelegate).trackUseCase,
            (application as AppDelegate).albumUseCase,
        )
    }

    private val albumDetailsViewModel: AlbumDetailsViewModel by viewModels {
        AlbumDetailsViewModel.AlbumDetailsViewModelFactory(
            (application as AppDelegate).trackUseCase,
        )
    }

    private lateinit var navController: NavHostController
    private val liveNetworkState: MutableLiveData<NetworkStatus> =
        MutableLiveData(NetworkStatusListener.getStatus())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            MainScreen(
                navController = navController,
                liveNetworkState = liveNetworkState,
                trackViewModel = trackViewModel,
                albumDetailsViewModel = albumDetailsViewModel,
                albumDetailsCallback = this@MainActivity,
                catalogComponentCallback = this@MainActivity
            )
        }
    }

    override fun onStart() {
        super.onStart()
        observe()
        trackViewModel.load()
    }

    private fun observe() {
        NetworkStatusListener.observe(this) {
            Log.wtf("#test", "offline ? $it ${NetworkStatusListener.isOffline()}")
            liveNetworkState.postValue(it)
        }
    }

    /**
     * CatalogComponentCallback
     */
    override fun select(album: Album) {
        albumDetailsViewModel.setAlbum(album)
        navController.navigate("album_detail")
    }

    override fun refresh() {
        trackViewModel.load()
    }

    /**
     * AlbumDetailsComponentCallback
     */
    override fun leaveAlbumDetails() {
        navController.popBackStack()
    }
}