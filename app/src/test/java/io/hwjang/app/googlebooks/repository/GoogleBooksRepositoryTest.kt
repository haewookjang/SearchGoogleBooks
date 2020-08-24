package io.hwjang.app.googlebooks.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import io.hwjang.app.googlebooks.MainCoroutineScopeRule
import io.hwjang.app.googlebooks.data.api.GoogleBooksService
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.data.model.GoogleBooks
import io.hwjang.app.googlebooks.domain.repository.GoogleBooksRepository
import io.hwjang.app.googlebooks.model.BookSearchResult
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GoogleBooksRepositoryTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var googleBooksService: GoogleBooksService

    @Mock
    lateinit var googleBooksRepository: GoogleBooksRepository

    @Spy
    val bookSearchResult =
        BookSearchResult(
            MutableLiveData(),
            MutableLiveData<Boolean>(true),
            MutableLiveData<Int>(100),
            MutableLiveData<Exception>()
        )

    @Spy
    val googleBooks = GoogleBooks(listOf(), "book", 200)

    @Mock
    lateinit var book: Book


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(googleBooksRepository.search("android")).thenReturn(bookSearchResult)
    }

    @Test
    fun test_api_google_books_service_search() {
        coroutineScope.runBlockingTest {
            Mockito.`when`(googleBooksService.search("android")).thenReturn(googleBooks)
            val result = googleBooksService.search("android")
            Mockito.verify(googleBooksService).search("android")
            assertEquals(result, googleBooks)
        }

    }

    @Test
    fun test_api_google_books_service_get_book() {
        coroutineScope.runBlockingTest {
            Mockito.`when`(googleBooksService.getBookInfoById("android")).thenReturn(book)
            val result = googleBooksService.getBookInfoById("android")
            Mockito.verify(googleBooksService).getBookInfoById("android")
            assertEquals(result, book)
        }

    }

    @Test
    fun test_google_books_repository_search() {
        coroutineScope.runBlockingTest {
            val result = googleBooksRepository.search("android")
            Mockito.verify(googleBooksRepository).search("android")
            assertEquals(result, bookSearchResult)
        }
    }


}