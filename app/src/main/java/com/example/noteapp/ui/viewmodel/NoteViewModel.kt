package com.example.noteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteColor
import com.example.noteapp.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    suspend fun insertNote(title: String, content: String, color: NoteColor = NoteColor.DEFAULT) {
        val note = Note(
            title = title,
            content = content,
            color = color.colorCode
        )
        repository.insertNote(note)
    }

    suspend fun updateNote(id: Long, title: String, content: String, originalCreatedAt: Date, color: NoteColor) {
        val note = Note(
            id = id,
            title = title,
            content = content,
            createdAt = originalCreatedAt,
            updatedAt = Date(),
            color = color.colorCode
        )
        repository.updateNote(note)
    }

    suspend fun deleteNoteById(id: Long) {
        repository.deleteNoteById(id)
    }
}