package com.sunasterisk.fooddaily.data.model

import android.net.Uri
import com.sunasterisk.fooddaily.utils.ApiKeys

data class DataRequest(
    val scheme: String = ApiKeys.SCHEME_HTTPS,
    val authority: String = ApiKeys.AUTHORITY_SPOONACULAR,
    val paths: List<String>,
    val queryParams: Map<String, Any>
) {
    fun toUrl(): String = Uri.Builder().apply {
        scheme(scheme)
        authority(authority)
        paths.forEach {
            appendPath(it)
        }
        queryParams.forEach {
            appendQueryParameter(it.key, it.value.toString())
        }
    }.toString()
}
