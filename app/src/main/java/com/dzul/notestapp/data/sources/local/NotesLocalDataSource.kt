package com.dzul.notestapp.data.sources.local

import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class NotesLocalDataSource @Inject constructor (
    private val notesDao: NotesDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun insertNote(title: String, content: String) {
        val noteId: UUID = withContext(dispatcher) {
            UUID.randomUUID()
        }

        val localNote = LocalNotes(
            uid = noteId,
            title = title,
            content = content
        )

        notesDao.insertNote(localNote)
    }

    fun getAllNotes(): Flow<List<LocalNotes>> = notesDao.observeGetAll()

    fun getNoteById(noteId: UUID): Flow<LocalNotes?> = notesDao.observeGetNoteById(noteId)

    fun deleteNoteById(noteId: UUID) = notesDao.deleteNoteById(noteId)

    fun updateNote(note: LocalNotes) = notesDao.updateNote(note)
}