package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.NursingProcessDiagnostics
import com.charros_software.proceso_enfermeria.data.room.NursingProcessInterventions
import com.charros_software.proceso_enfermeria.data.room.NursingProcessResults
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val roomRepository: RoomRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    init {
        updateCollectionList()
    }

    private fun updateCollectionList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(currentCollectionsList = roomRepository.getNursingProcessCollectionList())
            }
        }
    }

    fun deleteCollection(idCollection: Int) {
        viewModelScope.launch {
            val diagnosticsList = roomRepository.getDiagnosticsByCollectionId(idCollection)
            val interventionsList = roomRepository.getInterventionsByCollectionId(idCollection)
            val resultsList = roomRepository.getResultsByCollectionId(idCollection)

            if (diagnosticsList.isNotEmpty()) {
                deleteDiagnostics(diagnosticsList)
            }

            if (interventionsList.isNotEmpty()) {
                deleteInterventions(interventionsList)
            }

            if (resultsList.isNotEmpty()) {
                deleteResults(resultsList)
            }

            roomRepository.deleteCollection(idCollection)
            updateCollectionList()
            _uiState.update { it.copy(showDeleteCollectionMessage = true) }
            delay(10)
            _uiState.update { it.copy(showDeleteCollectionMessage = false) }
        }
    }

    private fun deleteDiagnostics(diagnostics: List<NursingProcessDiagnostics>) {
        viewModelScope.launch {
            diagnostics.forEach {
                roomRepository.deleteDiagnosticById(it.idDiagnostic)
            }
        }
    }

    private fun deleteInterventions(interventions: List<NursingProcessInterventions>) {
        viewModelScope.launch {
            interventions.forEach {
                roomRepository.deleteInterventionById(it.idIntervention)
            }
        }
    }

    private fun deleteResults(results: List<NursingProcessResults>) {
        viewModelScope.launch {
            results.forEach {
                roomRepository.deleteResultById(it.idResult)
            }
        }
    }
}