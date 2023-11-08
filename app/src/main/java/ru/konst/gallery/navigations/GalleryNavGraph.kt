package ru.konst.gallery.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.konst.gallery.screens.GalleryScreen
import ru.konst.gallery.screens.PhotoItemScreen
import ru.konst.gallery.vm.GalleryViewModel

@Composable
fun GalleryNavGraph(navController: NavHostController, galleryViewModel: GalleryViewModel) {
    NavHost(
        navController = navController,
        startDestination = GalleryNavRoute.GalleryScreen.path
    ) {
        addGalleryScreen(galleryViewModel = galleryViewModel, navController = navController, navGraphBuilder = this)
        addPhotoIdScreen(galleryViewModel = galleryViewModel, navGraphBuilder = this)
    }
}

private fun addGalleryScreen(
    galleryViewModel: GalleryViewModel,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = GalleryNavRoute.GalleryScreen.path) {
        GalleryScreen(galleryViewModel = galleryViewModel, navHostController = navController)
    }
}

private fun addPhotoIdScreen(
    galleryViewModel: GalleryViewModel,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = GalleryNavRoute.PhotoItemScreen.withArgsFormat(GalleryNavRoute.PhotoItemScreen.photoId),
        arguments = listOf(
            navArgument(GalleryNavRoute.PhotoItemScreen.photoId) {
                type = NavType.IntType
            }
        )
    ) {
        PhotoItemScreen(galleryViewModel = galleryViewModel)
    }
}