package com.sunasterisk.fooddaily.ui.activity.food

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager
import com.facebook.CallbackManager
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.repository.RecipeRepository
import com.sunasterisk.fooddaily.data.source.local.RecipeLocalDataSource
import com.sunasterisk.fooddaily.data.source.local.dao.FoodDAOImpl
import com.sunasterisk.fooddaily.data.source.local.database.FoodDailyDatabase
import com.sunasterisk.fooddaily.data.source.remote.RecipeRemoteDataSource
import com.sunasterisk.fooddaily.ui.adapter.InstructionAdapter
import com.sunasterisk.fooddaily.ui.base.BaseActivity
import com.sunasterisk.fooddaily.ui.fragment.instruction.InstructionFragment
import com.sunasterisk.fooddaily.utils.applyGlide
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.dialog_choose_collection.*
import kotlinx.android.synthetic.main.dialog_cogratulations.*
import kotlinx.android.synthetic.main.frame_food_detail_instruction.*
import kotlinx.android.synthetic.main.frame_food_detail_preview.*

class FoodDetailActivity :
    BaseActivity(),
    View.OnClickListener,
    ViewPager.OnPageChangeListener {

    override val layoutRes: Int = R.layout.activity_food_detail

    private var foodDetailPresenter: FoodDetailPresenter? = null

    private var food: FoodDetail? = null
    private var isCompletedCooking = false
    private var currentPositionPage = 0
    private var instructionAdapter: InstructionAdapter? = null
    private var callbackManager: CallbackManager? = null
    private var shareDialog: ShareDialog? = null

    override fun initPresenter() {
        val foodDailyDatabase = FoodDailyDatabase.getInstance(applicationContext)
        val foodDAO = FoodDAOImpl.getInstance(foodDailyDatabase)
        val recipeRepository = RecipeRepository.getInstance(
            RecipeRemoteDataSource.getInstance(),
            RecipeLocalDataSource.getInstance(foodDAO)
        )
        foodDetailPresenter = FoodDetailPresenter(recipeRepository)
    }

    override fun initView() {
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
        food = intent.getParcelableExtra(EXTRA_FOOD_ITEM)
        food?.let { showFoodDetail(it) }
        initActionBar()
        initListener()
    }

    private fun initListener() {
        buttonCollectionActionBar.setOnClickListener(this)
        buttonBackActionBar.setOnClickListener(this)
        buttonStart.setOnClickListener(this)
    }

    private fun showFoodDetail(food: FoodDetail) {
        textFoodNameDes.text = food.title
        textFoodNameDes.isSelected = true
        textSummaryDes.text = HtmlCompat.fromHtml(
            food.summary.toString(),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        textReadyMinutesDes.text = food.getRequiredTime()
        textPriceDes.text = food.getPriceEstimate()
        textIngredientDes.text = food.getIngredient() ?: getString(R.string.title_updating)
        imageFoodDetail.applyGlide(food.imageUrl)
    }

    private fun initActionBar() {
        buttonSearchActionBar.visibility = View.GONE
        textActionBarTitle.text = getString(R.string.title_food_detail)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonBackActionBar -> backToHomeScreen()
            R.id.buttonCollectionActionBar -> showDialogChooseCollection()
            R.id.buttonStart -> controlStartCooking()
            R.id.buttonCompleted -> controlCompletedCooking()
            R.id.buttonPreviousNavigation -> goToPreviousPage()
            R.id.buttonNextNavigation -> goToNextPage()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        currentPositionPage = position
        when (position) {
            0 -> {
                hideButtonPrevious()
                displayButtonNext()
            }
            pagerInstruction.adapter?.count?.minus(1) -> {
                displayButtonCompleted()
                hideButtonNext()
                displayButtonPrevious()
            }
            else -> displayButtonNav()
        }
    }

    private fun goToNextPage() {
        pagerInstruction.adapter?.let {
            if (currentPositionPage.plus(1) < it.count) {
                pagerInstruction.currentItem = currentPositionPage.plus(1)
            }
        }
    }

    private fun goToPreviousPage() {
        pagerInstruction.adapter?.let {
            if (currentPositionPage.minus(1) >= 0) {
                pagerInstruction.currentItem = currentPositionPage.minus(1)
            }
        }
    }

    private fun controlCompletedCooking() {
        food?.let { foodDetailPresenter?.deleteFoodFromCooking(it) }
        isCompletedCooking  = true
        showDialogCongratulations()
    }

    private fun showDialogCongratulations() {
        Dialog(this, R.style.DialogTheme).run {
            setCancelable(false)
            setContentView(R.layout.dialog_cogratulations)
            show()
            buttonNoThank.setOnClickListener { dismiss() }
            buttonShareOnFacebook.setOnClickListener {
                shareRecipeOnFacebook()
                dismiss()
            }
        }
    }

    private fun shareRecipeOnFacebook() {
        val drawable = imageFoodDetail.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val photo: SharePhoto = SharePhoto.Builder()
            .setBitmap(bitmap)
            .build()
        val sharePhotoContent: SharePhotoContent = SharePhotoContent.Builder()
            .addPhoto(photo)
            .setShareHashtag(
                ShareHashtag.Builder()
                    .setHashtag(TITLE_HASH_TAG)
                    .build()
            )
            .build()
        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            shareDialog?.show(sharePhotoContent)
        }
    }

    private fun showDialogChooseCollection() {
        Dialog(this, R.style.DialogTheme).run {
            setCancelable(false)
            setContentView(R.layout.dialog_choose_collection)
            show()
            buttonCloseChooseDialog.setOnClickListener { dismiss() }
            buttonFavorite.setOnClickListener {
                food?.let(::addFoodToFavorite)
                dismiss()
            }
            buttonFamily.setOnClickListener {
                food?.let(::addFoodToFamily)
                dismiss()
            }
            buttonParty.setOnClickListener {
                food?.let(::addFoodToParty)
                dismiss()
            }
        }
    }

    private fun backToHomeScreen() {
        if (isCompletedCooking) {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra(RESULT_FOOD_ID, food)
            )
        }
        finish()
    }

    private fun controlStartCooking() {
        showFrameInstruction()
        food?.let { foodDetailPresenter?.addFoodToCooking(it) }
    }

    private fun showFrameInstruction() {
        buttonStart.visibility = View.GONE
        frameFoodDetailPreview.visibility = View.GONE
        frameFoodDetailInstruction.visibility = View.VISIBLE
        initInstructionAdapter()
        buttonPreviousNavigation.setOnClickListener(this)
        buttonNextNavigation.setOnClickListener(this)
    }

    private fun initInstructionAdapter() {

        val fragmentSteps = food?.instructions?.map {
            InstructionFragment.newInstance(it)
        }
        instructionAdapter = InstructionAdapter(supportFragmentManager).apply {
            fragmentSteps?.let { addFragment(it) }
        }
        pagerInstruction.apply {
            adapter = instructionAdapter
            addOnPageChangeListener(this@FoodDetailActivity)
        }
    }

    private fun addFoodToParty(food: FoodDetail) {
        foodDetailPresenter?.addFoodToParty(food)
    }

    private fun addFoodToFamily(food: FoodDetail) {
        foodDetailPresenter?.addFoodToFamily(food)
    }

    private fun addFoodToFavorite(food: FoodDetail) {
        foodDetailPresenter?.addFoodToFavorite(food)
    }

    private fun displayButtonNav() {
        buttonCompleted.visibility = View.GONE
        buttonPreviousNavigation.visibility = View.VISIBLE
        buttonNextNavigation.visibility = View.VISIBLE
    }

    private fun hideButtonNext() {
        buttonNextNavigation.visibility = View.GONE
    }

    private fun hideButtonPrevious() {
        buttonPreviousNavigation.visibility = View.GONE
    }

    private fun displayButtonNext() {
        buttonNextNavigation.visibility = View.VISIBLE
    }

    private fun displayButtonPrevious() {
        buttonPreviousNavigation.visibility = View.VISIBLE
    }

    private fun displayButtonCompleted() {
        buttonCompleted.visibility = View.VISIBLE
        buttonCompleted.setOnClickListener(this)
    }

    companion object {
        const val REQUEST_FOOD_DETAIL_ACTIVITY = 0
        const val RESULT_FOOD_ID = "result_food_id"
        private const val TITLE_HASH_TAG = "#food_daily"
        private const val EXTRA_FOOD_ITEM =
            "com.sunasterisk.fooddaily.utils.Constants.EXTRA_FOOD_ITEM"

        fun getIntent(context: Context, food: FoodDetail): Intent =
            Intent(context, FoodDetailActivity::class.java)
                .putExtra(EXTRA_FOOD_ITEM, food)
    }
}
