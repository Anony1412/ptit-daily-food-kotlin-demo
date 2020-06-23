package com.sunasterisk.fooddaily.ui.activity.food

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository

class FoodDetailPresenter(
    private val recipeRepository: RecipeRepository
): FoodDetailContract.Presenter {

    override fun addFoodToFavorite(food: FoodDetail) = recipeRepository.addToFavorite(food)

    override fun addFoodToFamily(food: FoodDetail) = recipeRepository.addToFamily(food)

    override fun addFoodToParty(food: FoodDetail) = recipeRepository.addToParty(food)

    override fun addFoodToCooking(food: FoodDetail) = recipeRepository.addToCooking(food)

    override fun deleteFoodFromCooking(food: FoodDetail) =
        recipeRepository.deleteFoodFromCooking(food)
}
