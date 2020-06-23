package com.sunasterisk.fooddaily.ui.activity.splash

import android.view.animation.AnimationUtils
import android.widget.Toast
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.local.RecipeLocalDataSource
import com.sunasterisk.fooddaily.data.source.local.dao.FoodDAOImpl
import com.sunasterisk.fooddaily.data.source.local.database.FoodDailyDatabase
import com.sunasterisk.fooddaily.data.source.remote.RecipeRemoteDataSource
import com.sunasterisk.fooddaily.ui.activity.main.MainActivity
import com.sunasterisk.fooddaily.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : BaseActivity(), SplashScreenContract.View {

    override val layoutRes: Int = R.layout.activity_splash_screen
    private var splashPresenter: SplashScreenPresenter? = null

    override fun initPresenter() {
        val foodDailyDatabase = FoodDailyDatabase.getInstance(applicationContext)
        val foodDAO = FoodDAOImpl.getInstance(foodDailyDatabase)
        val recipeRepository = RecipeRepository.getInstance(
            RecipeRemoteDataSource.getInstance(),
            RecipeLocalDataSource.getInstance(foodDAO)
        )
        splashPresenter = SplashScreenPresenter(this, recipeRepository)
        splashPresenter?.getRandomFoods()
    }

    override fun initView() {
        val animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate)
        imageLoadData.startAnimation(animRotate)
    }

    override fun onTransportDataToHome(data: List<FoodDetail>) {
        val intentMain = MainActivity.getIntent(applicationContext, data)
        startActivity(intentMain)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(
            this,
            getString(R.string.title_error) + exception.message,
            Toast.LENGTH_SHORT
        ).show()
        splashPresenter?.getRandomFoods()
    }
}
