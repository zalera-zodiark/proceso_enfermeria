package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoriteResultDao {

    @Query(
        "INSERT INTO favorite_result(resultId, is_favorite) " +
                "VALUES(:resultId, :isFavorite)"
    ) suspend fun addFavoriteResult(resultId: Int, isFavorite: Boolean)

     @Query(
         "SELECT resultId FROM favorite_result WHERE resultId = :resultId"
     ) suspend fun checkFavoriteResultExist(resultId: Int): Int?

     @Query(
         "UPDATE favorite_result SET is_favorite = :isFavorite WHERE resultId = :resultId"
     ) suspend fun updateFavoriteResult(resultId: Int, isFavorite: Boolean)

     @Query(
         "SELECT * FROM favorite_result"
     ) suspend fun getAllFavoriteResultsList(): List<FavoriteResult>
}