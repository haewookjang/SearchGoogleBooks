package io.hwjang.app.googlebooks.ui.search

import android.view.View
import io.hwjang.app.googlebooks.data.model.Book

interface BookDetailClickListener {
    fun onBookClick(book: Book, view: View)
}