package io.hwjang.app.googlebooks.data.model


import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("medium")
    val medium: String
)