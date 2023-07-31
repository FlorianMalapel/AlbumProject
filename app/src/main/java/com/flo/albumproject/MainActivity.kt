package com.flo.albumproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.composables.CatalogComponent
import com.flo.albumproject.presentation.composables.CatalogComponentCallback
import com.flo.albumproject.presentation.composables.AlbumDetailsComponent
import com.flo.albumproject.presentation.theme.AlbumProjectTheme
import com.flo.albumproject.presentation.theme.ProjectTheme
import com.flo.albumproject.presentation.viewmodel.AlbumDetailsViewModel
import com.flo.albumproject.presentation.viewmodel.TrackViewModel

class MainActivity : ComponentActivity(), CatalogComponentCallback {

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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            AlbumProjectTheme {
                NavHost(
                    modifier = Modifier.fillMaxSize().background(ProjectTheme.colors.background),
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
                                    modifier = Modifier.fillMaxSize().padding(it),
                                    color = ProjectTheme.colors.background
                                ) {
                                    CatalogComponent(
                                        liveAlbums = trackViewModel.liveAlbums,
                                        callback = this@MainActivity
                                    )
                                }
                            }
                        )
                    }
                    composable("album_detail") {
                        AlbumDetailsComponent(viewModel = albumDetailsViewModel)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        trackViewModel.load()
    }

    /**
     * CatalogComponentCallback
     */
    override fun select(album: Album) {
        albumDetailsViewModel.setAlbum(album)
        navController.navigate("album_detail")
    }
}