package com.sunasterisk.fooddaily.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import com.sunasterisk.fooddaily.utils.DatabaseKeys
import com.sunasterisk.fooddaily.utils.getData
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

private const val JSON_KEY_ID = "id"
private const val JSON_KEY_TITLE = "title"
private const val JSON_KEY_PRICE = "pricePerServing"
private const val JSON_KEY_READY_MINUTES = "readyInMinutes"
private const val JSON_KEY_SUMMARY = "summary"
private const val JSON_KEY_IMAGE = "image"
private const val JSON_KEY_INGREDIENTS = "extendedIngredients"
private const val JSON_KEY_INSTRUCTIONS = "analyzedInstructions"
private const val JSON_KEY_STEPS = "steps"

const val REQUIRED_TIME_UNIT = " minutes"
const val PRICE_ESTIMATE_UNIT = " $"
const val CHARACTER_MINUS = "- "
const val CHARACTER_ENTER = "\n"

@Parcelize
data class FoodDetail(
    var id: String? = "",
    var title: String? = "",
    var price: String? = "",
    var readyMinutes: String? = "",
    var summary: String? = "",
    var imageUrl: String? = "",
    var ingredients: List<Ingredient>? = null,
    var instructions: List<Instruction>? = null
) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        id = jsonObject.optString(JSON_KEY_ID),
        title = jsonObject.optString(JSON_KEY_TITLE),
        price = jsonObject.optString(JSON_KEY_PRICE),
        readyMinutes = jsonObject.optString(JSON_KEY_READY_MINUTES),
        summary = jsonObject.optString(JSON_KEY_SUMMARY),
        imageUrl = jsonObject.optString(JSON_KEY_IMAGE),
        ingredients = mutableListOf<Ingredient>().apply {
            val ingredientList = jsonObject.optJSONArray(JSON_KEY_INGREDIENTS)
            if (ingredientList != null) {
                for (i in 0 until ingredientList.length()) {
                    add(Ingredient(ingredientList.optJSONObject(i)))
                }
            }
        },
        instructions = mutableListOf<Instruction>().apply {
            val instructionList = jsonObject.optJSONArray(JSON_KEY_INSTRUCTIONS)
            if (instructionList != null) {
                val instruction = instructionList.optJSONObject(0)
                if (instruction != null) {
                    val stepList = instruction.optJSONArray(JSON_KEY_STEPS)
                    if (stepList != null) {
                        for (i in 0 until stepList.length()) {
                            add(Instruction(stepList.optJSONObject(i)))
                        }
                    }
                }
            }
        }
    )

    constructor(cursor: Cursor) : this(
        id = cursor.getData(ID),
        title = cursor.getData(TITLE),
        price = cursor.getData(PRICE),
        readyMinutes = cursor.getData(READY_MINUTES),
        summary = cursor.getData(SUMMARY),
        imageUrl = cursor.getData(IMAGE_URL)
    )

    fun getRequiredTime(): String = readyMinutes + REQUIRED_TIME_UNIT

    fun getPriceEstimate(): String = price + PRICE_ESTIMATE_UNIT

    fun getIngredient(): String? = ingredients?.joinToString(CHARACTER_ENTER) {
        CHARACTER_MINUS + it.original
    }

    fun getContentValues(collectionName: String): ContentValues =
        ContentValues().apply {
            put(ID, id)
            put(TITLE, title)
            put(PRICE, price)
            put(READY_MINUTES, readyMinutes)
            put(SUMMARY, summary)
            put(IMAGE_URL, imageUrl)
            put(collectionName, DatabaseKeys.KEY_IN_COLLECTION)
        }

    companion object {
        const val TABLE_NAME = "tbl_food"
        const val ID = "food_id"
        const val TITLE = "food_title"
        const val PRICE = "food_price"
        const val READY_MINUTES = "food_ready_minutes"
        const val SUMMARY = "food_summary"
        const val IMAGE_URL = "food_image_url"
        const val TYPE_FAVORITE = "is_favorite"
        const val TYPE_FAMILY = "is_family"
        const val TYPE_PARTY = "is_party"
        const val STATE_IS_COOKING = "is_cooking"
    }
}
