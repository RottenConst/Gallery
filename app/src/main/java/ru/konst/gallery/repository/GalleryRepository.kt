package ru.konst.gallery.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.konst.gallery.data.PhotoItem
import ru.konst.gallery.network.ApiGallery

class GalleryRepository(private val apiGallery: ApiGallery) {

    fun getAlbumStream(): Flow<PagingData<PhotoItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(apiGallery)}
        ).flow
    }
}