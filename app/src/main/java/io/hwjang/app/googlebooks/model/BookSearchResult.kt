package io.hwjang.app.googlebooks.model


import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.hwjang.app.googlebooks.data.model.Book

data class BookSearchResult(
    val result: LiveData<PagedList<Book>>,
    val loading: LiveData<Boolean>,
    val searchResultCount: LiveData<Int>,
    val error: LiveData<Exception>
)