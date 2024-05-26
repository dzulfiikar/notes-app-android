package com.dzul.notestapp.domain.note

import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNoteByIdUseCase {
    fun invoke(noteId: String): Flow<Note?>?
}

class GetNoteByIdUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository
): GetNoteByIdUseCase {
    override fun invoke(noteId: String): Flow<Note?>? = notesRepository.getNoteById(noteId)
}