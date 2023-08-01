package com.flo.albumproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.composable.CatalogComponent
import com.flo.albumproject.presentation.composable.CatalogComponentCallback
import com.flo.albumproject.presentation.composable.AlbumDetailsComponent
import com.flo.albumproject.presentation.composable.AlbumDetailsComponentCallback
import com.flo.albumproject.presentation.composable.common.OfflineCard
import com.flo.albumproject.presentation.theme.AlbumProjectTheme
import com.flo.albumproject.presentation.theme.ProjectTheme
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
            (application as AppDelegate).albumUseCase,
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
            val networkStatus by liveNetworkState.observeAsState()
            AlbumProjectTheme {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ProjectTheme.colors.background),
                        navController = navController, startDestination = "albums"
                    ) {
                        composable("albums") {
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                topBar = {
                                    CenterAlignedTopAppBar(
                                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                            containerColor = ProjectTheme.colors.background
                                        ),
                                        title = {
                                            Text(
                                                LocalContext.current.getString(R.string.title_albums),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 20.sp,
                                                color = ProjectTheme.colors.onBackground
                                            )
                                        })
                                },
                                content = {
                                    Surface(
                                        modifier = Modifier.fillMaxSize(),
                                        color = ProjectTheme.colors.background
                                    ) {
                                        CatalogComponent(
                                            liveAlbums = trackViewModel.liveAlbums,
                                            liveLoadingState = trackViewModel.liveLoadingState,
                                            callback = this@MainActivity,
                                            topBarPaddingValues = it
                                        )
                                    }
                                }
                            )
                        }
                        composable("album_detail") {
                            AlbumDetailsComponent(
                                viewModel = albumDetailsViewModel,
                                callback = this@MainActivity
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = networkStatus == NetworkStatus.Offline,
                        modifier = Modifier.alpha(0.95f).constrainAs(createRef()) {
                            end.linkTo(parent.end, 8.dp)
                            top.linkTo(parent.top, 8.dp)
                        }
                    ) {
                        OfflineCard(modifier = Modifier)
                    }
                }
            }
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