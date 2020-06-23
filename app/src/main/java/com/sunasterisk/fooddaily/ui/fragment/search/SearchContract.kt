package com.sunasterisk.fooddaily.ui.fragment.search

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface SearchContract {

    interface View {
        fun showRecipeComplex(recipes: List<FoodDetail>)
        fun showRecipeById(recipes: List<FoodDetail>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun searchRecipeComplex(keyword: String)
        fun searchRecipeById(foodId: String)
    }
}
