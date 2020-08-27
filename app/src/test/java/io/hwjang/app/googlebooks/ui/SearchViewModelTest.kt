package io.hwjang.app.googlebooks.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import io.hwjang.app.googlebooks.MainCoroutineScopeRule
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository
import io.hwjang.app.googlebooks.model.BookSearchResult
import io.hwjang.app.googlebooks.ui.search.SearchViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var googleBooksRepository: GoogleBooksRepository

    private val unknownHostException = UnknownHostException()

    @Spy
    val bookSearchResult =
        BookSearchResult(
            MutableLiveData(),
            MutableLiveData<Boolean>(true),
            MutableLiveData<Int>(100),
            MutableLiveData<Exception>(unknownHostException)
        )

    @Mock
    lateinit var book: Book

    lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var searchResultTotalCountObserver: Observer<Int>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<Exception>

    @Mock
    lateinit var bookInfoObserver: Observer<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(googleBooksRepository.search("android")).thenReturn(bookSearchResult)
        viewModel = SearchViewModel(googleBooksRepository)
    }


    @Test
    fun test_google_books_repository_get_book() {
        coroutineScope.runBlockingTest {
            `when`(googleBooksRepository.getBookInfoById("android")).thenReturn(book)
            val result = googleBooksRepository.getBookInfoById("android")
            verify(googleBooksRepository).getBookInfoById("android")
            assertEquals(result, book)
        }
    }

    @Test
    fun test_show_loading() {
        viewModel.loading.observeForever(loadingObserver)
        viewModel.search("android")
        verify(loadingObserver).onChanged(true)
    }

    @Test
    fun test_search_result_total_count() {
        viewModel.searchResultCount.observeForever(searchResultTotalCountObserver)
        viewModel.search("android")
        assertEquals(100, viewModel.searchResultCount.value)
        verify(searchResultTotalCountObserver).onChanged(100)
    }

    @Test
    fun test_search_error() {
        viewModel.error.observeForever(errorObserver)
        viewModel.search("android")
        assertEquals(unknownHostException, viewModel.error.value)
        verify(errorObserver).onChanged(unknownHostException)
    }



}