package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NursingProcessDiagnosticsDao {
    @Query(
        "INSERT INTO nursing_process_diagnostics(idDiagnostic, collection_ref) " +
                "VALUES(:idDiagnostic, :refCollection)"
    ) suspend fun addDiagnosticToCollection(idDiagnostic:Int, refCollection: Int)

    @Query(
        "SELECT * FROM nursing_process_diagnostics WHERE idDiagnostic = :idDiagnostic AND collection_ref = :collectionRef"
    ) suspend fun checkDuplicate(idDiagnostic: Int, collectionRef: Int): NursingProcessDiagnostics?
}