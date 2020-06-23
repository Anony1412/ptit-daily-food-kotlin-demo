package com.sunasterisk.fooddaily.data.source

import java.lang.Exception

interface OnLoadedCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(exception: Exception)
}
