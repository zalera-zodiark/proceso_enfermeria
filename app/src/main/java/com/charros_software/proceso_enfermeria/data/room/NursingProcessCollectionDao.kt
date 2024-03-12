package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface NursingProcessCollectionDao {
    @Transaction
    @Query(
        "SELECT * FROM nursing_process_collection"
    ) suspend fun getCollectionWithDiagnostics(): List<CollectionWithDiagnostics>

    @Query(
        "INSERT INTO nursing_process_collection(name) VALUES(:name)"
    ) suspend fun addProcessCollection(name: String)

    @Query(
        "SELECT * FROM nursing_process_collection WHERE name = :name"
    ) suspend fun checkCollectionExist(name: String): NursingProcessCollection?
}