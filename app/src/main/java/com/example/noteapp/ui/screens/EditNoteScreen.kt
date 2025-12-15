package com.example.noteapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteColor
import com.example.noteapp.ui.components.ColorSelection
import com.example.noteapp.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NoteViewModel,
    note: Note,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    var selectedColor by remember { mutableStateOf(NoteColor.fromCode(note.color)) }
    var showColorPicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val hasChanges = title != note.title || content != note.content || selectedColor.colorCode != note.color

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактировать заметку") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showColorPicker = !showColorPicker },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode))
                        )
                    ) {
                        Icon(
                            Icons.Default.ColorLens,
                            contentDescription = "Выбрать цвет"
                        )
                    }
                    IconButton(
                        onClick = {
                            if (title.isNotEmpty() && content.isNotEmpty()) {
                                coroutineScope.launch {
                                    viewModel.updateNote(note.id, title, content, note.createdAt, selectedColor)
                                    onBack()
                                }
                            }
                        },
                        enabled = title.isNotEmpty() && content.isNotEmpty() && hasChanges
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Сохранить"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            if (showColorPicker) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Выберите цвет заметки",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        ColorSelection(
                            selectedColor = selectedColor,
                            onColorSelected = { color ->
                                selectedColor = color
                                showColorPicker = false
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Заголовок") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode)),
                        unfocusedBorderColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode)).copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Содержание") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = Int.MAX_VALUE,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode)),
                        unfocusedBorderColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode)).copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(android.graphics.Color.parseColor(selectedColor.colorCode)).copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Текущий цвет",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = selectedColor.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(android.graphics.Color.parseColor(selectedColor.colorCode)))
                        )
                    }
                }
            }
        }
    }
}