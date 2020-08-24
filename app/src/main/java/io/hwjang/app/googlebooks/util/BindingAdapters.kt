package io.hwjang.app.googlebooks.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.hwjang.app.googlebooks.R
import io.hwjang.app.googlebooks.data.model.ImageLinks

@BindingAdapter("loading")
fun loading(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@SuppressLint("CheckResult")
@BindingAdapter("load")
fun load(view: ImageView, url: String?) {
    if (url != null && url.isNotEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(20))
            .into(view)
    } else {
        Glide.with(view.context)
            .load(R.drawable.ic_book)
            .placeholder(null)
            //.transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageLinks: ImageLinks?) {
    val url = if (imageLinks?.small != null) {
        imageLinks.small
    } else {
        imageLinks?.thumbnail
    }
    load(view, url)
}

@BindingAdapter("authors")
fun authors(view: TextView, list: List<String>?) {
    view.text = list?.joinToString(",") ?: null
}


@BindingAdapter("toHtml")
fun toHtml(view: TextView, text: String?) {
    if (text != null) {
        view.text = text.htmlToString()
    } else {
        view.text = null
    }
}


