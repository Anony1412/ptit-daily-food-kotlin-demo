package com.sunasterisk.fooddaily.ui.fragment.home

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface HomeContract {
    interface View {
        fun showDailyMenu(dailyFoods: List<FoodDetail>)
    }

    interface Presenter {
        fun createDailyMenu(otherFoods: List<FoodDetail>)
    }
}
