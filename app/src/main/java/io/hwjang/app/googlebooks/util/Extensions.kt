package io.hwjang.app.googlebooks.util

import android.os.Build
import android.text.Html
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun String.htmlToString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}

fun Fragment.toast(@StringRes resId: Int) {
    Toast.makeText(requireContext(), resId, Toast.LENGTH_LONG).show()
}


