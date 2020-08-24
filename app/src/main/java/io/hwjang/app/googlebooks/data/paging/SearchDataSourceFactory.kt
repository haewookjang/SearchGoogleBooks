package io.hwjang.app.googlebooks.data.paging

import androidx.paging.DataSource
import io.hwjang.app.googlebooks.data.model.Book

class SearchDataSourceFactory(private val dataSource: SearchDataSource) : DataSource.Factory<Int, Book>() {
    override fun create(): DataSource<Int, Book> {
        return dataSource
    }

}