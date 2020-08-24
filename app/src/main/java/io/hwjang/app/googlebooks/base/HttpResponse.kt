package io.hwjang.app.googlebooks.base

sealed class HttpResponse<out T> {
    data class Success<out T>(val data: T) : HttpResponse<T>()
    data class Error(val exception: Exception) : HttpResponse<Nothing>()
    object Loading : HttpResponse<Nothing>()
}

fun <T> HttpResponse<T>.getOrNull(): T {
    return (this as HttpResponse.Success<T>).data
}

val <T> HttpResponse<T>.exception: java.lang.Exception
    get() = (this as HttpResponse.Error).exception

val <T> HttpResponse<T>.success: Boolean
    get() = (this is HttpResponse.Success<T>)


