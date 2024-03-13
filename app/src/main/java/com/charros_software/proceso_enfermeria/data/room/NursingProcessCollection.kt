package com.charros_software.proceso_enfermeria.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nursing_process_collection")
data class NursingProcessCollection(
    @PrimaryKey(autoGenerate = true) val idNursingProcessCollection: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: String
)
