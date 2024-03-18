package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.FavoriteDiagnostic
import com.charros_software.proceso_enfermeria.data.room.NursingProcessCollection
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel

data class DiagnosticUiState(
    val currentDiagnosticsList: List<DiagnosticModel> = emptyList(),
    val favoriteDiagnosticsList: List<FavoriteDiagnostic> = emptyList(),
    val collectionsList: List<NursingProcessCollection> = emptyList(),
    val isCollectionDuplicateError: Boolean = false,
    val isDiagnosticDuplicateError: Boolean = false,
    val idCollectionDiagnosticDuplicateError: Int = -1,
    val showDiagnosticAddedToCollectionMessage: Boolean = false
)