package io.hwjang.app.googlebooks.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

@Suppress("UNCHECKED_CAST")
abstract class BaseDataBindingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        initViewDataBinding(binding)
        return DataBindingViewHolder(binding)
    }

    open fun initViewDataBinding(binding: ViewDataBinding) {

    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        getItem(position)?.let {
            bind(holder.binding, it)
            holder.bind(it)
        }
    }

    abstract fun bind(binding: ViewDataBinding, item: T)
}