package com.sunasterisk.fooddaily.data.source

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface SearchDataSource {
    interface Remote {
        fun searchRecipeComplex(keyword: String, callback: OnLoadedCallback<List<FoodDetail>>)
        fun searchRecipeById(foodId: String, callback: OnLoadedCallback<List<FoodDetail>>)
    }
}
