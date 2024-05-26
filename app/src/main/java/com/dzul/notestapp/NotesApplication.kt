package com.dzul.notestapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}