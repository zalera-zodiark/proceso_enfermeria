package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.FavoriteDiagnostic
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel

data class DiagnosticUiState(
    val currentDiagnosticsList: List<DiagnosticModel> = emptyList(),
    val favoriteDiagnosticsList: List<FavoriteDiagnostic> = emptyList(),
    val favoriteListLoaded: Boolean = false
)