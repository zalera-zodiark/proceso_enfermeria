package com.charros_software.proceso_enfermeria.data.room

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RoomRepository(
    val nursingProcessDiagnosticsDao: NursingProcessDiagnosticsDao,
    private val nursingProcessCollectionDao: NursingProcessCollectionDao,
    private val favoriteDiagnosticDao: FavoriteDiagnosticDao
) {
    suspend fun checkFavoriteDiagnosticExistOrNull(idDiagnostic: Int): Int? {
        return favoriteDiagnosticDao.checkFavoriteDiagnosticExist(idDiagnostic)
    }

    suspend fun addFavoriteDiagnostic(idDiagnostic: Int) {
        favoriteDiagnosticDao.addFavoriteDiagnostic(idDiagnostic, true)
    }

    suspend fun updateFavoriteDiagnostic(idDiagnostic: Int, isFavorite: Boolean) {
        favoriteDiagnosticDao.updateFavoriteDiagnostic(idDiagnostic, isFavorite)
    }

    suspend fun getFavoriteDiagnosticsList() = favoriteDiagnosticDao.getAllFavoriteDiagnosticsList()

    suspend fun getNursingProcessCollectionList() = nursingProcessCollectionDao.getCollectionsList()

    suspend fun checkCollectionExist(collection: String) =
        nursingProcessCollectionDao.checkCollectionExist(collection)

    suspend fun addNewCollection(collection: String) {
        nursingProcessCollectionDao.addProcessCollection(
            collection, LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MMM-yyyy")
            )
        )
    }

    suspend fun checkDiagnosticAlreadyInCollection(
        idCollection: Int,
        diagnosticNumber: Int
    ): Boolean {
        val collectionsWithDiagnostics = nursingProcessCollectionDao.getCollectionWithDiagnostics()

        val collectionWithDiagnostics =
            collectionsWithDiagnostics.find { it.collection.idNursingProcessCollection == idCollection }

        val diagnostic =
            collectionWithDiagnostics!!.diagnostics.find { it.idDiagnostic == diagnosticNumber }
        return diagnostic != null
    }

    suspend fun addDiagnosticToCollection(idCollection: Int, idDiagnostic: Int) {
        nursingProcessDiagnosticsDao.addDiagnosticToCollection(idDiagnostic, idCollection)
    }
}