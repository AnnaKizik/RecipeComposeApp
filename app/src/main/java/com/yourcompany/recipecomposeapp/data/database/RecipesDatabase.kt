package com.yourcompany.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourcompany.recipecomposeapp.data.database.converter.Converters
import com.yourcompany.recipecomposeapp.data.database.dao.CategoryDao
import com.yourcompany.recipecomposeapp.data.database.dao.RecipeDao
import com.yourcompany.recipecomposeapp.data.database.entity.CategoryEntity
import com.yourcompany.recipecomposeapp.data.database.entity.RecipeEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        CategoryEntity::class,
        RecipeEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun buildDatabase(context: Context): RecipesDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = RecipesDatabase::class.java,
                name = "recipes_database",
            )
                .fallbackToDestructiveMigration(true)
                .build()
        }
    }
}
