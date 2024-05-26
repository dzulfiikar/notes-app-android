package com.dzul.notestapp.ui.screens.notes.child

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dzul.notestapp.ui.screens.notes.NoteMainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddNotesScreen(
    navController: NavHostController,
    viewModel: NoteMainScreenViewModel = hiltViewModel<NoteMainScreenViewModel>(),
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Create Note")
                },
                navigationIcon = {
                     IconButton(onClick = { navController.popBackStack() }) {
                         Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Return To Notes Screen")
                     }
                },
            )
        }
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
                value = viewModel.title,
                onValueChange = { newValue -> viewModel.updateTitle(newValue) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            OutlinedTextField(
                label = { Text(text = "Content") },
                value = viewModel.content,
                onValueChange = { newValue -> viewModel.updateContent(newValue) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    val insertNoteResult = viewModel.insertNote()
                    if(insertNoteResult.isOk) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
            ) {
                Text(text = "SAVE")
            }
        }
    }
}
