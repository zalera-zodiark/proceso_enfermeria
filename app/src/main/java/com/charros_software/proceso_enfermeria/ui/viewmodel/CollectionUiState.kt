package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.NursingProcessCollection

data class CollectionUiState(
    val currentCollectionsList: List<NursingProcessCollection> = emptyList(),
    val showDeleteCollectionMessage: Boolean = false,
)