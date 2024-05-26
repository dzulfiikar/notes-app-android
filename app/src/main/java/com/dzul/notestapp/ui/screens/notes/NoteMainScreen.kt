package com.dzul.notestapp.ui.screens.notes

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dzul.notestapp.NoteChildRoutes
import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.models.fakeListOfNotes
import com.dzul.notestapp.data.sources.network.ApiService
import com.dzul.notestapp.data.sources.network.Post
import com.dzul.notestapp.data.sources.network.fetchPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Composable
fun NoteMainScreen(
    navHostController: NavHostController,
    viewModel: NoteMainScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val notes by viewModel.allNotes.collectAsState()
    NoteMainScreenContent(
        notes = notes,
        onFabClicked = {
            navHostController.navigate(NoteChildRoutes.Add.route)
       },
        onDetailClicked = {
            navHostController.navigate("${NoteChildRoutes.Detail.route}/${it}")
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteMainScreenContent(
    notes: List<Note>,
    onFabClicked: () -> Unit,
    onDetailClicked: (noteId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = onFabClicked) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Note")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(title = { Text(text = "List Note") })
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            NotesList(notes = notes, onClick = { onDetailClicked(it) })
        }
    }
}

@Composable
fun NotesList(
    notes: List<Note>,
    onClick: (noteId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    notes.forEach {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { onClick(it.id) }
        ) {
            Text(
                text = it.title,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Divider (
                color = Color.White,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_MASK
)
@Composable
fun NoteMainScreenContentPreview() {
    NoteMainScreenContent(notes = fakeListOfNotes(), onFabClicked = { }, onDetailClicked = {  })
}

@Preview()
@Composable
fun NotesListPreview() {
    Surface {
        Column {
            NotesList(notes = fakeListOfNotes(), onClick = {  })
        }
    }
}