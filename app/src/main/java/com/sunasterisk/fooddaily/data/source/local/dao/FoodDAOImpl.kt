package com.sunasterisk.fooddaily.data.source.local.dao

import android.content.ContentValues
import com.sunasterisk.fooddaily.data.model.FoodDetail
import com.sunasterisk.fooddaily.data.model.Ingredient
import com.sunasterisk.fooddaily.data.model.Instruction
import com.sunasterisk.fooddaily.data.source.local.database.FoodDailyDatabase
import com.sunasterisk.fooddaily.utils.DatabaseKeys
import com.sunasterisk.fooddaily.utils.getData

private const val CHARACTER_EQUAL = "="

class FoodDAOImpl private constructor(
    foodDailyDatabase: FoodDailyDatabase
) : FoodDAO {

    private val database = foodDailyDatabase.writableDatabase

    override fun getAllFavoriteFoods(): List<FoodDetail> =
        getAllFoodsByCollection(FoodDetail.TYPE_FAVORITE)

    override fun getAllFamilyFoods(): List<FoodDetail> =
        getAllFoodsByCollection(FoodDetail.TYPE_FAMILY)

    override fun getAllPartyFoods(): List<FoodDetail> =
        getAllFoodsByCollection(FoodDetail.TYPE_PARTY)

    override fun getAllCookingFoods(): List<FoodDetail> =
        getAllFoodsByCollection(FoodDetail.STATE_IS_COOKING)

    override fun insertFoodFavorite(foodDetail: FoodDetail) =
        resolveInsert(foodDetail, FoodDetail.TYPE_FAVORITE)

    override fun insertFoodFamily(foodDetail: FoodDetail) =
        resolveInsert(foodDetail, FoodDetail.TYPE_FAMILY)

    override fun insertFoodParty(foodDetail: FoodDetail) =
        resolveInsert(foodDetail, FoodDetail.TYPE_PARTY)

    override fun insertFoodCooking(foodDetail: FoodDetail) {
        resolveInsert(foodDetail, FoodDetail.STATE_IS_COOKING)
    }

    override fun deleteFoodFavorite(foodDetail: FoodDetail) =
        deleteFood(foodDetail, FoodDetail.TYPE_FAVORITE)

    override fun deleteFoodFamily(foodDetail: FoodDetail) =
        deleteFood(foodDetail, FoodDetail.TYPE_FAMILY)

    override fun deleteFoodParty(foodDetail: FoodDetail) =
        deleteFood(foodDetail, FoodDetail.TYPE_PARTY)

    override fun deleteFoodCooking(foodDetail: FoodDetail) {
        deleteFood(foodDetail, FoodDetail.STATE_IS_COOKING)
    }

    private fun getAllFoodsByCollection(collectionName: String): List<FoodDetail> {
        val foods = mutableListOf<FoodDetail>()
        val selection = collectionName + CHARACTER_EQUAL + DatabaseKeys.KEY_IN_COLLECTION
        val cursor = database.query(
            FoodDetail.TABLE_NAME,
            null,
            selection,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                foods.add(FoodDetail(cursor))
                it.moveToNext()
            }
        }
        cursor.close()
        for (food in foods) {
            food.ingredients = getFoodIngredient(food.id.toString())
            food.instructions = getFoodInstruction(food.id.toString())
        }
        return foods
    }

    private fun resolveInsert(foodDetail: FoodDetail,collectionName: String) {
        if (checkFoodInDatabase(foodDetail)) {
            updateFood(foodDetail, collectionName)
        } else {
            insertFood(foodDetail, collectionName)
        }
    }

    private fun updateFood(foodDetail: FoodDetail, collectionName: String) {
        val whereClause = FoodDetail.ID + CHARACTER_EQUAL + foodDetail.id
        val contentValues = ContentValues().apply {
            put(collectionName, DatabaseKeys.KEY_IN_COLLECTION)
        }
        database.update(FoodDetail.TABLE_NAME, contentValues, whereClause, null)
    }

    private fun deleteFood(foodDetail: FoodDetail, collectionName: String) {
        val whereClause = FoodDetail.ID + CHARACTER_EQUAL + foodDetail.id
        val contentValues = ContentValues().apply {
            put(collectionName, DatabaseKeys.KEY_NOT_IN_COLLECTION)
        }
        database.update(FoodDetail.TABLE_NAME, contentValues, whereClause, null)
    }

    private fun getAllFoodIds(): List<String> {
        val foodIds = mutableListOf<String>()
        val cursor = database.query(
            FoodDetail.TABLE_NAME,
            null, null, null, null, null, null
        )
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                foodIds.add(it.getData(FoodDetail.ID))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return foodIds
    }

    private fun getFoodIngredient(foodId: String): List<Ingredient>? {
        val ingredients = mutableListOf<Ingredient>()
        val selection = FoodDetail.ID + CHARACTER_EQUAL + foodId
        val cursor = database.query(
            Ingredient.TABLE_NAME,
            null,
            selection,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val original = it.getString(it.getColumnIndex(Ingredient.ORIGINAL))
                ingredients.add(Ingredient(null, null, original))
                it.moveToNext()
            }
        }
        cursor.close()
        return ingredients
    }

    private fun getFoodInstruction(foodId: String): List<Instruction>? {
        val instructions = mutableListOf<Instruction>()
        val selection = FoodDetail.ID + CHARACTER_EQUAL + foodId
        val cursor = database.query(
            Instruction.TABLE_NAME,
            null,
            selection,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                instructions.add(Instruction(it))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return instructions
    }

    private fun checkFoodInDatabase(foodDetail: FoodDetail): Boolean {
        var isFoodExist = false
        val foodIds = getAllFoodIds()
        for (foodId in foodIds) {
            if (foodId.compareTo(foodDetail.id.toString()) == 0) {
                isFoodExist = true
                break
            }
        }
        return isFoodExist
    }

    private fun insertFood(foodDetail: FoodDetail, collectionName: String) {
        database.insert(
            FoodDetail.TABLE_NAME,
            null,
            foodDetail.getContentValues(collectionName)
        )
        insertToTableIngredient(foodDetail)
        insertToTableInstruction(foodDetail)
    }

    private fun insertToTableInstruction(foodDetail: FoodDetail) {
        foodDetail.instructions?.forEach { instruction ->
            database.insert(
                Instruction.TABLE_NAME,
                null,
                instruction.getContentValues(foodDetail)
            )
        }
    }

    private fun insertToTableIngredient(foodDetail: FoodDetail) {
        foodDetail.ingredients?.forEach { ingredient ->
            database.insert(
                Ingredient.TABLE_NAME,
                null,
                ingredient.getContentValues(foodDetail)
            )
        }
    }

    companion object {
        private var instance: FoodDAO? = null
        fun getInstance(foodDailyDatabase: FoodDailyDatabase): FoodDAO =
            instance ?: FoodDAOImpl(foodDailyDatabase).also { instance = it }
    }
}
