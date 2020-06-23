package com.sunasterisk.fooddaily.ui.fragment.instruction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.sunasterisk.fooddaily.R
import com.sunasterisk.fooddaily.data.model.DataRequest
import com.sunasterisk.fooddaily.data.model.Instruction
import com.sunasterisk.fooddaily.ui.base.BaseFragment
import com.sunasterisk.fooddaily.utils.ApiKeys
import com.sunasterisk.fooddaily.utils.applyGlide
import kotlinx.android.synthetic.main.fragment_item_instruction.*

class InstructionFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_item_instruction

    private var instruction: Instruction? = null

    override fun initActionBar() {}

    override fun getArgument() {
        instruction = arguments?.getParcelable(ARGUMENT_INSTRUCTION_ITEM)
    }

    override fun initPresenter() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayStep()
    }

    private fun displayStep() {
        instruction?.let {
            textInstructionNumber.text = it.getStepNumber()
            textInstructionDes.text = it.step
            val imageInstructionUrl =
                createImageUrl(it.imageInstruction.toString(), ApiKeys.PATH_EQUIPMENT)
            val imageIngredientUrl =
                createImageUrl(it.imageIngredient.toString(), ApiKeys.PATH_INGREDIENT)
            imageFoodInstruction.applyGlide(imageInstructionUrl)
            imageFoodIngredient.applyGlide(imageIngredientUrl)
        }
    }

    private fun createImageUrl(imageName: String, path: String): String =
        if (imageName.isEmpty()) {
            ApiKeys.DEFAULT_IMAGE_URL
        } else {
            DataRequest(
                authority = ApiKeys.AUTHORITY_SPOONACULAR_IMAGE,
                paths = listOf(
                    ApiKeys.PATH_CDN,
                    path,
                    imageName
                ),
                queryParams = mapOf()
            ).toUrl()
        }

    companion object {

        private const val ARGUMENT_INSTRUCTION_ITEM = "ARGUMENT_INSTRUCTION_ITEM"

        fun newInstance(instruction: Instruction): InstructionFragment =
            InstructionFragment().apply {
                arguments = bundleOf(ARGUMENT_INSTRUCTION_ITEM to instruction)
            }
    }
}
