package com.dzul.notestapp.data.repositories

import android.util.Log
import com.dzul.notestapp.data.mappers.toDomain
import com.dzul.notestapp.data.mappers.toLocal
import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.sources.local.NotesLocalDataSource
import com.dzul.notestapp.data.sources.network.FetchAllNoteResult
import com.dzul.notestapp.data.sources.network.NoteAPIService
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(title: String, content: String)
    fun getNoteById(noteId: String): Flow<Note?>?
    suspend fun deleteNoteById(noteId: String)
    suspend fun updateNote(note: Note)
}

class NotesRepositoryImpl @Inject constructor(
    private val notesLocalDataSource: NotesLocalDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): NotesRepository {
    override fun getAllNotes(): Flow<List<Note>> = notesLocalDataSource.getAllNotes().map { it -> it.map { it.toDomain() } }

    override suspend fun insertNote(title: String, content: String) = notesLocalDataSource.insertNote(title, content)

    override fun getNoteById(noteId: String): Flow<Note?>? {
        try {
            val noteUid = UUID.fromString(noteId)
            return notesLocalDataSource.getNoteById(noteUid).map { it?.toDomain() }
        } catch (err: Error) {
            return null
        }
    }

    override suspend fun deleteNoteById(noteId: String) = notesLocalDataSource.deleteNoteById(UUID.fromString(noteId))

    override suspend fun updateNote(note: Note) = notesLocalDataSource.updateNote(note.toLocal())
}