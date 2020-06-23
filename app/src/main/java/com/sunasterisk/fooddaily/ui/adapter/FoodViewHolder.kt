package com.sunasterisk.fooddaily.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.utils.applyGlide
import kotlinx.android.synthetic.main.item_daily_menu_food.view.*
import kotlinx.android.synthetic.main.item_food_collection.view.*
import kotlinx.android.synthetic.main.item_other_food_grid.view.*
import kotlinx.android.synthetic.main.item_other_food_list.view.*
import kotlinx.android.synthetic.main.item_search_result.view.*

abstract class FoodViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit
) : RecyclerView.ViewHolder(view) {

    protected var foodDetail: FoodDetail? = null
    init {
        itemView.setOnClickListener {
            foodDetail?.let { onClickListener(it) }
        }
    }
    abstract fun bind(food: FoodDetail)
}

class DailyMenuViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit
) : FoodViewHolder(view, onClickListener) {

    override fun bind(food: FoodDetail) {
        foodDetail = food
        itemView.apply {
            textFoodNameDailyMenu.text = food.title
            textDailyMenuPrice.text = food.price
            textDailyMenuReadyMinutes.text = food.getRequiredTime()
            imageFoodDailyMenu.applyGlide(food.imageUrl)
        }
    }
}

class OtherFoodListViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit
) : FoodViewHolder(view, onClickListener) {

    override fun bind(food: FoodDetail) {
        foodDetail = food
        itemView.apply {
            textFoodNameOtherList.text = food.title
            textFoodNameOtherList.isSelected = true
            textOtherListPrice.text = food.price
            textOtherListReadyMinutes.text = food.readyMinutes
            imageFoodOtherList.applyGlide(food.imageUrl)
        }
    }
}

class OtherFoodGridViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit
) : FoodViewHolder(view, onClickListener) {

    override fun bind(food: FoodDetail) {
        foodDetail = food
        itemView.apply {
            textFoodOtherGrid.text = food.title
            textOtherGridPrice.text = food.price
            textOtherGridReadyMinutes.text = food.readyMinutes
            imageFoodOtherGrid.applyGlide(food.imageUrl)
        }
    }
}

class CookingFoodViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit,
    private val onClickDelete: (FoodDetail) -> Unit
) : FoodViewHolder(view, onClickListener) {

    override fun bind(food: FoodDetail) {
        foodDetail = food
        itemView.apply {
            textFoodNameCollection.text = food.title
            textFoodNameCollection.isSelected = true
            textFoodCollectionPrice.text = food.price
            textFoodCollectionReadyMinutes.text = food.readyMinutes
            imageFoodCollection.applyGlide(food.imageUrl)
            buttonDeleteFood.setOnClickListener {
                onClickDelete(food)
            }
        }
    }
}

class SearchResultFoodViewHolder(
    view: View,
    onClickListener: (FoodDetail) -> Unit
) : FoodViewHolder(view, onClickListener) {

    override fun bind(food: FoodDetail) {
        foodDetail = food
        itemView.apply {
            textFoodNameSearchResult.text = food.title
            textFoodNameSearchResult.isSelected = true
            imageFoodSearchResult.applyGlide(food.imageUrl)
        }
    }
}
