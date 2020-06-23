package com.sunasterisk.fooddaily.data.repository

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import com.sunasterisk.fooddaily.data.source.RecipeDataSource

class RecipeRepository private constructor(
    private val remoteDataSource: RecipeDataSource.Remote,
    private val localDataSource: RecipeDataSource.Local
): RecipeDataSource.Remote, RecipeDataSource.Local {

    override fun getRandomRecipes(callback: OnLoadedCallback<List<FoodDetail>>) {
        remoteDataSource.getRandomRecipes(callback)
    }

    override fun getAllFavoriteFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        localDataSource.getAllFavoriteFoods(callback)
    }

    override fun getAllPartyFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        localDataSource.getAllPartyFoods(callback)
    }

    override fun getAllFamilyFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        localDataSource.getAllFamilyFoods(callback)
    }

    override fun getAllCookingFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        localDataSource.getAllCookingFoods(callback)
    }

    override fun addToFavorite(foodDetail: FoodDetail) {
        localDataSource.addToFavorite(foodDetail)
    }

    override fun addToFamily(foodDetail: FoodDetail) {
        localDataSource.addToFamily(foodDetail)
    }

    override fun addToParty(foodDetail: FoodDetail) {
        localDataSource.addToParty(foodDetail)
    }

    override fun addToCooking(foodDetail: FoodDetail) {
        localDataSource.addToCooking(foodDetail)
    }

    override fun deleteFoodFromFavorite(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromFamily(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromParty(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromCooking(foodDetail: FoodDetail) {
        localDataSource.deleteFoodFromCooking(foodDetail)
    }

    companion object {
        private var instance: RecipeRepository? = null
        fun getInstance(
            remoteDataSource: RecipeDataSource.Remote,
            localDataSource: RecipeDataSource.Local
        ): RecipeRepository =
            instance ?:
            RecipeRepository(
                remoteDataSource,
                localDataSource
            ).also { instance = it }
    }
}
