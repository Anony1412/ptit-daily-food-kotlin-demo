package com.sunasterisk.fooddaily.data.source.remote.response

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.Constants
import java.net.URL

class SearchResponseHandler private constructor(): DataResponseHandler {
    override fun getResponse(urlRequest: String): List<FoodDetail> {
        val strJSON = handleConnect(URL(urlRequest))
        return convertJSONToFood(strJSON, Constants.RESULTS)
    }

    companion object {
        private var instance: SearchResponseHandler? = null
        fun getInstance(): SearchResponseHandler =
            instance ?: SearchResponseHandler().also { instance = it }
    }
}
