package com.sunasterisk.fooddaily.utils

import android.database.Cursor

fun Cursor.getData(columnName: String): String = getString(getColumnIndex(columnName))
