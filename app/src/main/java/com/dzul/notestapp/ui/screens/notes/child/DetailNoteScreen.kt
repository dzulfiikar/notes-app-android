package com.dzul.notestapp.ui.screens.notes.child

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dzul.notestapp.NoteChildRoutes
import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.ui.screens.notes.NoteMainScreenViewModel

@Composable
fun DetailNoteScreen(
    navHostController: NavHostController,
    viewModel: NoteMainScreenViewModel = hiltViewModel<NoteMainScreenViewModel>(),
    modifier: Modifier = Modifier
) {
    val selectedNote by viewModel.selectedNote.collectAsState()
    if(selectedNote !== null) {
        DetailNoteScreenContent(
            note = selectedNote!!,
            onReturnAction = {
                navHostController.popBackStack()
            },
            onDeleteAction = {
                val deleteNoteResult = viewModel.deleteNote()
                if(deleteNoteResult.isOk) {
                    navHostController.popBackStack()
                }
            },
            onEditAction = {
                navHostController.navigate("${NoteChildRoutes.Edit.route}/${viewModel.selectedNote.value!!.id}")
            },
            modifier = modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNoteScreenContent(
    note: Note,
    onReturnAction: () -> Unit,
    onEditAction: () -> Unit,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dropdownExpanded by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onReturnAction) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { dropdownExpanded = !dropdownExpanded }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                    DropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Edit",
                                        modifier = Modifier.weight(1f),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    )
                                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Note")
                                }
                            },
                            onClick = {
                                dropdownExpanded = !dropdownExpanded
                                onEditAction()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Delete",
                                        modifier = Modifier.weight(1f),
                                        style = TextStyle(
                                            color = Color.Red,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    )
                                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Note", tint = Color.Red)
                                }
                            },
                            onClick = {
                                dropdownExpanded = false
                                onDeleteAction()
                            }
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text(
                text = "Title",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(vertical = 8.dp)
            )
            Text(
                text = note.title,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Content",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(vertical = 8.dp)
            )
            Text(
                text = note.content,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                softWrap = true
            )
        }
    }
}

@Preview
@Composable
fun DetailNoteScreenContentPreview() {
    MaterialTheme {
        DetailNoteScreenContent(
            note = Note(
                id = "1",
                title = "Hello World",
                content = "THIS IS A HELLO WORLD!"
            ),
            onReturnAction = {},
            onDeleteAction = {},
            onEditAction = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

