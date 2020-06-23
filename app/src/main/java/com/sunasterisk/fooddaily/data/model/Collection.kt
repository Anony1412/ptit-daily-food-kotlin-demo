package com.sunasterisk.fooddaily.data.model

data class Collection(
    val name: String,
    val imgRes: Int,
    var isExpanded: Boolean,
    val content: List<FoodDetail>
)
