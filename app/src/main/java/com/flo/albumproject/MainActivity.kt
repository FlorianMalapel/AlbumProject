package com.flo.albumproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.presentation.composables.CatalogComponent
import com.flo.albumproject.presentation.composables.CatalogComponentCallback
import com.flo.albumproject.presentation.theme.AlbumProjectTheme
import com.flo.albumproject.presentation.theme.md_theme_dark_onPrimary
import com.flo.albumproject.presentation.theme.md_theme_dark_onPrimaryContainer
import com.flo.albumproject.presentation.theme.md_theme_dark_primary
import com.flo.albumproject.presentation.theme.md_theme_light_primary
import com.flo.albumproject.presentation.theme.md_theme_light_secondary
import com.flo.albumproject.presentation.viewmodel.AlbumViewModel

class MainActivity : ComponentActivity(), CatalogComponentCallback {

    private val albumViewModel: AlbumViewModel by viewModels {
        AlbumViewModel.AlbumModelFactory(
            (application as AppDelegate).albumUseCase
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlbumProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar =  {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = md_theme_light_secondary),
                            title = {
                                Text(
                                    LocalContext.current.getString(R.string.title_albums),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = md_theme_dark_onPrimaryContainer
                                )
                            })
                    },
                    content = {
                        Surface(
                            modifier = Modifier.fillMaxSize().padding(it),
                        ) {
                            CatalogComponent(liveAlbums = albumViewModel.liveAlbums, callback = this@MainActivity)
                        }
                    })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        albumViewModel.load()
    }

    /**
     * CatalogComponentCallback
     */
    override fun select(album: Album) {

    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlbumProjectTheme {
        Greeting("Android")
    }
}