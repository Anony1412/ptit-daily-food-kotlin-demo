package com.sunasterisk.fooddaily.ui.activity.splash

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback

class SplashScreenPresenter(
    private val splashView: SplashScreenContract.View,
    private val recipeRepository: RecipeRepository
): SplashScreenContract.Presenter {

    override fun getRandomFoods() {
        recipeRepository.getRandomRecipes(object: OnLoadedCallback<List<FoodDetail>> {
            override fun onSuccess(data: List<FoodDetail>) {
                splashView.onTransportDataToHome(data)
            }

            override fun onFailure(exception: Exception) {
                splashView.showError(exception)
            }
        })
    }
}
