package com.sunasterisk.fooddaily.data.source.remote.response

import android.os.AsyncTask
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback

class GetResponsesAsync(
    private val dataResponseHandler: DataResponseHandler,
    private val callback: OnLoadedCallback<List<FoodDetail>>
) : AsyncTask<String, Void, List<FoodDetail>>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: String): List<FoodDetail>? =
        //TODO("xu ly request den api lay data ve parse sang food model")
        try {
            dataResponseHandler.getResponse(params[0])
        } catch (exception: Exception) {
            this.exception = exception
            null
        }

    override fun onPostExecute(result: List<FoodDetail>?) {
        super.onPostExecute(result)
        result?.let {
            callback.onSuccess(it)
        } ?: exception?.let {
            callback.onFailure(it)
        }
    }
}
