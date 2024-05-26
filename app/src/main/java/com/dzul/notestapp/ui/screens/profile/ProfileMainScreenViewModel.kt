package com.dzul.notestapp.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzul.notestapp.domain.profile.GetProfileImageUseCase
import com.dzul.notestapp.domain.profile.GetProfileNameUseCase
import com.dzul.notestapp.domain.profile.SaveProfileImageUseCase
import com.dzul.notestapp.domain.profile.SaveProfileNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMainScreenViewModel @Inject constructor(
    private val saveProfileNameUseCase: SaveProfileNameUseCase,
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val saveProfileImageUseCase: SaveProfileImageUseCase,
    private val getProfileImageUseCase: GetProfileImageUseCase
): ViewModel() {

    private val _name: MutableStateFlow<String> = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _profileImage: MutableStateFlow<Uri> = MutableStateFlow<Uri>(Uri.EMPTY)
    val profileImage: StateFlow<Uri> = _profileImage.asStateFlow()

    init {
        loadProfileName()
        loadProfileImage()
    }

    private fun loadProfileName() {
        viewModelScope.launch {
            getProfileNameUseCase.invoke().collect {
                if(it.isBlank()) {
                    updateName("User")
                } else {
                    updateName(it)
                }
            }
        }
    }

    private fun loadProfileImage() {
        viewModelScope.launch {
            getProfileImageUseCase.invoke().collect { imageUri ->
                Log.d(ProfileMainScreenViewModel::class.simpleName, "$imageUri")
                updateProfileImage(imageUri)
            }
        }
    }

    fun updateName(input: String) {
        _name.value = input
    }

    fun saveProfileName() = viewModelScope.launch {
        saveProfileNameUseCase.invoke(name.value)
    }

    fun updateProfileImage(input: Uri) {
        _profileImage.value = input
    }

    fun saveProfileImage() = viewModelScope.launch {
        saveProfileImageUseCase.invoke(profileImage.value)
    }

}

class ProfileMainScreenFakeViewModel(): ViewModel() {
    var name by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            updateName("Hello World")
        }
    }

    fun updateName(input: String) {
        name = input
    }

    fun saveProfileName() = viewModelScope.launch {

    }
}