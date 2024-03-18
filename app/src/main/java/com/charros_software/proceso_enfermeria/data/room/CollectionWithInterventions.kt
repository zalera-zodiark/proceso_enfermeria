package com.charros_software.proceso_enfermeria.data.room

import androidx.room.Embedded
import androidx.room.Relation

data class CollectionWithInterventions(
    @Embedded val collection: NursingProcessCollection,
    @Relation(
        parentColumn = "idNursingProcessCollection",
        entityColumn = "collection_ref"
    )
    val interventions: List<NursingProcessInterventions>
)