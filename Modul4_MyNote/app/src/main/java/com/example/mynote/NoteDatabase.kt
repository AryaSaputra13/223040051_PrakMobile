package com.example.mynote

import android.content.Context
import androidx.compose.runtime.CompositionLocalContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynote.dao.NoteDao
import com.example.mynote.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}