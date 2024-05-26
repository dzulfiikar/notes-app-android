package com.dzul.notestapp.domain.note

import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.repositories.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNotesUseCase {
    fun invoke(): Flow<List<Note>>
}

class GetNotesUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository
): GetNotesUseCase {
    override fun invoke(): Flow<List<Note>> = notesRepository.getAllNotes()

}

class GetNotesUseCaseFakeImpl: GetNotesUseCase {
    override fun invoke(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

}