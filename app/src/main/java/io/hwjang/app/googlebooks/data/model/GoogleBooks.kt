package io.hwjang.app.googlebooks.data.model


import com.google.gson.annotations.SerializedName

data class GoogleBooks(
    @SerializedName("items")
    val books: List<Book>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("totalItems")
    val totalItems: Int
)