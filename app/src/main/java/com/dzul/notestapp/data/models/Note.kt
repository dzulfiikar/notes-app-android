package com.dzul.notestapp.data.models

data class Note(
    val id: String,
    val title: String,
    val content: String,
)

fun fakeListOfNotes() = listOf<Note>(
    Note(
        id = "1",
        title = "Hello",
        content = "World"
    ),
    Note(
        id = "2",
        title = "Hello",
        content = "World"
    ),
    Note(
        id = "3",
        title = "Hello",
        content = "World"
    ),
)