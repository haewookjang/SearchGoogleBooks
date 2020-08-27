package io.hwjang.app.googlebooks.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import io.hwjang.app.googlebooks.MainCoroutineScopeRule
import io.hwjang.app.googlebooks.base.HttpResponse
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository
import io.hwjang.app.googlebooks.model.BookSearchResult
import io.hwjang.app.googlebooks.ui.detail.DetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.internal.verification.Times
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException
import java.net.UnknownHostException

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var googleBooksRepository: GoogleBooksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailViewModel(googleBooksRepository)
    }

    @Mock
    lateinit var searchResult: BookSearchResult

    private val api = object : GoogleBooksRepository {
        override fun search(q: String): BookSearchResult {
            return searchResult
        }

        @Throws(Exception::class)
        override suspend fun getBookInfoById(id: String): Book {
            return book
        }

    }


    @Mock
    lateinit var book: Book


    @Spy
    val vm = DetailViewModel(api)

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var bookObserver: Observer<Book>

    @Mock
    lateinit var errorObserver: Observer<Exception>


    @Mock
    lateinit var unknownHostException: UnknownHostException




    @Test
    fun test_get_book_success() {
        coroutineScope.launch {
            vm.book.observeForever(bookObserver)
            vm.load("android")
            assertEquals(book, vm.book.value)
            verify(bookObserver).onChanged(book)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_book_failure() {

        coroutineScope.runBlockingTest {
            val runtimeException = RuntimeException()
            `when`(vm.repository.getBookInfoById("android")).thenThrow(runtimeException)
            vm.load("android")
            vm.error.observeForever(errorObserver)
            verify(errorObserver).onChanged(runtimeException)
        }
    }

}