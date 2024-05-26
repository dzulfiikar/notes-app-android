package com.dzul.notestapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.dzul.notestapp.data.repositories.NotesRepository
import com.dzul.notestapp.data.repositories.NotesRepositoryImpl
import com.dzul.notestapp.data.repositories.ProfileRepository
import com.dzul.notestapp.data.repositories.ProfileRepositoryImpl
import com.dzul.notestapp.data.sources.local.NotesAppDatabase
import com.dzul.notestapp.data.sources.local.NotesDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val PROFILE_PREFERENCES = "profile_preferences"

@Module
@InstallIn(SingletonComponent::class)
object ProfilePreferencesModule {

    @Provides
    @Singleton
    fun provideProfileDataStorePreferences(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                context = appContext,
                PROFILE_PREFERENCES,
            )
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(PROFILE_PREFERENCES) }
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNotesRepository(repository: NotesRepositoryImpl): NotesRepository

    @Singleton
    @Binds
    abstract fun bindProfileRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotesAppDatabase {
        return Room.databaseBuilder(
            context = context,
            NotesAppDatabase::class.java,
            "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNotesDao(database: NotesAppDatabase): NotesDao = database.notesDAO()
}