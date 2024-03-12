package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoriteDiagnosticDao {
    @Query(
        "INSERT INTO favorite_diagnostic(diagnosticId, is_favorite) " +
                "VALUES(:diagnosticId, :isFavorite)"
    ) suspend fun addFavoriteDiagnostic(diagnosticId: Int, isFavorite: Boolean)

    @Query(
        "SELECT diagnosticId FROM favorite_diagnostic WHERE diagnosticId = :diagnosticId"
    ) suspend fun checkFavoriteDiagnosticExist(diagnosticId: Int): Int?

    @Query(
        "UPDATE favorite_diagnostic SET is_favorite = :isFavorite WHERE diagnosticId = :diagnosticId"
    ) suspend fun updateFavoriteDiagnostic(diagnosticId: Int, isFavorite: Boolean)

    @Query(
        "SELECT * FROM favorite_diagnostic"
    ) suspend fun getAllFavoriteDiagnosticsList(): List<FavoriteDiagnostic>
}