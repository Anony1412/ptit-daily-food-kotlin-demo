package com.sunasterisk.fooddaily.ui.fragment.cooking

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface CookingContract {
    
    interface View {
        fun showAllCookingFoods(cookingFoods: List<FoodDetail>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getAllCookingFoods()
        fun deleteFoodFromCooking(foodDetail: FoodDetail)
    }
}
