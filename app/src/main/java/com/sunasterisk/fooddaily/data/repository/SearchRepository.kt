package com.sunasterisk.fooddaily.data.repository

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import com.sunasterisk.fooddaily.data.source.SearchDataSource

class SearchRepository private constructor(
    private val remoteDataSource: SearchDataSource.Remote
): SearchDataSource.Remote {
    override fun searchRecipeComplex(
        keyword: String,
        callback: OnLoadedCallback<List<FoodDetail>>
    ) {
        remoteDataSource.searchRecipeComplex(keyword, callback)
    }

    override fun searchRecipeById(foodId: String, callback: OnLoadedCallback<List<FoodDetail>>) {
        remoteDataSource.searchRecipeById(foodId, callback)
    }

    companion object {
        private var instance: SearchRepository? = null
        fun getInstance(remoteDataSource: SearchDataSource.Remote): SearchRepository =
            instance ?: SearchRepository(remoteDataSource).also { instance = it }
    }
}
