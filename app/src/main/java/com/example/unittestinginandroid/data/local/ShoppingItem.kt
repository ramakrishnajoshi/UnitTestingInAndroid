package com.example.unittestinginandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
        var name: String,
        var quantity: Int,
        var price: Float,
        var imageUrl: String,
        @PrimaryKey(autoGenerate = true)
        var itemId: Int? = null
)
