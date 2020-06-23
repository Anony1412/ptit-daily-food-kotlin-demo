package com.sunasterisk.fooddaily.ui.fragment.cooking

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback

class CookingPresenter (
    private val view: CookingContract.View,
    private val recipeRepository: RecipeRepository
): CookingContract.Presenter{

    override fun getAllCookingFoods() {
        recipeRepository.getAllCookingFoods(object : OnLoadedCallback<List<FoodDetail>>{

            override fun onSuccess(data: List<FoodDetail>) {
                view.showAllCookingFoods(data)
            }

            override fun onFailure(exception: Exception) {
                view.showError(exception)
            }
        })
    }

    override fun deleteFoodFromCooking(foodDetail: FoodDetail) {
        recipeRepository.deleteFoodFromCooking(foodDetail)
    }
}
