package com.sunasterisk.fooddaily.data.source.remote

import android.util.Log
import com.sunasterisk.fooddaily.BuildConfig
import com.sunasterisk.fooddaily.data.model.DataRequest
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import com.sunasterisk.fooddaily.data.source.RecipeDataSource
import com.sunasterisk.fooddaily.data.source.remote.response.GetResponsesAsync
import com.sunasterisk.fooddaily.data.source.remote.response.RecipeResponseHandler
import com.sunasterisk.fooddaily.utils.ApiKeys

class RecipeRemoteDataSource private constructor() : RecipeDataSource.Remote {
    override fun getRandomRecipes(callback: OnLoadedCallback<List<FoodDetail>>) {

        val urlRequest = DataRequest(
            paths = listOf(
                ApiKeys.PATH_RECIPES,
                ApiKeys.PATH_RANDOM
            ),
            queryParams = mapOf(
                ApiKeys.QUERY_NUMBER to ApiKeys.DEFAULT_RANDOM_RESULT_LIMIT,
                ApiKeys.QUERY_LIMIT_LICENSE to ApiKeys.DEFAULT_RESULT_LIMIT_LICENSE,
                ApiKeys.QUERY_API_KEY to BuildConfig.API_KEY
            )
        ).toUrl()
        Log.d("TAG", urlRequest)
        GetResponsesAsync(RecipeResponseHandler.getInstance(), callback).execute(urlRequest)
    }

    companion object {
        private var instance: RecipeDataSource.Remote? = null
        fun getInstance(): RecipeDataSource.Remote =
            instance ?: RecipeRemoteDataSource().also { instance = it }
    }
}
