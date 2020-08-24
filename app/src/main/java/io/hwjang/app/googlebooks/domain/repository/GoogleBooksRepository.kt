package io.hwjang.app.googlebooks.domain.repository

import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.model.BookSearchResult

interface GoogleBooksRepository {
    fun search(q: String): BookSearchResult

    suspend fun getBookInfoById(id: String): Book
}
