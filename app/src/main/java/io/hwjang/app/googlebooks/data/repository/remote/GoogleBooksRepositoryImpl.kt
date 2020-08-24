package io.hwjang.app.googlebooks.data.repository.remote

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.hwjang.app.googlebooks.data.api.GoogleBooksService
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.data.paging.SearchDataSource
import io.hwjang.app.googlebooks.data.paging.SearchDataSourceFactory
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository
import io.hwjang.app.googlebooks.model.BookSearchResult
import javax.inject.Inject

class GoogleBooksRepositoryImpl @Inject constructor(
    private val googleBooksService: GoogleBooksService,
    private val config: PagedList.Config
) :
    GoogleBooksRepository {
    override fun search(q: String): BookSearchResult {
        val dataSource = SearchDataSource(googleBooksService, q)
        return BookSearchResult(
            LivePagedListBuilder<Int, Book>(SearchDataSourceFactory(dataSource), config).build()
            , dataSource.loading
            , dataSource.searchResultCount
            , dataSource.error
        )
    }

    override suspend fun getBookInfoById(id: String): Book {
        return googleBooksService.getBookInfoById(id)
    }
}