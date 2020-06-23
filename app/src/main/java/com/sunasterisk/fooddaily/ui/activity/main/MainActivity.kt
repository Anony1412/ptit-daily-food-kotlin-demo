package com.sunasterisk.fooddaily.ui.activity.main

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.ui.base.BaseActivity
import com.sunasterisk.fooddaily.ui.fragment.collection.CollectionFragment
import com.sunasterisk.fooddaily.ui.fragment.cooking.CookingFragment
import com.sunasterisk.fooddaily.ui.fragment.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutRes: Int = R.layout.activity_main

    private lateinit var allFood: List<FoodDetail>
    private val bottomNavSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(HomeFragment.newInstance(allFood))
                true
            }
            R.id.navigation_collection -> {
                setFragment(CollectionFragment.newInstance())
                true
            }
            R.id.navigation_cooking -> {
                setFragment(CookingFragment.newInstance())
                true
            }
            else -> false
        }
    }

    override fun initView() {
        createFoodsAll()
        setFragment(HomeFragment.newInstance(allFood))
        initBottomNavigation()
    }

    override fun initPresenter() {}

    private fun createFoodsAll() {
        allFood = intent
            .getParcelableArrayListExtra<FoodDetail>(EXTRA_FOOD_LIST) as List<FoodDetail>
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavSelected)
    }

    companion object {

        private const val EXTRA_FOOD_LIST =
            "com.sunasterisk.fooddaily.ui.activity.main.KEY_FOOD_LIST"

        fun getIntent(context: Context, foods: List<FoodDetail>): Intent =
            Intent(context, MainActivity::class.java)
                .putParcelableArrayListExtra(EXTRA_FOOD_LIST, ArrayList(foods))
    }
}
