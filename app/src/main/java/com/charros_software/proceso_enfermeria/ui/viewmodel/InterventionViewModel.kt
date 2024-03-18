package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.domain.model.InterventionModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InterventionViewModel(
    private val roomRepository: RoomRepository,
    interventionList: List<InterventionModel>
): ViewModel() {

    private val _uiState = MutableStateFlow(InterventionUiState())
    val uiState: StateFlow<InterventionUiState> = _uiState.asStateFlow()
    private lateinit var interventionList: List<InterventionModel>
    private var favoriteFilterIsSelected = false

    init {
        updateFavoriteInterventions()
        updateCollectionsList()
        updateInterventionsList(interventionList)
    }

    private fun updateInterventionsList(interventionList: List<InterventionModel>) {
        val orderList = interventionList.sortedBy { it.number }
        this.interventionList = orderList

        _uiState.update { it.copy(currentInterventionsList = orderList) }
    }

    private fun updateCollectionsList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(collectionsList = roomRepository.getNursingProcessCollectionList())
            }
        }
    }

    private fun updateFavoriteInterventions() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(favoriteInterventionsList = roomRepository.getFavoriteInterventionsList())
            }
        }
    }

    fun setFavoriteIntervention(interventionNumber: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            if (roomRepository.checkFavoriteInterventionExistOrNull(interventionNumber) != null) {
                roomRepository.updateFavoriteIntervention(interventionNumber, isFavorite)
            } else {
                roomRepository.addFavoriteIntervention(interventionNumber)
            }
            _uiState.update { it.copy(favoriteInterventionsList = roomRepository.getFavoriteInterventionsList()) }
        }
        if (favoriteFilterIsSelected) {
            filterFavoriteInterventionList(true, interventionNumber)
        }
    }

    fun filterFavoriteInterventionList(isSelected: Boolean, favoriteDeselected: Int?) {
        if (isSelected) {
            favoriteFilterIsSelected = true
            val updatedFavoriteList: MutableList<InterventionModel> = emptyList<InterventionModel>().toMutableList()
            uiState.value.favoriteInterventionsList.forEach { favoriteIntervention ->
                uiState.value.currentInterventionsList.forEach { currentIntervention ->
                    if (currentIntervention.number == favoriteIntervention.interventionId && favoriteIntervention.isFavorite)
                        updatedFavoriteList.add(currentIntervention)
                }
            }
            val finalList: List<InterventionModel> = if (favoriteDeselected != null) {
                updatedFavoriteList.filter { it.number != favoriteDeselected }
            } else { updatedFavoriteList }
            _uiState.update { it.copy(currentInterventionsList = finalList) }
        } else {
            favoriteFilterIsSelected = false
            _uiState.update { it.copy(currentInterventionsList = interventionList) }
        }
    }

    fun addInterventionToCollection(idCollection: Int, interventionId: Int) {
        viewModelScope.launch {
            if (roomRepository.checkInterventionAlreadyInCollection(idCollection, interventionId)) {
                _uiState.update {
                    it.copy(
                        isInterventionDuplicateError = true,
                        idCollectionInterventionDuplicateError = idCollection
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        isInterventionDuplicateError = false,
                        idCollectionInterventionDuplicateError = -1
                    )
                }
            } else {
                roomRepository.addInterventionToCollection(idCollection, interventionId)
                _uiState.update { it.copy(showInterventionAddedToCollectionMessage = true) }
                delay(10)
                _uiState.update { it.copy(showInterventionAddedToCollectionMessage = false) }
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

    fun searchValueChange(value: String) {
        val newList = interventionList.filter {
            it.intervention.uppercase().contains(value.trim().uppercase()) ||
                    it.number.toString().contains(value, true)
        }
        _uiState.update { it.copy(currentInterventionsList = newList) }
    }
}