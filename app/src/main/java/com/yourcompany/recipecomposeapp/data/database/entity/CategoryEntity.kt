package com.yourcompany.recipecomposeapp.data.database.entity

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)