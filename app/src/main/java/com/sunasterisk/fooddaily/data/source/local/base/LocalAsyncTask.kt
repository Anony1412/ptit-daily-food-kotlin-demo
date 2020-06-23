package com.sunasterisk.fooddaily.data.source.local.base

import android.os.AsyncTask
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback

private const val MESSAGE_NULL_RESULT =
    "com.sunasterisk.fooddaily.data.source.local.base.LocalAsyncTask.MESSAGE_NULL_RESULT"

class LocalAsyncTask<P, T> (
    private val handler: LocalDataHandler<P, T>,
    private val callback: OnLoadedCallback<T>
) : AsyncTask<P, Void, T?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: P): T? =
        try {
            handler.execute(params[0])
        } catch (exception: Exception) {
            this.exception = exception
            null
        }

    override fun onPostExecute(result: T?) {
        result?.let {
            callback.onSuccess(it)
        } ?: callback.onFailure(exception ?: Exception(MESSAGE_NULL_RESULT))
    }
}
