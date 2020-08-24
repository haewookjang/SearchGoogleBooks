package io.hwjang.app.googlebooks.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.hwjang.app.googlebooks.base.HttpResponse
import io.hwjang.app.googlebooks.base.exception
import io.hwjang.app.googlebooks.base.getOrNull
import io.hwjang.app.googlebooks.base.success
import io.hwjang.app.googlebooks.data.api.GoogleBooksService
import io.hwjang.app.googlebooks.data.model.GoogleBooks
import io.hwjang.app.googlebooks.data.model.Book

class SearchDataSource constructor(val api: GoogleBooksService, private val query: String) :
    PageKeyedDataSource<Int, Book>() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading
    private val _searchResultCount = MutableLiveData(-1)
    val searchResultCount: LiveData<Int>
        get() = _searchResultCount
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {
        GlobalScope.launch {
            val response = load(0, query)
            if (!response.success) {
                _error.postValue(response.exception)
                return@launch
            }
            val result = response.getOrNull()
            _searchResultCount.postValue(result.totalItems)
            if (result.totalItems > 0) {
                callback.onResult(
                    result.books,
                    0,
                    result.totalItems,
                    null,
                    result.books.size
                )
            } else {
                callback.onResult(
                    listOf(),
                    0,
                    0,
                    null,
                    null
                )
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        GlobalScope.launch {
            val response = load(params.key, query)
            if (response.success) {
                response.getOrNull().books.let {
                    callback.onResult(it, params.key + it.size)
                }
            } else {
                _error.postValue(response.exception)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {

    }


    private fun setLoading(l: Boolean) {
        _loading.postValue(l)
    }

    suspend fun load(start: Int = 0, q: String): HttpResponse<GoogleBooks> =
        withContext(Dispatchers.IO) {
            try {
                setLoading(true)
                HttpResponse.Success(api.search(q, start))
            } catch (e: Exception) {
                HttpResponse.Error(e)
            } finally {
                setLoading(false)
            }
        }

}