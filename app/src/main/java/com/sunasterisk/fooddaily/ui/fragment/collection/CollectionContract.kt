package com.sunasterisk.fooddaily.ui.fragment.collection

import com.sunasterisk.fooddaily.data.model.FoodDetail

interface CollectionContract {

    interface View {
        fun createFavoriteFoods(favoriteFoods: List<FoodDetail>)
        fun createFamilyFoods(familyFoods: List<FoodDetail>)
        fun createPartyFoods(partyFoods: List<FoodDetail>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getAllFavoriteFoods()
        fun getAllFamilyFoods()
        fun getAllPartyFoods()
    }
}
