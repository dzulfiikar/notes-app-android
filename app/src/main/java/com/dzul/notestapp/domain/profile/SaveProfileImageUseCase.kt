package com.dzul.notestapp.domain.profile

import android.net.Uri
import com.dzul.notestapp.data.repositories.ProfileRepository
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface SaveProfileImageUseCase {
    suspend fun invoke(image: Uri)
}

class SaveProfileImageUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    @DefaultDispatcher private val context: CoroutineDispatcher
): SaveProfileImageUseCase {
    override suspend fun invoke(image: Uri) = withContext(context) {
        profileRepository.saveImage(image)
    }
}