package ru.konst.gallery.navigations

sealed class GalleryNavRoute(val path: String) {

    object GalleryScreen: GalleryNavRoute("gallery")
    object PhotoItemScreen: GalleryNavRoute("photo") {
        const val photoId = "photoId"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}