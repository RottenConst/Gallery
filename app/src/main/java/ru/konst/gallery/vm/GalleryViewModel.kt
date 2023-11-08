package ru.konst.gallery.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import ru.konst.gallery.data.PhotoItem
import ru.konst.gallery.navigations.GalleryNavRoute
import ru.konst.gallery.repository.GalleryRepository

class GalleryViewModel (
    private val galleryRepository: GalleryRepository
): ViewModel() {

    val pagingDataFlow: Flow<PagingData<PhotoItem>>
    private val _photo: MutableLiveData<PhotoItem> = MutableLiveData()
    val photo: LiveData<PhotoItem>
        get() = _photo

    init {
        pagingDataFlow = responseAlbum()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(500),
                replay = 1
            )
            .cachedIn(viewModelScope)
    }

    fun setPhoto(photo: PhotoItem, navHostController: NavHostController) {
        _photo.value = photo
        navHostController.navigate(
            GalleryNavRoute.PhotoItemScreen.withArgs(photo.id.toString())
        )
    }

    private fun responseAlbum(): Flow<PagingData<PhotoItem>> =
        galleryRepository.getAlbumStream()

}