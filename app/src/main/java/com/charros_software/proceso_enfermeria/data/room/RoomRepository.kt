package com.charros_software.proceso_enfermeria.data.room

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RoomRepository(
    private val nursingProcessDiagnosticsDao: NursingProcessDiagnosticsDao,
    private val nursingProcessInterventionsDao: NursingProcessInterventionsDao,
    private val nursingProcessResultsDao: NursingProcessResultsDao,
    private val nursingProcessCollectionDao: NursingProcessCollectionDao,
    private val favoriteDiagnosticDao: FavoriteDiagnosticDao,
    private val favoriteInterventionDao: FavoriteInterventionDao,
    private val favoriteResultDao: FavoriteResultDao
) {
    suspend fun checkFavoriteDiagnosticExistOrNull(idDiagnostic: Int): Int? {
        return favoriteDiagnosticDao.checkFavoriteDiagnosticExist(idDiagnostic)
    }

    suspend fun checkFavoriteInterventionExistOrNull(idIntervention: Int): Int? {
        return favoriteInterventionDao.checkFavoriteInterventionExist(idIntervention)
    }

    suspend fun checkFavoriteResultExistOrNull(idResult: Int): Int? {
        return favoriteResultDao.checkFavoriteResultExist(idResult)
    }

    suspend fun addFavoriteDiagnostic(idDiagnostic: Int) {
        favoriteDiagnosticDao.addFavoriteDiagnostic(idDiagnostic, true)
    }

    suspend fun addFavoriteIntervention(idIntervention: Int) {
        favoriteInterventionDao.addFavoriteIntervention(idIntervention, true)
    }

    suspend fun addFavoriteResult(idResult: Int) {
        favoriteResultDao.addFavoriteResult(idResult, true)
    }

    suspend fun updateFavoriteDiagnostic(idDiagnostic: Int, isFavorite: Boolean) {
        favoriteDiagnosticDao.updateFavoriteDiagnostic(idDiagnostic, isFavorite)
    }

    suspend fun updateFavoriteIntervention(idIntervention: Int, isFavorite: Boolean) {
        favoriteInterventionDao.updateFavoriteIntervention(idIntervention, isFavorite)
    }

    suspend fun updateFavoriteResult(idResult: Int, isFavorite: Boolean) {
        favoriteResultDao.updateFavoriteResult(idResult, isFavorite)
    }

    suspend fun getFavoriteDiagnosticsList() = favoriteDiagnosticDao.getAllFavoriteDiagnosticsList()

    suspend fun getFavoriteInterventionsList() = favoriteInterventionDao.getAllFavoriteInterventionsList()

    suspend fun getFavoriteResultsList() = favoriteResultDao.getAllFavoriteResultsList()

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

    suspend fun getNursingProcessCollectionById(idCollection: Int) = nursingProcessCollectionDao.getCollectionById(idCollection)


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

    suspend fun checkInterventionAlreadyInCollection(
        idCollection: Int,
        interventionNumber: Int
    ): Boolean {
        val collectionsWithInterventions = nursingProcessCollectionDao.getCollectionWithInterventions()

        val collectionWithInterventions =
            collectionsWithInterventions.find { it.collection.idNursingProcessCollection == idCollection }

        val intervention =
            collectionWithInterventions!!.interventions.find { it.idIntervention == interventionNumber }
        return intervention != null
    }

    suspend fun checkResultAlreadyInCollection(
        idCollection: Int,
        resultNumber: Int
    ): Boolean {
        val collectionsWithResults = nursingProcessCollectionDao.getCollectionWithResults()

        val collectionWithResults =
            collectionsWithResults.find { it.collection.idNursingProcessCollection == idCollection }

        val result =
            collectionWithResults!!.results.find { it.idResult == resultNumber }
        return result != null
    }

    suspend fun addDiagnosticToCollection(idCollection: Int, idDiagnostic: Int) {
        nursingProcessDiagnosticsDao.addDiagnosticToCollection(idDiagnostic, idCollection)
    }

    suspend fun addInterventionToCollection(idCollection: Int, idIntervention: Int) {
        nursingProcessInterventionsDao.addInterventionToCollection(idIntervention, idCollection)
    }

    suspend fun addResultToCollection(idCollection: Int, idResult: Int) {
        nursingProcessResultsDao.addResultToCollection(idResult, idCollection)
    }

    suspend fun getDiagnosticsByCollectionId(idCollection: Int) =
        nursingProcessDiagnosticsDao.getDiagnosticsByCollectionId(idCollection)

    suspend fun getInterventionsByCollectionId(idCollection: Int) =
        nursingProcessInterventionsDao.getInterventionsByCollectionId(idCollection)

    suspend fun getResultsByCollectionId(idCollection: Int) =
        nursingProcessResultsDao.getResultsByCollectionId(idCollection)

    suspend fun deleteDiagnosticById(idDiagnostic: Int) = nursingProcessDiagnosticsDao.deleteDiagnosticById(idDiagnostic)

    suspend fun deleteInterventionById(idIntervention: Int) = nursingProcessInterventionsDao.deleteInterventionById(idIntervention)

    suspend fun deleteResultById(idResult: Int) = nursingProcessResultsDao.deleteResultById(idResult)

    suspend fun deleteCollection(idCollection: Int) = nursingProcessCollectionDao.deleteCollectionById(idCollection)
}