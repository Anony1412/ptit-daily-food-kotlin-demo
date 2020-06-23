package com.sunasterisk.fooddaily.ui.activity.splash

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface SplashScreenContract {
    interface View {
        fun onTransportDataToHome(data: List<FoodDetail>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getRandomFoods()
    }
}
