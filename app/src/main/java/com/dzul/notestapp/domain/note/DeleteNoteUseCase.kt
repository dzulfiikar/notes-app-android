package com.dzul.notestapp.domain.note

import com.dzul.notestapp.data.repositories.NotesRepository
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteNoteUseCase {
    suspend fun invoke(noteId: String)
}

class DeleteNoteUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : DeleteNoteUseCase {
    override suspend fun invoke(noteId: String) = withContext(defaultDispatcher) {
        notesRepository.deleteNoteById(noteId)
    }
}

class DeleteNoteUseCaseFakeImpl: DeleteNoteUseCase {
    override suspend fun invoke(noteId: String) {
        TODO("Not yet implemented")
    }

}