package com.charros_software.proceso_enfermeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charros_software.proceso_enfermeria.data.room.RoomRepository
import com.charros_software.proceso_enfermeria.domain.model.ResultModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel(
    private val roomRepository: RoomRepository,
    resultList: List<ResultModel>
): ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()
    private lateinit var resultList: List<ResultModel>
    private var favoriteFilterIsSelected = false

    init {
        updateFavoriteResults()
        updateCollectionsList()
        updateResultsList(resultList)
    }

    private fun updateResultsList(resultList: List<ResultModel>) {
        val orderList = resultList.sortedBy { it.number }
        this.resultList = orderList

        _uiState.update { it.copy(currentResultsList = orderList) }
    }

    private fun updateCollectionsList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(collectionsList = roomRepository.getNursingProcessCollectionList())
            }
        }
    }

    private fun updateFavoriteResults() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(favoriteResultsList = roomRepository.getFavoriteResultsList())
            }
        }
    }

    fun setFavoriteResult(resultNumber: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            if (roomRepository.checkFavoriteResultExistOrNull(resultNumber) != null) {
                roomRepository.updateFavoriteResult(resultNumber, isFavorite)
            } else {
                roomRepository.addFavoriteResult(resultNumber)
            }
            _uiState.update { it.copy(favoriteResultsList = roomRepository.getFavoriteResultsList()) }
        }
        if (favoriteFilterIsSelected) {
            filterFavoriteResultList(true, resultNumber)
        }
    }

    fun filterFavoriteResultList(isSelected: Boolean, favoriteDeselected: Int?) {
        if (isSelected) {
            favoriteFilterIsSelected = true
            _uiState.update { it.copy(currentResultsList = resultList) }
            val updatedFavoriteList: MutableList<ResultModel> =
                emptyList<ResultModel>().toMutableList()
            uiState.value.favoriteResultsList.forEach { favoriteResult ->
                uiState.value.currentResultsList.forEach { currentResult ->
                    if (currentResult.number == favoriteResult.resultId && favoriteResult.isFavorite)
                        updatedFavoriteList.add(currentResult)
                }
            }
            val finalList: List<ResultModel> = if (favoriteDeselected != null) {
                updatedFavoriteList.filter { it.number != favoriteDeselected }
            } else {
                updatedFavoriteList
            }
            _uiState.update { it.copy(currentResultsList = finalList) }
        } else {
            favoriteFilterIsSelected = false
            _uiState.update { it.copy(currentResultsList = resultList) }
        }
    }

    fun addResultToCollection(idCollection: Int, resultId: Int) {
        viewModelScope.launch {
            if (roomRepository.checkResultAlreadyInCollection(idCollection, resultId)) {
                _uiState.update {
                    it.copy(
                        isResultDuplicateError = true,
                        idCollectionResultDuplicateError = idCollection
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        isResultDuplicateError = false,
                        idCollectionResultDuplicateError = -1
                    )
                }
            } else {
                roomRepository.addResultToCollection(idCollection, resultId)
                _uiState.update { it.copy(showResultAddedToCollectionMessage = true) }
                delay(10)
                _uiState.update { it.copy(showResultAddedToCollectionMessage = false) }
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
        val newList = resultList.filter {
            it.result.uppercase().contains(value.trim().uppercase()) ||
                    it.number.toString().contains(value, true)
        }
        _uiState.update { it.copy(currentResultsList = newList) }
    }
}