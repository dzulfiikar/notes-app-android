package com.dzul.notestapp.domain.note

import android.util.Log
import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.repositories.NotesRepository
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UpdateNoteUseCase {
    suspend fun invoke(note: Note)
}

class UpdateNoteUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): UpdateNoteUseCase {
    override suspend fun invoke(note: Note) = withContext(defaultDispatcher) {
        Log.d(UpdateNoteUseCase::class.simpleName, "NOTE: ${note}")
        notesRepository.updateNote(note)
    }
}