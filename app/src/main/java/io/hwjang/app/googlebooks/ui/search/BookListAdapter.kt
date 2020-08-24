package io.hwjang.app.googlebooks.ui.search

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.hwjang.app.googlebooks.BR
import io.hwjang.app.googlebooks.R
import io.hwjang.app.googlebooks.base.BaseDataBindingAdapter
import io.hwjang.app.googlebooks.data.model.Book
import io.hwjang.app.googlebooks.databinding.ListItemBookBinding

class BookListAdapter constructor(private val listener: BookDetailClickListener) :
    BaseDataBindingAdapter<Book>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldBook: Book, newBook: Book): Boolean {
            return oldBook.id == newBook.id
        }

        override fun areContentsTheSame(oldBook: Book, newBook: Book): Boolean {
            return oldBook.id == newBook.id
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item_book
    }

    override fun initViewDataBinding(binding: ViewDataBinding) {
        binding.setVariable(BR.clickListener, listener)
    }

    override fun bind(binding: ViewDataBinding, item: Book) {
        (binding as ListItemBookBinding).apply {
            bookImage.transitionName = item.id
            bookTitle.transitionName = item.volumeInfo.title
        }
    }
}



