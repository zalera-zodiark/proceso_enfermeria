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

    @Transaction
    @Query(
        "SELECT * FROM nursing_process_collection"
    ) suspend fun getCollectionWithInterventions(): List<CollectionWithInterventions>

    @Transaction
    @Query(
        "SELECT * FROM nursing_process_collection"
    ) suspend fun getCollectionWithResults(): List<CollectionWithResults>

    @Query(
        "INSERT INTO nursing_process_collection(name, date) VALUES(:name, :date)"
    ) suspend fun addProcessCollection(name: String, date: String)

    @Query(
        "SELECT * FROM nursing_process_collection WHERE name = :name"
    ) suspend fun checkCollectionExist(name: String): NursingProcessCollection?

    @Query(
        "SELECT * FROM nursing_process_collection"
    ) suspend fun getCollectionsList(): List<NursingProcessCollection>

    @Query(
        "DELETE FROM nursing_process_collection WHERE idNursingProcessCollection = :idCollection"
    ) suspend fun deleteCollectionById(idCollection: Int)

    @Query(
        "SELECT * FROM nursing_process_collection WHERE idNursingProcessCollection = :idCollection"
    ) suspend fun getCollectionById(idCollection: Int): NursingProcessCollection?
}