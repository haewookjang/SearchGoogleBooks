package io.hwjang.app.googlebooks.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.hwjang.app.googlebooks.base.BaseViewModel
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository

class SearchViewModel @ViewModelInject constructor(
    private val googleBooksRepository: GoogleBooksRepository
) : BaseViewModel() {

    private val _bookId = MutableLiveData<String>()
    val bookId: LiveData<String>
        get() = _bookId

    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query

    private val searchResult = Transformations.map(_query) {
        googleBooksRepository.search(it)
    }
    val searchResultList = Transformations.switchMap(searchResult) {
        it.result
    }
    val searchResultCount = Transformations.switchMap(searchResult) {
        it.searchResultCount
    }

    override var loading = Transformations.switchMap(searchResult) {
        it.loading
    }
    override val error = Transformations.switchMap(searchResult) {
        it.error
    }

    init {

    }

    fun search(q: String?) {
        q?.let {
            _query.postValue(it)
        }
    }
}