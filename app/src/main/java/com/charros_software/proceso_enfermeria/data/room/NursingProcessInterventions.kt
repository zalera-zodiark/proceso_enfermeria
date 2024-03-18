package com.charros_software.proceso_enfermeria.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nursing_process_interventions")
data class NursingProcessInterventions(
    @PrimaryKey(autoGenerate = true) val idNursingProcessInterventions: Int,
    val idIntervention: Int,
    @ColumnInfo(name = "collection_ref") val collectionRef: Int
)