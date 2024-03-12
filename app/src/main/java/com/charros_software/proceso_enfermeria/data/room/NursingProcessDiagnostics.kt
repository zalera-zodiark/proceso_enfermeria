package com.charros_software.proceso_enfermeria.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nursing_process_diagnostics")
data class NursingProcessDiagnostics(
    @PrimaryKey val idDiagnostic: Int,
    @ColumnInfo(name = "collection_ref") val collectionRef: Int
)
