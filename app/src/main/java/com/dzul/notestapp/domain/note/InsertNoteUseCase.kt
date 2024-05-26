package com.dzul.notestapp.domain.note

import com.dzul.notestapp.data.repositories.NotesRepository
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface InsertNoteUseCase {
   suspend fun invoke(title: String, content: String)
}

class InsertNoteUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): InsertNoteUseCase {
    override suspend operator fun invoke(title: String, content: String) = withContext(defaultDispatcher) {
        notesRepository.insertNote(title, content)
    }
}