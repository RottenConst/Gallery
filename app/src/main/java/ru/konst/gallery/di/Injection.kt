package ru.konst.gallery.di

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import ru.konst.gallery.network.ApiGallery
import ru.konst.gallery.repository.GalleryRepository

object Injection {

    private fun provideGalleryRepository(): GalleryRepository {
        return GalleryRepository(ApiGallery.create())
    }
    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGalleryRepository())
    }
}