package io.hwjang.app.googlebooks.ui.search

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.hwjang.app.googlebooks.R
import io.hwjang.app.googlebooks.base.BaseDataBindingFragment
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment :
    BaseDataBindingFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels()

    private val clickListener = object : BookDetailClickListener {
        override fun onBookClick(book: Book, view: View) {
            showBookInfo(book, view)
        }
    }
    private val bookListAdapter: BookListAdapter by lazy {
        BookListAdapter(clickListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        viewModel.searchResultList.observe(this, Observer {
            bookListAdapter.submitList(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun showBookInfo(book: Book, view: View) {
        val imageUri = book.volumeInfo.imageLinks?.thumbnail ?: ""
        val action =
            SearchFragmentDirections.searchToDetail(imageUri, book.id, book.volumeInfo.title)
        val imageView = view.findViewById<ImageView>(R.id.book_image)
        val titleView = view.findViewById<TextView>(R.id.book_title)
        val extras = FragmentNavigatorExtras(
            imageView to book.id,
            titleView to book.volumeInfo.title

        )
        findNavController().navigate(action, extras)
    }

    override fun initDataBinding() {
        binding.list.apply {
            adapter = bookListAdapter

        }
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismissKeyboard(this@apply)
                    viewModel.search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

        binding.searchView.setOnClickListener {v->
            v.requestFocus()
            v.requestFocusFromTouch()
        }

    }

    private fun dismissKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
