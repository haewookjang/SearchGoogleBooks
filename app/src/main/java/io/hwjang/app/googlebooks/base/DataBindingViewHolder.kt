package io.hwjang.app.googlebooks.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.hwjang.app.googlebooks.BR

class DataBindingViewHolder<T>(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }

}