package com.charros_software.proceso_enfermeria.ui.viewmodel

import com.charros_software.proceso_enfermeria.data.room.FavoriteResult
import com.charros_software.proceso_enfermeria.data.room.NursingProcessCollection
import com.charros_software.proceso_enfermeria.domain.model.ResultModel

data class ResultUiState(
    val currentResultsList: List<ResultModel> = emptyList(),
    val favoriteResultsList: List<FavoriteResult> = emptyList(),
    val collectionsList: List<NursingProcessCollection> = emptyList(),
    val isCollectionDuplicateError: Boolean = false,
    val isResultDuplicateError: Boolean = false,
    val idCollectionResultDuplicateError: Int = -1,
    val showResultAddedToCollectionMessage: Boolean = false
)