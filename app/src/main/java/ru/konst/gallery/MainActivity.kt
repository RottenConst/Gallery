package ru.konst.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import ru.konst.gallery.di.Injection
import ru.konst.gallery.navigations.GalleryNavGraph
import ru.konst.gallery.ui.theme.GalleryTheme
import ru.konst.gallery.vm.GalleryViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(
            owner = this,
            factory = Injection.provideViewModelFactory(owner = this)
        ).get(GalleryViewModel::class.java)
        setContent {
            GalleryTheme {
                val navController = rememberNavController()
                GalleryNavGraph(navController = navController, galleryViewModel = viewModel)
            }
        }
    }
}