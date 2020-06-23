package com.sunasterisk.fooddaily.utils

object ApiKeys {
    const val SCHEME_HTTPS = "https"

    const val AUTHORITY_SPOONACULAR = "api.spoonacular.com"
    const val AUTHORITY_SPOONACULAR_IMAGE = "spoonacular.com"

    const val PATH_RECIPES = "recipes"
    const val PATH_COMPLEX_SEARCH = "complexSearch"
    const val PATH_RANDOM = "random"
    const val PATH_INFORMATION = "information"
    const val PATH_CDN = "cdn"
    const val PATH_EQUIPMENT = "equipment_250x250"
    const val PATH_INGREDIENT = "ingredients_100x100"

    const val QUERY_API_KEY = "apiKey"
    const val QUERY_NUMBER = "number"
    const val QUERY_NUTRITION = "includeNutrition"
    const val QUERY_LIMIT_LICENSE = "limitLicense"

    const val QUERY_KEYWORD = "query"

    const val DEFAULT_SEARCH_RESULT_LIMIT = 10
    const val DEFAULT_RANDOM_RESULT_LIMIT = 25
    const val DEFAULT_NUTRITION = "false"
    const val DEFAULT_RESULT_LIMIT_LICENSE = "true"
    const val DEFAULT_IMAGE_URL = "https://png.pngtree.com/png-clipart/20190516/original/" +
            "pngtree-cartoon-chef-hold-dish-png-image_3707878.jpg"
}
