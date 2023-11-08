package ru.konst.gallery.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.konst.gallery.data.PhotoItem
import ru.konst.gallery.vm.GalleryViewModel

@Composable
fun GalleryScreen(galleryViewModel: GalleryViewModel, navHostController: NavHostController) {

    val listState = rememberLazyGridState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        PhotoCard(galleryViewModel.pagingDataFlow.collectAsLazyPagingItems(), listState) {
            galleryViewModel.setPhoto(it, navHostController)
        }
    }
}

@Composable
fun PhotoCard(photos: LazyPagingItems<PhotoItem>, listState: LazyGridState, getPhoto: (PhotoItem) -> Unit) {
    when(photos.loadState.refresh) {
        LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).matchParentSize()
                )
            }
        }
        is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "error",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {
            LazyVerticalGrid(columns = GridCells.Adaptive(150.dp), state = listState) {
                items(photos.itemCount) { photoIndex ->
                        GlideImage(
                            imageModel = {photos[photoIndex]?.thumbnailUrl},
                            loading = {
                                Box(modifier = Modifier.height(150.dp).width(150.dp)) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center).matchParentSize()
                                    )
                                }
                            },
                            requestOptions = { RequestOptions().diskCacheStrategy(
                                DiskCacheStrategy.DATA)
                            },
                            modifier = Modifier.clickable { photos[photoIndex]?.let { getPhoto(it) } }
                        )
                    }
        }
    }



    }
}