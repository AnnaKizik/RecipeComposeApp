package com.yourcompany.recipecomposeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourcompany.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipeList: List<RecipeEntity>)

    @Query("SELECT * FROM recipes WHERE categoryId=:categoryId")
    fun getRecipesByCategoryId(categoryId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id=:recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity>?

    @Query("SELECT * FROM recipes WHERE id IN (:ids)")
    fun getRecipeListByIds(ids: List<Int>): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}