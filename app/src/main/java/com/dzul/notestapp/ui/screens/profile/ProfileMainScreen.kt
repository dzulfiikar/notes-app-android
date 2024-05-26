package com.dzul.notestapp.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.dzul.notestapp.R
import kotlin.coroutines.coroutineContext

@Composable
fun ProfileMainScreen(
    viewModel: ProfileMainScreenViewModel = hiltViewModel<ProfileMainScreenViewModel>(),
    modifier: Modifier = Modifier
) {
    val nameState by viewModel.name.collectAsState()
    val profileImageState by viewModel.profileImage.collectAsState()
    ProfileMainScreenContent(
        name = nameState,
        onNameValueChanged = { newValue ->
            viewModel.updateName(newValue)
        },
        onNameSaved = {
            viewModel.saveProfileName()
        },
        profileImage = profileImageState,
        onProfileImageChanged = { newValue ->
            viewModel.updateProfileImage(newValue)
            viewModel.saveProfileImage()
        },
        modifier = modifier
    )
}

@Composable
fun ProfileMainScreenContent(
    name: String,
    onNameValueChanged: (String) -> Unit,
    onNameSaved: () -> Unit,
    profileImage: Uri,
    onProfileImageChanged: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        ProfileImageComponent(
            profileImage = profileImage,
            onProfileImageChanged = onProfileImageChanged
        )
        ProfileNameComponent(
            name = name,
            onNameValueChanged = { onNameValueChanged(it) },
            onNameSaved = onNameSaved
        )
    }
}

@Composable
fun ProfileImageComponent(
    profileImage: Uri,
    onProfileImageChanged: (Uri) -> Unit
) {
    val context = LocalContext.current
    val resourcePainter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(if (profileImage != Uri.EMPTY) profileImage else R.drawable.user)
            .size(Size.ORIGINAL)
            .build()
    )
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if(uri == Uri.EMPTY || uri == null) return@rememberLauncherForActivityResult

            val input = context.contentResolver.openInputStream(uri) ?: return@rememberLauncherForActivityResult
            val outputFile = context.filesDir.resolve("profilePic.jpg")
            input.copyTo(outputFile.outputStream())
            onProfileImageChanged(outputFile.toUri())
        }
    )
    Image(
        painter = resourcePainter,
        contentDescription = "Profile Image",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(16.dp)
            .size(150.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.White
                ),
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(true, onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly))
                )
            })
            .background(Color.White)
            .padding(16.dp)
    )
}


@Composable
fun ProfileNameComponent(
    name: String,
    onNameValueChanged: (String) -> Unit,
    onNameSaved: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = name,
        onValueChange = { onNameValueChanged(it) },
        enabled = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onNameSaved()
            }
        ),
        textStyle = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
        ),
        maxLines = 1
    )
}


@Preview(
    showBackground = true
)
@Composable
fun ProfileMainScreenContentPreview() {
    var name by remember {
        mutableStateOf("")
    }
    ProfileMainScreenContent(
        name = name,
        onNameValueChanged = {
            name = it
        },
        onNameSaved = {

        },
        profileImage = Uri.EMPTY,
        onProfileImageChanged = {}
    )
}