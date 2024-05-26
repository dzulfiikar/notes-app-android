package com.dzul.notestapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalNotes::class], version = 1, exportSchema = false)
abstract class NotesAppDatabase: RoomDatabase() {
    abstract fun notesDAO(): NotesDao
}