package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.FavoriteIntervention
import com.charros_software.proceso_enfermeria.data.room.NursingProcessCollection
import com.charros_software.proceso_enfermeria.domain.model.InterventionModel

data class InterventionUiState(
    val currentInterventionsList: List<InterventionModel> = emptyList(),
    val favoriteInterventionsList: List<FavoriteIntervention> = emptyList(),
    val collectionsList: List<NursingProcessCollection> = emptyList(),
    val isCollectionDuplicateError: Boolean = false,
    val isInterventionDuplicateError: Boolean = false,
    val idCollectionInterventionDuplicateError: Int = -1,
    val showInterventionAddedToCollectionMessage: Boolean = false
)