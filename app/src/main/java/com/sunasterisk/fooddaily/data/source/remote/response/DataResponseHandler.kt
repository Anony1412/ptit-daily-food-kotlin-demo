package com.sunasterisk.fooddaily.data.source.remote.response

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.Constants
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

interface DataResponseHandler {
    fun getResponse(urlRequest: String): List<FoodDetail>

    fun convertJSONToFood(strJSON: String, jsonKey: String): List<FoodDetail> {
        val foodDetails = mutableListOf<FoodDetail>()
        val recipes = JSONObject(strJSON).optJSONArray(jsonKey)
        for (i in 0 until recipes.length()) {
            val food = FoodDetail(recipes.optJSONObject(i))
            foodDetails.add(food)
        }
        return foodDetails
    }

    fun handleConnect(url: URL): String {
        var result = ""
        var urlConnection: HttpURLConnection? = null
        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.apply {
                doInput = true
                requestMethod = Constants.METHOD_GET
                connect()
            }
            val responseCode = urlConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val scanner = Scanner(url.openStream())
                while (scanner.hasNext()) {
                    result += scanner.nextLine()
                }
                scanner.close()
            } else {
                throw IOException()
            }
        } finally {
            urlConnection?.disconnect()
        }
        return result
    }
}
