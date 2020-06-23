package com.sunasterisk.fooddaily.data.source.local

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.source.OnLoadedCallback
import com.sunasterisk.fooddaily.data.source.RecipeDataSource
import com.sunasterisk.fooddaily.data.source.local.base.LocalAsyncTask
import com.sunasterisk.fooddaily.data.source.local.base.LocalDataHandler
import com.sunasterisk.fooddaily.data.source.local.dao.FoodDAO
import com.sunasterisk.fooddaily.utils.Constants

class RecipeLocalDataSource private constructor(
    private val foodDAO: FoodDAO
) : RecipeDataSource.Local {

    override fun getAllFavoriteFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        LocalAsyncTask(object: LocalDataHandler<String, List<FoodDetail>> {
            override fun execute(params: String): List<FoodDetail> = foodDAO.getAllFavoriteFoods()
        }, callback).execute(Constants.EMPTY_PARAMS)
    }

    override fun getAllPartyFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        LocalAsyncTask(object: LocalDataHandler<String, List<FoodDetail>> {
            override fun execute(params: String): List<FoodDetail> = foodDAO.getAllPartyFoods()
        }, callback).execute(Constants.EMPTY_PARAMS)
    }

    override fun getAllFamilyFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        LocalAsyncTask(object: LocalDataHandler<String, List<FoodDetail>> {
            override fun execute(params: String): List<FoodDetail> = foodDAO.getAllFamilyFoods()
        }, callback).execute(Constants.EMPTY_PARAMS)
    }

    override fun getAllCookingFoods(callback: OnLoadedCallback<List<FoodDetail>>) {
        LocalAsyncTask(object: LocalDataHandler<String, List<FoodDetail>> {
            override fun execute(params: String): List<FoodDetail> = foodDAO.getAllCookingFoods()
        }, callback).execute(Constants.EMPTY_PARAMS)
    }

    override fun addToFavorite(foodDetail: FoodDetail) {
        foodDAO.insertFoodFavorite(foodDetail)
    }

    override fun addToFamily(foodDetail: FoodDetail) {
        foodDAO.insertFoodFamily(foodDetail)
    }

    override fun addToParty(foodDetail: FoodDetail) {
        foodDAO.insertFoodParty(foodDetail)
    }

    override fun addToCooking(foodDetail: FoodDetail) {
        foodDAO.insertFoodCooking(foodDetail)
    }

    override fun deleteFoodFromFavorite(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromFamily(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromParty(foodDetail: FoodDetail) {

    }

    override fun deleteFoodFromCooking(foodDetail: FoodDetail) {
        foodDAO.deleteFoodCooking(foodDetail)
    }

    companion object {
        private var instance: RecipeDataSource.Local? = null
        fun getInstance(foodDAO: FoodDAO): RecipeDataSource.Local =
            instance ?: RecipeLocalDataSource(foodDAO).also { instance = it }
    }
}
