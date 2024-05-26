package com.dzul.notestapp.domain.profile

import com.dzul.notestapp.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetProfileNameUseCase {
    fun invoke(): Flow<String>
}

class GetProfileNameUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): GetProfileNameUseCase {
    override fun invoke(): Flow<String> = profileRepository.getName()
}