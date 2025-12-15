package com.example.noteapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.ui.components.EmptyNotesState
import com.example.noteapp.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onCreateNote: () -> Unit,
    onEditNote: (Note) -> Unit,
    onSettingsClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNote) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_note))
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_notes)) },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (notes.isEmpty()) {
            EmptyNotesState(onCreateNote = onCreateNote)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 8.dp)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onDeleteClick = {
                            coroutineScope.launch {
                                viewModel.deleteNoteById(note.id)
                            }
                        },
                        onEditClick = {
                            onEditNote(note)
                        }
                    )
                }
            }
        }
    }
}