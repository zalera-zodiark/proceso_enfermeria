package com.charros_software.proceso_enfermeria.data.room

class RoomRepository(
    val nursingProcessDiagnosticsDao: NursingProcessDiagnosticsDao,
    val nursingProcessCollection: NursingProcessCollectionDao,
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
}