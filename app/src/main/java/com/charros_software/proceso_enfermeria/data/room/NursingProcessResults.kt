package com.charros_software.proceso_enfermeria.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nursing_process_results")
data class NursingProcessResults(
    @PrimaryKey(autoGenerate = true) val idNursingProcessResults: Int,
    val idResult: Int,
    @ColumnInfo(name = "collection_ref") val collectionRef: Int
)