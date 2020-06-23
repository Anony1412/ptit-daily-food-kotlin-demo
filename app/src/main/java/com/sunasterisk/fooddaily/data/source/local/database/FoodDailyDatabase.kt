package com.sunasterisk.fooddaily.data.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.model.Ingredient
import com.sunasterisk.fooddaily.data.model.Instruction
import com.sunasterisk.fooddaily.utils.DatabaseKeys

open class FoodDailyDatabase private constructor(
    context: Context
) : SQLiteOpenHelper(
    context,
    DatabaseKeys.DATABASE_NAME,
    null,
    DatabaseKeys.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            execSQL(SQL_CREATE_TABLE_FOOD)
            execSQL(SQL_CREATE_TABLE_INGREDIENT)
            execSQL(SQL_CREATE_TABLE_INSTRUCTION)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.run {
            execSQL(SQL_DROP_TABLE_FOOD)
            execSQL(SQL_DROP_TABLE_INGREDIENT)
            execSQL(SQL_DROP_TABLE_INSTRUCTION)
        }
        onCreate(db)
    }

    companion object {
        private const val SQL_CREATE_TABLE_FOOD =
            "CREATE TABLE " + FoodDetail.TABLE_NAME + " (" +
                    FoodDetail.ID + " INTEGER PRIMARY KEY," +
                    FoodDetail.TITLE + " TEXT," +
                    FoodDetail.PRICE + " TEXT," +
                    FoodDetail.READY_MINUTES + " TEXT," +
                    FoodDetail.SUMMARY + " TEXT," +
                    FoodDetail.IMAGE_URL + " TEXT," +
                    FoodDetail.TYPE_FAVORITE + " TEXT," +
                    FoodDetail.TYPE_FAMILY + " TEXT," +
                    FoodDetail.TYPE_PARTY + " TEXT," +
                    FoodDetail.STATE_IS_COOKING + " TEXT);"

        private const val SQL_CREATE_TABLE_INGREDIENT =
            "CREATE TABLE " + Ingredient.TABLE_NAME + " (" +
                    Ingredient.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Ingredient.ORIGINAL + " TEXT," +
                    FoodDetail.ID + " INTEGER," +
                    "FOREIGN KEY (" + FoodDetail.ID + ") " +
                    "REFERENCES " + FoodDetail.TABLE_NAME + "(" + FoodDetail.ID + ")" +
                    ");"

        private const val SQL_CREATE_TABLE_INSTRUCTION =
            "CREATE TABLE " + Instruction.TABLE_NAME + " (" +
                    Instruction.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Instruction.NUMBER + " TEXT," +
                    Instruction.STEP + " TEXT," +
                    Instruction.IMAGE_INSTRUCTION + " TEXT," +
                    Instruction.IMAGE_INGREDIENT + " TEXT," +
                    FoodDetail.ID + " INTEGER," +
                    "FOREIGN KEY (" + FoodDetail.ID + ") " +
                    "REFERENCES " + FoodDetail.TABLE_NAME + "(" + FoodDetail.ID + ")" +
                    ");"

        private const val SQL_DROP_TABLE_FOOD =
            "DROP TABLE IF EXISTS " + FoodDetail.TABLE_NAME

        private const val SQL_DROP_TABLE_INGREDIENT =
            "DROP TABLE IF EXISTS " + Ingredient.TABLE_NAME

        private const val SQL_DROP_TABLE_INSTRUCTION =
            "DROP TABLE IF EXISTS " + Instruction.TABLE_NAME

        private var instance: FoodDailyDatabase? = null
        fun getInstance(context: Context): FoodDailyDatabase =
            instance ?: FoodDailyDatabase(context).also { instance = it }
    }
}
