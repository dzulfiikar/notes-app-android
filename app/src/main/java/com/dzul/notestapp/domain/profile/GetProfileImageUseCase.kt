package com.dzul.notestapp.domain.profile

import android.net.Uri
import com.dzul.notestapp.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetProfileImageUseCase {
    fun invoke(): Flow<Uri>
}

class GetProfileImageUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): GetProfileImageUseCase {
    override fun invoke(): Flow<Uri> = profileRepository.getImage()
}