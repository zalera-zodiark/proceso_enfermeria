package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NursingProcessInterventionsDao {
    @Query(
        "INSERT INTO nursing_process_interventions(idIntervention, collection_ref) " +
                "VALUES(:idIntervention, :refCollection)"
    ) suspend fun addInterventionToCollection(idIntervention: Int, refCollection: Int)

    @Query(
        "SELECT * FROM nursing_process_interventions WHERE idIntervention = :idIntervention AND collection_ref = :collectionRef"
    ) suspend fun checkDuplicate(idIntervention: Int, collectionRef: Int): NursingProcessInterventions?

    @Query(
        "SELECT * FROM nursing_process_interventions WHERE collection_ref = :idCollection"
    ) suspend fun getInterventionsByCollectionId(idCollection: Int): List<NursingProcessInterventions>

    @Query(
        "DELETE FROM nursing_process_interventions WHERE idIntervention = :idIntervention"
    ) suspend fun deleteInterventionById(idIntervention: Int)
}