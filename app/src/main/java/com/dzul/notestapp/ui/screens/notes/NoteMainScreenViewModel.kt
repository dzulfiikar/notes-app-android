package com.dzul.notestapp.ui.screens.notes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.models.fakeListOfNotes
import com.dzul.notestapp.data.sources.network.NoteAPIService
import com.dzul.notestapp.data.sources.network.Post
import com.dzul.notestapp.data.sources.network.PostItem
import com.dzul.notestapp.data.sources.network.fetchPost
import com.dzul.notestapp.domain.note.DeleteNoteUseCase
import com.dzul.notestapp.domain.note.GetNoteByIdUseCase
import com.dzul.notestapp.domain.note.GetNotesUseCase
import com.dzul.notestapp.domain.note.InsertNoteUseCase
import com.dzul.notestapp.domain.note.UpdateNoteUseCase
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NoteMainScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
): ViewModel() {
    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    val allNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

    var post by mutableStateOf<List<PostItem>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            getNotesUseCase.invoke().collect {
                _allNotes.value = it
            }
        }

        val noteId: String? = savedStateHandle["noteId"]
        noteId?.let {
            loadNoteById(it)
        }
    }

    fun testClick() = viewModelScope.launch {
        Log.d("TEST1", "HELLO")
        post = fetchPost()
        Log.d("TEST", "HELLO")
    }

    private fun loadNoteById(noteId: String) = viewModelScope.launch {
        getNoteByIdUseCase.invoke(noteId)?.collect { note ->
            _selectedNote.value = note
            note.let {
                if (it != null) {
                    title = it.title
                    content = it.content
                }
            }
        }
    }

    fun insertNote(): Result<Any?, Any?>  {
        if (title.isNotEmpty()  && content.isNotEmpty()) {
            viewModelScope.launch {
                insertNoteUseCase.invoke(title, content)
            }
            return Ok(null)
        }
        return Err(null)
    }

    fun deleteNote(): Result<Any?, Any?> {
        if(selectedNote.value != null) {
            viewModelScope.launch {
                deleteNoteUseCase.invoke(selectedNote.value!!.id)
            }
            _selectedNote.value = null
            return Ok(null)
        }
        return Err(null)
    }

    fun updateNote(): Result<Any?, Any?> {
        if(selectedNote.value != null) {
            viewModelScope.launch {
                val updateNote = Note(
                    id = selectedNote.value!!.id,
                    title = title,
                    content = content
                )
                updateNoteUseCase.invoke(updateNote)
            }
            return Ok(null)
        }
        return Err(null)
    }

    fun updateTitle(input: String) {
        title = input
    }

    fun updateContent(input: String) {
        content = input
    }
}

class NoteMainScreenFakeViewModel(): ViewModel() {
    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    val allNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            _allNotes.value = fakeListOfNotes()
        }
    }

    fun insertNote(): Job {
        TODO("Not yet implemented")
    }

    fun updateTitle(input: String) {
        TODO("Not yet implemented")
    }

    fun updateContent(input: String) {
        TODO("Not yet implemented")
    }

}