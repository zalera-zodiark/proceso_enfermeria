package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoriteInterventionDao {

    @Query(
        "INSERT INTO favorite_intervention(interventionId, is_favorite) " +
                "VALUES(:interventionId, :isFavorite)"
    ) suspend fun addFavoriteIntervention(interventionId: Int, isFavorite: Boolean)

    @Query(
        "SELECT interventionId FROM favorite_intervention WHERE interventionId = :interventionId"
    ) suspend fun checkFavoriteInterventionExist(interventionId: Int): Int?

    @Query(
        "UPDATE favorite_intervention SET is_favorite = :isFavorite WHERE interventionId = :interventionId"
    ) suspend fun updateFavoriteIntervention(interventionId: Int, isFavorite: Boolean)

    @Query(
        "SELECT * FROM favorite_intervention"
    ) suspend fun getAllFavoriteInterventionsList(): List<FavoriteIntervention>
}