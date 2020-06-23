package com.sunasterisk.fooddaily.data.source

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface RecipeDataSource {
    interface Remote {
        fun getRandomRecipes(callback: OnLoadedCallback<List<FoodDetail>>)
    }
    interface Local {
        fun getAllFavoriteFoods(callback: OnLoadedCallback<List<FoodDetail>>)
        fun getAllPartyFoods(callback: OnLoadedCallback<List<FoodDetail>>)
        fun getAllFamilyFoods(callback: OnLoadedCallback<List<FoodDetail>>)
        fun getAllCookingFoods(callback: OnLoadedCallback<List<FoodDetail>>)
        fun addToFavorite(foodDetail: FoodDetail)
        fun addToFamily(foodDetail: FoodDetail)
        fun addToParty(foodDetail: FoodDetail)
        fun addToCooking(foodDetail: FoodDetail)
        fun deleteFoodFromFavorite(foodDetail: FoodDetail)
        fun deleteFoodFromFamily(foodDetail: FoodDetail)
        fun deleteFoodFromParty(foodDetail: FoodDetail)
        fun deleteFoodFromCooking(foodDetail: FoodDetail)
    }
}
