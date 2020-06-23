package com.sunasterisk.fooddaily.utils

import org.json.JSONObject

private const val JSON_KEY_EQUIPMENT = "equipment"
private const val JSON_KEY_INGREDIENT = "ingredients"
private const val JSON_KEY_IMAGE = "image"

fun JSONObject.getImageInstructionName(): String =
    optJSONArray(JSON_KEY_EQUIPMENT)
        ?.optJSONObject(0)
        ?.optString(JSON_KEY_IMAGE)
        ?: ""

fun JSONObject.getImageIngredientName(): String =
    optJSONArray(JSON_KEY_INGREDIENT)
        ?.optJSONObject(0)
        ?.optString(JSON_KEY_IMAGE)
        ?: ""
