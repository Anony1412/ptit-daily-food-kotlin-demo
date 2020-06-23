package com.sunasterisk.fooddaily.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import com.sunasterisk.fooddaily.utils.getData
import com.sunasterisk.fooddaily.utils.getImageIngredientName
import com.sunasterisk.fooddaily.utils.getImageInstructionName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

private const val JSON_KEY_NUMBER = "number"
private const val JSON_KEY_STEP = "step"
private const val TITLE_STEP = "Step "

@Parcelize
data class Instruction(
    var number: String? = "",
    var step: String? = "",
    var imageInstruction: String? = "",
    var imageIngredient: String? = ""
) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        number = jsonObject.optString(JSON_KEY_NUMBER),
        step = jsonObject.optString(JSON_KEY_STEP),
        imageInstruction = jsonObject.getImageInstructionName(),
        imageIngredient = jsonObject.getImageIngredientName()
    )

    constructor(cursor: Cursor) : this(
        number = cursor.getData(NUMBER),
        step = cursor.getData(STEP),
        imageInstruction = cursor.getData(IMAGE_INSTRUCTION),
        imageIngredient = cursor.getData(IMAGE_INGREDIENT)
    )

    fun getStepNumber(): String = TITLE_STEP + number

    fun getContentValues(foodDetail: FoodDetail): ContentValues =
        ContentValues().apply {
            put(NUMBER, number)
            put(STEP, step)
            put(IMAGE_INSTRUCTION, imageInstruction)
            put(IMAGE_INGREDIENT, imageIngredient)
            put(FoodDetail.ID, foodDetail.id)
        }

    companion object {
        const val TABLE_NAME = "tbl_instruction"
        const val ID = "instruction_id"
        const val NUMBER = "instruction_number"
        const val STEP = "instruction_step"
        const val IMAGE_INSTRUCTION = "image_instruction"
        const val IMAGE_INGREDIENT = "image_ingredient"
    }
}
