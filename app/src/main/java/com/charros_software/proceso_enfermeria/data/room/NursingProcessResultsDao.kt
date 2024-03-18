package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NursingProcessResultsDao {
    @Query(
        "INSERT INTO nursing_process_results(idResult, collection_ref) " +
                "VALUES(:idResult, :refCollection)"
    ) suspend fun addResultToCollection(idResult: Int, refCollection: Int)

    @Query(
        "SELECT * FROM nursing_process_results WHERE idResult = :idResult AND collection_ref = :collectionRef"
    ) suspend fun checkDuplicate(idResult: Int, collectionRef: Int): NursingProcessResults?

    @Query(
        "SELECT * FROM nursing_process_results WHERE collection_ref = :idCollection"
    ) suspend fun getResultsByCollectionId(idCollection: Int): List<NursingProcessResults>

    @Query(
        "DELETE FROM nursing_process_results WHERE idResult = :idResult"
    ) suspend fun deleteResultById(idResult: Int)
}