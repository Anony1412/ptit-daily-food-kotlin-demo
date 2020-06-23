package com.sunasterisk.fooddaily.data.source.remote.response

import com.sunasterisk.fooddaily.data.model.FoodDetail
import org.json.JSONObject
import java.net.URL

class RecipeByIdResponseHandler private constructor(): DataResponseHandler {
    override fun getResponse(urlRequest: String): List<FoodDetail> {
        val strJSON = handleConnect(URL(urlRequest))
        return convertJSONToFood(strJSON)
    }

    /**
     * because get recipe by id only return 1 element
     * but getResponse request return type is List
     * -> create function convertJSONToFood return List have size = 1
     */
    private fun convertJSONToFood(strJSON: String): List<FoodDetail> {
        val foodDetails = mutableListOf<FoodDetail>()
        val recipe = JSONObject(strJSON)
        val food = FoodDetail(recipe)
        foodDetails.add(food)
        return foodDetails
    }

    companion object {
        private var instance: RecipeByIdResponseHandler? = null
        fun getInstance(): RecipeByIdResponseHandler =
            instance ?: RecipeByIdResponseHandler().also { instance = it }
    }
}
