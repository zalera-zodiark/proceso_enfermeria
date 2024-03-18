package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel
import com.charros_software.proceso_enfermeria.domain.model.InterventionModel
import com.charros_software.proceso_enfermeria.domain.model.ResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionVViewModel(
    private val roomRepository: RoomRepository,
    private val idCollection: Int,
    private val diagnosticsList: List<DiagnosticModel>,
    private val interventionsList: List<InterventionModel>,
    private val resultsList: List<ResultModel>
): ViewModel() {

    private val _uiState = MutableStateFlow(CollectionVUiState())
    val uiState: StateFlow<CollectionVUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val collection = roomRepository.getNursingProcessCollectionById(idCollection)
            val diagnosticsRef = roomRepository.getDiagnosticsByCollectionId(idCollection)
            val interventionsRef = roomRepository.getInterventionsByCollectionId(idCollection)
            val resultsRef = roomRepository.getResultsByCollectionId(idCollection)

            val diagnostics = diagnosticsRef.map { ref -> diagnosticsList.find { it.number == ref.idDiagnostic } }
            val interventions = interventionsRef.map { ref -> interventionsList.find { it.number == ref.idIntervention }}
            val results = resultsRef.map { ref -> resultsList.find { it.number == ref.idResult }}

            _uiState.update { currentState ->
                currentState.copy(
                    currentCollection = collection,
                    currentDiagnosticsList = diagnostics,
                    currentInterventionsList = interventions,
                    currentResultsList = results)
            }
        }
    }

    fun deleteDiagnosticCollection(diagnosticNumber: Int) {
        viewModelScope.launch {
            roomRepository.deleteDiagnosticById(diagnosticNumber)
            val listDiagnostic = uiState.value.currentDiagnosticsList
            val updatedDiagnosticList = listDiagnostic.filter { it!!.number != diagnosticNumber }
            _uiState.update {
                it.copy(currentDiagnosticsList = updatedDiagnosticList)
            }
        }
    }

    fun deleteInterventionCollection(interventionNumber: Int) {
        viewModelScope.launch {
            roomRepository.deleteInterventionById(interventionNumber)
            val listIntervention = uiState.value.currentInterventionsList
            val updatedInterventionList = listIntervention.filter { it!!.number != interventionNumber }
            _uiState.update {
                it.copy(currentInterventionsList = updatedInterventionList)
            }
        }
    }

    fun deleteResultCollection(resultNumber: Int) {
        viewModelScope.launch {
            roomRepository.deleteResultById(resultNumber)
            val listResult = uiState.value.currentResultsList
            val updatedResultList = listResult.filter { it!!.number != resultNumber }
            _uiState.update {
                it.copy(currentResultsList = updatedResultList)
            }
        }
    }
}