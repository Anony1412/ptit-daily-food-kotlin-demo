package com.sunasterisk.fooddaily.ui.fragment.collection

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback

class CollectionPresenter(
    private val view: CollectionContract.View,
    private val recipeRepository: RecipeRepository
): CollectionContract.Presenter {

    override fun getAllFavoriteFoods() {
        recipeRepository.getAllFavoriteFoods(object : OnLoadedCallback<List<FoodDetail>>{
            override fun onSuccess(data: List<FoodDetail>) {
                view.createFavoriteFoods(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }

    override fun getAllFamilyFoods() {
        recipeRepository.getAllFamilyFoods(object : OnLoadedCallback<List<FoodDetail>>{
            override fun onSuccess(data: List<FoodDetail>) {
                view.createFamilyFoods(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }

    override fun getAllPartyFoods() {
        recipeRepository.getAllPartyFoods(object : OnLoadedCallback<List<FoodDetail>>{
            override fun onSuccess(data: List<FoodDetail>) {
                view.createPartyFoods(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }
}
