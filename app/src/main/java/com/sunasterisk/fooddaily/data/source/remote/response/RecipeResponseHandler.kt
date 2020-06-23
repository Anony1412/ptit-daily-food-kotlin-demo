package com.sunasterisk.fooddaily.data.source.remote.response

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.Constants
import java.net.URL

class RecipeResponseHandler private constructor() : DataResponseHandler {
    override fun getResponse(urlRequest: String): List<FoodDetail> {
        val strJSON = handleConnect(URL(urlRequest))
        return convertJSONToFood(strJSON, Constants.RECIPES)
    }

    companion object {
        private var instance: RecipeResponseHandler? = null
        fun getInstance(): RecipeResponseHandler =
            instance ?: RecipeResponseHandler().also { instance = it }
    }
}
