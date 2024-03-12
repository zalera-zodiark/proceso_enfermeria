package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiagnosticViewModel(
    private val roomRepository: RoomRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(DiagnosticUiState())
    val uiState: StateFlow<DiagnosticUiState> = _uiState.asStateFlow()
    private lateinit var diagnosticList: List<DiagnosticModel>
    private var favoriteFilterIsSelected = false

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(favoriteDiagnosticsList = roomRepository.getFavoriteDiagnosticsList())
            }
            _uiState.update { currentState ->
                currentState.copy(favoriteListLoaded = true)
            }
        }
    }

    fun initDiagnosticsList(diagnosticsList: List<DiagnosticModel>) {

        val orderList = diagnosticsList.sortedBy { it.number }
        this.diagnosticList = orderList
        _uiState.update { currentState -> currentState.copy(currentDiagnosticsList = orderList) }
    }

    fun setFavoriteDiagnostic(diagnosticNumber: Int, isFavorite: Boolean) {
        _uiState.update { currentState -> currentState.copy(favoriteListLoaded = false) }
        viewModelScope.launch {

            if (roomRepository.checkFavoriteDiagnosticExistOrNull(diagnosticNumber) != null) {
                roomRepository.updateFavoriteDiagnostic(diagnosticNumber, isFavorite)
            } else {
                roomRepository.addFavoriteDiagnostic(diagnosticNumber)
            }
            _uiState.update { it.copy(favoriteDiagnosticsList = roomRepository.getFavoriteDiagnosticsList()) }
        }
        if (favoriteFilterIsSelected) {
            filterFavoriteDiagnosticList(true, diagnosticNumber)
        } else {
            _uiState.update { it.copy(favoriteListLoaded = true) }
        }
    }

    fun filterFavoriteDiagnosticList(isSelected: Boolean, favoriteDeselected: Int?) {
        if (isSelected) {
            favoriteFilterIsSelected = true
            val updatedFavoriteList: MutableList<DiagnosticModel> = emptyList<DiagnosticModel>().toMutableList()
            uiState.value.favoriteDiagnosticsList.forEach { favoriteDiagnostic ->
                uiState.value.currentDiagnosticsList.forEach { currentDiagnostic ->
                    if (currentDiagnostic.number == favoriteDiagnostic.diagnosticId && favoriteDiagnostic.isFavorite)
                        updatedFavoriteList.add(currentDiagnostic)
                }
            }
            val finalList:List<DiagnosticModel> = if (favoriteDeselected != null) {
                updatedFavoriteList.filter { it.number != favoriteDeselected }
            } else { updatedFavoriteList }
            _uiState.update { it.copy(currentDiagnosticsList = finalList, favoriteListLoaded = true) }
        } else {
            favoriteFilterIsSelected = false
            _uiState.update { it.copy(currentDiagnosticsList = diagnosticList, favoriteListLoaded = true) }
        }
    }

}