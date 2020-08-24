package io.hwjang.app.googlebooks.data.api

import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.data.model.GoogleBooks
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksService {

    companion object {
        const val BASE_URL = "https://www.googleapis.com"
        const val DEFAULT_PAGE_SIZE = 20
    }

    @GET("books/v1/volumes?projection=lite")
    suspend fun search(
        @Query("q") q: String,
        @Query("startIndex") start: Int = 0,
        @Query("maxResults") page: Int = DEFAULT_PAGE_SIZE
    ): GoogleBooks

    @GET("books/v1/volumes/{id}")
    suspend fun getBookInfoById(@Path("id") id: String): Book

}