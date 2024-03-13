package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiagnosticViewModel(
    private val roomRepository: RoomRepository,
    diagnosticList: List<DiagnosticModel>
): ViewModel() {

    private val _uiState = MutableStateFlow(DiagnosticUiState())
    val uiState: StateFlow<DiagnosticUiState> = _uiState.asStateFlow()
    private lateinit var diagnosticList: List<DiagnosticModel>
    private var favoriteFilterIsSelected = false

    init {
        updateFavoriteDiagnostics()
        updateCollectionsList()
        updateDiagnosticsList(diagnosticList)
    }

    private fun updateDiagnosticsList(diagnosticList: List<DiagnosticModel>) {
        val orderList = diagnosticList.sortedBy { it.number }
        this.diagnosticList = orderList

        _uiState.update { it.copy(currentDiagnosticsList = orderList) }
    }

    private fun updateCollectionsList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(collectionsList = roomRepository.getNursingProcessCollectionList()) }
        }
    }

    private fun updateFavoriteDiagnostics() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(favoriteDiagnosticsList = roomRepository.getFavoriteDiagnosticsList()) }
        }
    }

    fun setFavoriteDiagnostic(diagnosticNumber: Int, isFavorite: Boolean) {
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
            _uiState.update { it.copy(currentDiagnosticsList = finalList) }
        } else {
            favoriteFilterIsSelected = false
            _uiState.update { it.copy(currentDiagnosticsList = diagnosticList) }
        }
    }

    fun addDiagnosticToCollection(idCollection: Int, diagnosticId: Int) {
        viewModelScope.launch {
            if (roomRepository.checkDiagnosticAlreadyInCollection(idCollection, diagnosticId)) {
                viewModelScope.launch {
                    _uiState.update { it.copy(isDiagnosticDuplicateError = true, idCollectionDiagnosticDuplicateError = idCollection) }
                    delay(3000)
                    _uiState.update { it.copy(isDiagnosticDuplicateError = false, idCollectionDiagnosticDuplicateError = -1) }
                }
            } else {
                roomRepository.addDiagnosticToCollection(idCollection, diagnosticId)
            }
        }
    }

    fun addNewCollection(newCollection: String) {
        viewModelScope.launch {
            if (roomRepository.checkCollectionExist(newCollection) == null) {
                roomRepository.addNewCollection(newCollection)
                updateCollectionsList()
            } else {
                _uiState.update { it.copy(isCollectionDuplicateError = true) }
            }
        }
    }

    fun setOffCollectionDuplicateError() {
        _uiState.update { it.copy(isCollectionDuplicateError = false) }
    }

}