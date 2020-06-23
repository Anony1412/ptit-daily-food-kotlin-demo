package com.sunasterisk.fooddaily.ui.fragment.home

import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.Constants

class HomePresenter (private val view: HomeContract.View): HomeContract.Presenter {
    override fun createDailyMenu(otherFoods: List<FoodDetail>) {

        val indexes = mutableListOf<Int>()
        view.showDailyMenu(
            mutableListOf<FoodDetail>().apply {
                for (i in otherFoods.indices) indexes.add(i)
                indexes.shuffle()
                for (i in 0 until Constants.VALUE_DAILY_MENU_SIZE_LIMIT) add(otherFoods[indexes[i]])
            }
        )
    }
}
