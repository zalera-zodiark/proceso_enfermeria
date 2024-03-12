package com.charros_software.proceso_enfermeria.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteDiagnostic::class, NursingProcessDiagnostics::class, NursingProcessCollection::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDiagnosticDao(): FavoriteDiagnosticDao
    abstract fun nursingProcessCollectionDao(): NursingProcessCollectionDao
    abstract fun nursingProcessDiagnosticsDao(): NursingProcessDiagnosticsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nursing_process_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
