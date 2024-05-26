package com.dzul.notestapp.ui.screens.notes.child

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dzul.notestapp.ui.screens.notes.NoteMainScreenViewModel


@Composable
fun EditNoteScreen(
    navController: NavHostController,
    viewModel: NoteMainScreenViewModel = hiltViewModel<NoteMainScreenViewModel>(),
    modifier: Modifier = Modifier
) {
    EditNoteScreenContent(
        title = viewModel.title,
        onTitleValueChange = { newValue -> viewModel.updateTitle(newValue) },
        content = viewModel.content,
        onContentValueChange = { newValue -> viewModel.updateContent(newValue) },
        onBackAction = { navController.popBackStack() },
        onSaveAction = {
            val updateNoteResult = viewModel.updateNote()
            if(updateNoteResult.isOk) {
                navController.popBackStack()
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditNoteScreenContent(
    title: String,
    onTitleValueChange: (String) -> Unit,
    content: String,
    onContentValueChange: (String) -> Unit,
    onBackAction: () -> Unit,
    onSaveAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Edit Note")
                },
                navigationIcon = {
                    IconButton(onClick = onBackAction) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Return To Detail Note Screen")
                    }
                },
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            OutlinedTextField(
                label = { Text(text = "Title") },
                value = title,
                onValueChange = onTitleValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            OutlinedTextField(
                label = { Text(text = "Content") },
                value = content,
                onValueChange = onContentValueChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    onSaveAction()
                },
                modifier = Modifier
            ) {
                Text(text = "SAVE")
            }
        }
    }
}

@Preview
@Composable
fun EditNoteContentPreview() {
    EditNoteScreenContent(
        title = "Test Title",
        onTitleValueChange = {},
        content = "Test Content",
        onContentValueChange = {},
        onBackAction = { /*TODO*/ },
        onSaveAction = { /*TODO*/ })
}