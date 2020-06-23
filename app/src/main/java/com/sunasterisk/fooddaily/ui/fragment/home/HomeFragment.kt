package com.sunasterisk.fooddaily.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.ui.activity.food.FoodDetailActivity
import com.sunasterisk.fooddaily.ui.adapter.FoodAdapter
import com.sunasterisk.fooddaily.ui.base.BaseFragment
import com.sunasterisk.fooddaily.ui.fragment.search.SearchFragment
import com.sunasterisk.fooddaily.utils.FoodType
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.search_view.*

const val ITEM_GRID_WIDTH = 152

class HomeFragment :
    BaseFragment(),
    View.OnClickListener,
    HomeContract.View,
    SearchView.OnQueryTextListener {

    override val layoutRes: Int = R.layout.fragment_home

    private val homePresenter = HomePresenter(this)
    private var otherFoods: List<FoodDetail>? = null
    private var isSortList: Boolean = false
    private var isShowFrameSearch = false

    override fun initActionBar() {
        textActionBarTitle.text = getString(R.string.title_home)
        buttonSearchActionBar.setOnClickListener(this)
        buttonBackActionBar.visibility = View.GONE
        buttonCollectionActionBar.visibility = View.GONE
    }

    override fun getArgument() {
        otherFoods = arguments?.getParcelableArrayList(ARGUMENT_FOOD_LIST)
    }

    override fun initPresenter() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otherFoods?.let { homePresenter.createDailyMenu(it) }
        displayOtherFoodList()
        buttonSortOtherFood.setOnClickListener(this)
    }

    override fun showDailyMenu(dailyFoods: List<FoodDetail>) {
        recyclerViewDailyMenu.apply {
            adapter =
                FoodAdapter(FoodType.DAILY_MENU) { food ->
                    onFoodItemClick(food)
                }.apply {
                    updateData(dailyFoods)
                }
            setHasFixedSize(false)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            switchByAddFragment(SearchFragment.newInstance(it))
            hideFrameSearch()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSortOtherFood -> displayOtherFood()
            R.id.buttonSearchActionBar -> controlDisplayFrameSearch()
            R.id.frameSearch -> controlDisplayFrameSearch()
        }
    }

    private fun displayOtherFood() {
        val icon = if (isSortList) {
            displayOtherFoodGrid()
            R.drawable.ic_list
        } else {
            displayOtherFoodList()
            R.drawable.ic_grid
        }
        buttonSortOtherFood.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
    }

    private fun displayOtherFoodList() {
        recyclerViewOtherFood.apply {
            adapter = otherFoods?.let {
                FoodAdapter(FoodType.OTHER_LIST) { food ->
                    onFoodItemClick(food)
                }.apply {
                    updateData(it)
                }
            }
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }
        isSortList = true
    }

    private fun displayOtherFoodGrid() {
        recyclerViewOtherFood.apply {
            adapter =
                otherFoods?.let {
                    FoodAdapter(FoodType.OTHER_GRID) { food ->
                        onFoodItemClick(food)
                    }.apply {
                        updateData(it)
                    }
                }
            layoutManager = GridLayoutManager(context, columnFitScreen())
        }
        isSortList = false
    }

    private fun columnFitScreen(): Int {
        var value = 0
        val displayMetrics = context?.resources?.displayMetrics
        displayMetrics?.let {
            val screenWidthDp = it.widthPixels / it.density
            value = (screenWidthDp / (ITEM_GRID_WIDTH + 0.5)).toInt()
        }
        return value
    }

    private fun onFoodItemClick(food: FoodDetail) {
        activity?.let {
            startActivityForResult(
                FoodDetailActivity.getIntent(it, food),
                FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            )
        }
    }

    private fun controlDisplayFrameSearch() {
        isShowFrameSearch = if (isShowFrameSearch) {
            hideFrameSearch()
            false
        } else {
            displayFrameSearch()
            true
        }
    }

    private fun hideFrameSearch() {
        searchViewHomeScreen.setQuery(null, false)
        frameSearch.visibility = View.GONE
        isShowFrameSearch = false
    }

    private fun displayFrameSearch() {
        frameSearch.visibility = View.VISIBLE
        frameSearch.setOnClickListener(this)
        searchViewHomeScreen.setOnQueryTextListener(this)
    }

    companion object {
        private const val ARGUMENT_FOOD_LIST = "ARGUMENT_FOOD_LIST"
        fun newInstance(foods: List<FoodDetail>): HomeFragment = HomeFragment().apply {
            arguments = bundleOf(ARGUMENT_FOOD_LIST to ArrayList(foods))
        }
    }
}
