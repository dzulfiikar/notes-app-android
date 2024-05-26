package com.dzul.notestapp.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NotesDao {

    /**
     * Observe list of notes
     *
     * @return all notes
     */
    @Query("SELECT * FROM notes")
    fun observeGetAll(): Flow<List<LocalNotes>>

    /**
     * Select all from notes table
     *
     * @return all notes
     */
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<LocalNotes>

    @Insert()
    suspend fun insertNote(vararg note: LocalNotes)

    @Query("SELECT * FROM notes WHERE uid = :noteId LIMIT 1")
    fun getNoteById(noteId: UUID): LocalNotes?

    @Query("SELECT * FROM notes WHERE uid = :noteId LIMIT 1")
    fun observeGetNoteById(noteId: UUID): Flow<LocalNotes?>

    @Query("DELETE FROM notes WHERE uid = :noteId")
    fun deleteNoteById(noteId: UUID)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(vararg localNotes: LocalNotes)
}