package com.sunasterisk.fooddaily.data.source.remote

import com.sunasterisk.fooddaily.BuildConfig
import com.sunasterisk.fooddaily.data.model.DataRequest
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import com.sunasterisk.fooddaily.data.source.SearchDataSource
import com.sunasterisk.fooddaily.data.source.remote.response.GetResponsesAsync
import com.sunasterisk.fooddaily.data.source.remote.response.RecipeByIdResponseHandler
import com.sunasterisk.fooddaily.data.source.remote.response.SearchResponseHandler
import com.sunasterisk.fooddaily.utils.ApiKeys

class SearchRemoteDataSource private constructor() : SearchDataSource.Remote {

    override fun searchRecipeComplex(keyword: String, callback: OnLoadedCallback<List<FoodDetail>>) {
        val urlRequest = DataRequest(
            paths = listOf(
                ApiKeys.PATH_RECIPES,
                ApiKeys.PATH_COMPLEX_SEARCH
            ),
            queryParams = mapOf(
                ApiKeys.QUERY_KEYWORD to keyword,
                ApiKeys.QUERY_NUMBER to ApiKeys.DEFAULT_SEARCH_RESULT_LIMIT,
                ApiKeys.QUERY_API_KEY to BuildConfig.API_KEY
            )
        ).toUrl()
        GetResponsesAsync(SearchResponseHandler.getInstance(), callback).execute(urlRequest)
    }

    override fun searchRecipeById(foodId: String, callback: OnLoadedCallback<List<FoodDetail>>) {
        val urlRequest = DataRequest(
            paths = listOf(
                ApiKeys.PATH_RECIPES,
                foodId,
                ApiKeys.PATH_INFORMATION
            ),
            queryParams = mapOf(
                ApiKeys.QUERY_NUTRITION to ApiKeys.DEFAULT_NUTRITION,
                ApiKeys.QUERY_API_KEY to BuildConfig.API_KEY
            )
        ).toUrl()
        GetResponsesAsync(RecipeByIdResponseHandler.getInstance(), callback).execute(urlRequest)
    }

    companion object {
        private var instance: SearchDataSource.Remote? = null
        fun getInstance(): SearchDataSource.Remote =
            instance ?: SearchRemoteDataSource().also { instance = it }
    }
}
