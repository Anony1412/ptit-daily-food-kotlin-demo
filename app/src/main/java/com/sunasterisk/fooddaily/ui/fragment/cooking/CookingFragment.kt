package com.sunasterisk.fooddaily.ui.fragment.cooking

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.local.RecipeLocalDataSource
import com.sunasterisk.fooddaily.data.source.local.dao.FoodDAOImpl
import com.sunasterisk.fooddaily.data.source.local.database.FoodDailyDatabase
import com.sunasterisk.fooddaily.data.source.remote.RecipeRemoteDataSource
import com.sunasterisk.fooddaily.ui.activity.food.FoodDetailActivity
import com.sunasterisk.fooddaily.ui.adapter.FoodAdapter
import com.sunasterisk.fooddaily.ui.base.BaseFragment
import com.sunasterisk.fooddaily.utils.FoodType
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.fragment_cooking.*

class CookingFragment : BaseFragment(), CookingContract.View {

    override val layoutRes: Int = R.layout.fragment_cooking

    private var cookingPresenter: CookingPresenter? = null
    private val cookingAdapter = FoodAdapter(FoodType.COOKING) {
        onFoodItemClick(it)
    }.apply {
        onClickDelete = ::onFoodItemDelete
    }
    override fun initActionBar() {
        textActionBarTitle.text = getString(R.string.title_cooking)
        buttonBackActionBar.visibility = View.GONE
        buttonCollectionActionBar.visibility = View.GONE
        buttonSearchActionBar.visibility = View.GONE
    }

    override fun getArgument() {

    }

    override fun initPresenter() {
        val foodDailyDatabase = context?.let { FoodDailyDatabase.getInstance(it) }
        foodDailyDatabase?.let {
            val foodDAO = FoodDAOImpl.getInstance(it)
            val recipeRepository = RecipeRepository.getInstance(
                RecipeRemoteDataSource.getInstance(),
                RecipeLocalDataSource.getInstance(foodDAO)
            )
            cookingPresenter = CookingPresenter(this, recipeRepository)
        }
        cookingPresenter?.run {
            getAllCookingFoods()
        }
    }

    override fun showAllCookingFoods(cookingFoods: List<FoodDetail>) {
        recyclerViewCookingFood.adapter = cookingAdapter
        cookingAdapter.updateData(cookingFoods)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(
            activity,
            getString(R.string.notification_update_data),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            && resultCode == Activity.RESULT_OK
        ) {
            val foodDetail = data?.getParcelableExtra<FoodDetail>(FoodDetailActivity.RESULT_FOOD_ID)
            foodDetail?.let { cookingAdapter.deleteItem(it) }
        }
    }

    private fun onFoodItemClick(food: FoodDetail) {
        activity?.let {
            startActivityForResult(
                FoodDetailActivity.getIntent(it, food),
                FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            )
        }
    }

    private fun onFoodItemDelete(food: FoodDetail) {
        cookingPresenter?.deleteFoodFromCooking(food)
        cookingAdapter.deleteItem(food)
    }

    companion object {
        fun newInstance(): CookingFragment = CookingFragment()
    }
}
