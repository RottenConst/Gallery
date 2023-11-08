package ru.konst.gallery.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.konst.gallery.data.PhotoItem
import ru.konst.gallery.network.ApiGallery

private const val STARTING_ALBUM_ID = 1
class GalleryPagingSource(
    private val apiGallery: ApiGallery
): PagingSource<Int, PhotoItem>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        return try {
            val position = params.key ?: STARTING_ALBUM_ID
            val response = apiGallery.getPhotos(position)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_ALBUM_ID) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}