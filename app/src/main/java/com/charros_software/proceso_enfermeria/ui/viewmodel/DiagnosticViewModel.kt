package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiagnosticViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DiagnosticUiState())
    val uiState: StateFlow<DiagnosticUiState> = _uiState.asStateFlow()

    fun initDiagnosticsList(diagnosticsList: List<DiagnosticModel>) {
        val orderList = diagnosticsList.sortedBy { it.number }
        _uiState.update { currentState -> currentState.copy(currentDiagnosticsList = orderList) }
    }

}