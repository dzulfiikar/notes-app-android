package com.dzul.notestapp.di

import com.dzul.notestapp.domain.note.DeleteNoteUseCase
import com.dzul.notestapp.domain.note.DeleteNoteUseCaseImpl
import com.dzul.notestapp.domain.note.GetNoteByIdUseCase
import com.dzul.notestapp.domain.note.GetNoteByIdUseCaseImpl
import com.dzul.notestapp.domain.note.GetNotesUseCase
import com.dzul.notestapp.domain.note.GetNotesUseCaseImpl
import com.dzul.notestapp.domain.note.InsertNoteUseCase
import com.dzul.notestapp.domain.note.InsertNoteUseCaseImpl
import com.dzul.notestapp.domain.note.UpdateNoteUseCase
import com.dzul.notestapp.domain.note.UpdateNoteUseCaseImpl
import com.dzul.notestapp.domain.profile.GetProfileImageUseCase
import com.dzul.notestapp.domain.profile.GetProfileImageUseCaseImpl
import com.dzul.notestapp.domain.profile.GetProfileNameUseCase
import com.dzul.notestapp.domain.profile.GetProfileNameUseCaseImpl
import com.dzul.notestapp.domain.profile.SaveProfileImageUseCase
import com.dzul.notestapp.domain.profile.SaveProfileImageUseCaseImpl
import com.dzul.notestapp.domain.profile.SaveProfileNameUseCase
import com.dzul.notestapp.domain.profile.SaveProfileNameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Singleton
    @Binds
    abstract fun bindInsertNoteUseCase(useCase: InsertNoteUseCaseImpl): InsertNoteUseCase

    @Singleton
    @Binds
    abstract fun bindGetNotesUseCase(useCase: GetNotesUseCaseImpl): GetNotesUseCase

    @Singleton
    @Binds
    abstract fun bindGetNoteByIdUseCase(useCase: GetNoteByIdUseCaseImpl): GetNoteByIdUseCase

    @Singleton
    @Binds
    abstract fun bindDeleteNoteUseCase(useCase: DeleteNoteUseCaseImpl): DeleteNoteUseCase

    @Singleton
    @Binds
    abstract fun bindUpdateNoteUse(useCase: UpdateNoteUseCaseImpl): UpdateNoteUseCase

    @Singleton
    @Binds
    abstract fun bindSaveProfileNameUseCase(useCase: SaveProfileNameUseCaseImpl): SaveProfileNameUseCase

    @Singleton
    @Binds
    abstract fun bindGetProfileNameUseCase(useCase: GetProfileNameUseCaseImpl): GetProfileNameUseCase

    @Singleton
    @Binds
    abstract fun bindSaveProfileImageUseCase(useCase: SaveProfileImageUseCaseImpl): SaveProfileImageUseCase

    @Singleton
    @Binds
    abstract fun bindGetProfileImageUseCase(useCase: GetProfileImageUseCaseImpl): GetProfileImageUseCase
}