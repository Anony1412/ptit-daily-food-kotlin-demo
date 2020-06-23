package com.sunasterisk.fooddaily.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.SearchRepository
import com.sunasterisk.fooddaily.data.source.remote.SearchRemoteDataSource
import com.sunasterisk.fooddaily.ui.activity.food.FoodDetailActivity
import com.sunasterisk.fooddaily.ui.adapter.FoodAdapter
import com.sunasterisk.fooddaily.ui.base.BaseFragment
import com.sunasterisk.fooddaily.utils.FoodType
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.frame_loading.*

class SearchFragment : BaseFragment(), SearchContract.View {

    override val layoutRes: Int = R.layout.fragment_search
    private var searchKey: String? = null
    private val searchAdapter = FoodAdapter(FoodType.SEARCH_RESULT) { onSearchItemResultClick(it) }

    private var searchPresenter: SearchPresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        val animRotate = AnimationUtils.loadAnimation(activity, R.anim.anim_rotate)
        frameLoading.visibility = View.VISIBLE
        imageLoadSearchResult.startAnimation(animRotate)
        recyclerViewSearchResult.adapter = searchAdapter
    }

    override fun initActionBar() {
        textActionBarTitle.text = getString(R.string.title_search)
        buttonSearchActionBar.visibility = View.GONE
        buttonCollectionActionBar.visibility = View.GONE
    }

    override fun getArgument() {
        searchKey = arguments?.getString(ARGUMENT_SEARCH_KEY)
    }

    override fun initPresenter() {
        val searchRepository = SearchRepository.getInstance(SearchRemoteDataSource.getInstance())
        searchPresenter = SearchPresenter(this, searchRepository)
        searchKey?.let { searchPresenter?.searchRecipeComplex(it) }
    }

    private fun initListener() {
        buttonBackActionBar.setOnClickListener {
            removeFragment(this)
        }
    }

    override fun showRecipeComplex(recipes: List<FoodDetail>) {
        frameLoading.visibility = View.GONE
        searchAdapter.updateData(recipes)
    }

    override fun showRecipeById(recipes: List<FoodDetail>) {
        activity?.let {
            startActivityForResult(
                FoodDetailActivity.getIntent(it, recipes[0]),
                FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            )
        }
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun onSearchItemResultClick(food: FoodDetail) {
        searchPresenter?.searchRecipeById(food.id.toString())
    }

    companion object {
        private const val ARGUMENT_SEARCH_KEY = "ARGUMENT_SEARCH_KEY"
        fun newInstance(searchKey: String): SearchFragment = SearchFragment().apply {
            arguments = bundleOf(ARGUMENT_SEARCH_KEY to searchKey)
        }
    }
}
