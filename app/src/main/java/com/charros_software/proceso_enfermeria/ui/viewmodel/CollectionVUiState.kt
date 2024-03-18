package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.NursingProcessCollection
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel
import com.charros_software.proceso_enfermeria.domain.model.InterventionModel
import com.charros_software.proceso_enfermeria.domain.model.ResultModel

data class CollectionVUiState(
    val currentCollection: NursingProcessCollection? = null,
    val currentDiagnosticsList: List<DiagnosticModel?> = emptyList(),
    val currentInterventionsList: List<InterventionModel?> = emptyList(),
    val currentResultsList: List<ResultModel?> = emptyList()
)