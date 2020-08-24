package io.hwjang.app.googlebooks.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import io.hwjang.app.googlebooks.base.*
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository

class DetailViewModel @ViewModelInject constructor(val repository: GoogleBooksRepository) :
    BaseViewModel() {
    val book = MutableLiveData<Book>()
    @ExperimentalCoroutinesApi
    fun load(id: String?) {
        id?.let {
            viewModelScope.launch {
                getBookInfo(it)
                    .collect {
                        book.postValue(it.getOrNull())
                    }
            }
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getBookInfo(id: String) = httpGet {
        repository.getBookInfoById(id)
    }.map {
        setLoading(it is HttpResponse.Loading)
        it
    }.map {
        if (it is HttpResponse.Error) {
            onError(it.exception)
        }
        it
    }.filter {
        it is HttpResponse.Success
    }
}