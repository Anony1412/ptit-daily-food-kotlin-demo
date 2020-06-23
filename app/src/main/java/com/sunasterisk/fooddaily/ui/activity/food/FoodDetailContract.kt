package com.sunasterisk.fooddaily.ui.activity.food

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface FoodDetailContract {
    interface Presenter {
        fun addFoodToFavorite(food: FoodDetail)
        fun addFoodToFamily(food: FoodDetail)
        fun addFoodToParty(food: FoodDetail)
        fun addFoodToCooking(food: FoodDetail)
        fun deleteFoodFromCooking(food: FoodDetail)
    }
}
