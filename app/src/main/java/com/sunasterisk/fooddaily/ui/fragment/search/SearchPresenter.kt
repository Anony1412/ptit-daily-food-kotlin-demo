package com.sunasterisk.fooddaily.ui.fragment.search

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.SearchRepository
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import java.lang.Exception

class SearchPresenter(
    private val view: SearchContract.View,
    private val searchRepository: SearchRepository
): SearchContract.Presenter {

    override fun searchRecipeComplex(keyword: String) {
        searchRepository.searchRecipeComplex(keyword, object: OnLoadedCallback<List<FoodDetail>>{
            override fun onSuccess(data: List<FoodDetail>) {
                view.showRecipeComplex(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }

    override fun searchRecipeById(foodId: String) {
        searchRepository.searchRecipeById(foodId, object: OnLoadedCallback<List<FoodDetail>>{
            override fun onSuccess(data: List<FoodDetail>) {
                view.showRecipeById(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }
}
