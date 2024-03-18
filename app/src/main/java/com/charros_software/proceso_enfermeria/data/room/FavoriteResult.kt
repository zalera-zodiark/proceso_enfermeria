package com.charros_software.proceso_enfermeria.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_result")
data class FavoriteResult(
    @PrimaryKey val resultId: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)