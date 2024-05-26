package com.dzul.notestapp.domain.profile

import com.dzul.notestapp.data.repositories.ProfileRepository
import com.dzul.notestapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveProfileNameUseCase {
    suspend fun invoke(name: String)
}

class SaveProfileNameUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): SaveProfileNameUseCase {
    override suspend fun invoke(name: String) = withContext(defaultDispatcher) {
        profileRepository.saveName(name)
    }
}