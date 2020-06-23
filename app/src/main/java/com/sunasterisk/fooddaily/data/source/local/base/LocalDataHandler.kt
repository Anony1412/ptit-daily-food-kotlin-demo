package com.sunasterisk.fooddaily.data.source.local.base

import kotlin.Exception

interface LocalDataHandler<P, T> {
    @Throws(Exception::class)
    fun execute(params: P): T
}
