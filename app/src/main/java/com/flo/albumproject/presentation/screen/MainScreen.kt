package com.flo.albumproject.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.flo.albumproject.R
import com.flo.albumproject.presentation.composable.CatalogComponent
import com.flo.albumproject.presentation.composable.CatalogComponentCallback
import com.flo.albumproject.presentation.composable.common.OfflineCard
import com.flo.albumproject.presentation.theme.AlbumProjectTheme
import com.flo.albumproject.presentation.theme.ProjectTheme
import com.flo.albumproject.presentation.viewmodel.AlbumDetailsViewModel
import com.flo.albumproject.presentation.viewmodel.TrackViewModel
import com.flo.albumproject.utils.network.NetworkStatus

enum class NavigationScreen(val route: String) {
    Albums("albums"), AlbumDetails("album_details")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    liveNetworkState: MutableLiveData<NetworkStatus>,
    trackViewModel: TrackViewModel,
    albumDetailsViewModel: AlbumDetailsViewModel,
    albumDetailsCallback: AlbumDetailsComponentCallback,
    catalogComponentCallback: CatalogComponentCallback
) {
    val networkStatus by liveNetworkState.observeAsState()
    AlbumProjectTheme {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ProjectTheme.colors.background),
                navController = navController, startDestination = NavigationScreen.Albums.route
            ) {
                composable(NavigationScreen.Albums.route) {
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
                                    callback = catalogComponentCallback,
                                    topBarPaddingValues = it
                                )
                            }
                        }
                    )
                }
                composable(NavigationScreen.AlbumDetails.route) {
                    AlbumDetailsComponent(
                        viewModel = albumDetailsViewModel,
                        callback = albumDetailsCallback
                    )
                }
            }
            AnimatedVisibility(
                visible = networkStatus == NetworkStatus.Offline,
                modifier = Modifier
                    .alpha(0.95f)
                    .constrainAs(createRef()) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                    }
            ) {
                OfflineCard(modifier = Modifier)
            }
        }
    }
}