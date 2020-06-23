package com.sunasterisk.fooddaily.ui.fragment.collection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.Collection
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.local.RecipeLocalDataSource
import com.sunasterisk.fooddaily.data.source.local.dao.FoodDAOImpl
import com.sunasterisk.fooddaily.data.source.local.database.FoodDailyDatabase
import com.sunasterisk.fooddaily.data.source.remote.RecipeRemoteDataSource
import com.sunasterisk.fooddaily.ui.activity.food.FoodDetailActivity
import com.sunasterisk.fooddaily.ui.adapter.CollectionAdapter
import com.sunasterisk.fooddaily.ui.base.BaseFragment
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : BaseFragment(), CollectionContract.View {

    override val layoutRes: Int = R.layout.fragment_collection

    private var collectionPresenter: CollectionPresenter? = null
    private val collections = mutableListOf<Collection>()
    private val favoriteFoods = mutableListOf<FoodDetail>()
    private val familyFoods = mutableListOf<FoodDetail>()
    private val partyFoods = mutableListOf<FoodDetail>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFoodByCollections()
    }

    override fun initActionBar() {
        textActionBarTitle.text = getString(R.string.title_collection)
        buttonBackActionBar.visibility = View.GONE
        buttonSearchActionBar.visibility = View.GONE
        buttonCollectionActionBar.visibility = View.GONE
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
            collectionPresenter = CollectionPresenter(this, recipeRepository)
        }
        collectionPresenter?.run {
            getAllFavoriteFoods()
            getAllFamilyFoods()
            getAllPartyFoods()
        }
    }

    override fun createFavoriteFoods(favoriteFoods: List<FoodDetail>) {
        this.favoriteFoods.addAll(favoriteFoods)
    }

    override fun createFamilyFoods(familyFoods: List<FoodDetail>) {
        this.familyFoods.addAll(familyFoods)
    }

    override fun createPartyFoods(partyFoods: List<FoodDetail>) {
        this.partyFoods.addAll(partyFoods)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(
            activity,
            getString(R.string.notification_update_data),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showFoodByCollections() {
        initCollection()
        recyclerViewCollection.adapter = CollectionAdapter(collections) { food ->
            onFoodItemClick(food)
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

    private fun initCollection() {
        collections.apply {
            add(
                Collection(
                    getString(R.string.title_favorite),
                    R.drawable.ic_favorite,
                    false,
                    favoriteFoods
                )
            )
            add(
                Collection(
                    getString(R.string.title_family),
                    R.drawable.ic_family,
                    false,
                    familyFoods
                )
            )
            add(Collection(getString(R.string.title_party), R.drawable.ic_party, false, partyFoods))
        }
    }

    companion object {
        fun newInstance(): CollectionFragment = CollectionFragment()
    }
}
