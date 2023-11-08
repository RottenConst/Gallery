package ru.konst.gallery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.konst.gallery.R
import ru.konst.gallery.data.PhotoItem
import ru.konst.gallery.vm.GalleryViewModel

@Composable
fun PhotoItemScreen(galleryViewModel: GalleryViewModel) {
    val photo by galleryViewModel.photo.observeAsState()
    PhotoCard(photo = photo)
}

@Composable
fun PhotoCard(photo: PhotoItem?) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .padding(8.dp)
        ) {
            GlideImage(
                imageModel = { photo?.url },
                loading = {
                    Box {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .matchParentSize()
                        )
                    }
                },
                previewPlaceholder = R.drawable.ic_launcher_background,
                failure = {
                    Box(
                        modifier = Modifier
                            .background(Color.Red)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Text(
            text = photo?.title ?: "error",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
fun PhotoItemScreenPreview() {
    val photo = PhotoItem(
        albumId = 1,
        id = 1,
        title = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://via.placeholder.com/600/92c952",
        thumbnailUrl = "https://via.placeholder.com/150/92c952"
    )
    PhotoCard(photo = photo)
}