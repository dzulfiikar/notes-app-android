package com.dzul.notestapp.data.mappers

import com.dzul.notestapp.data.models.Note
import com.dzul.notestapp.data.sources.local.LocalNotes
import java.util.UUID

/**
 * This file is a mapper for models from
 * App Model is model exposed to other layers
 * 1. App Model to Local Model
 * 2. App Model to Network Model
 * Reference:
 * https://github.com/android/architecture-samples/blob/master/app/src/main/java/com/example/android/architecture/blueprints/todoapp/data/ModelMappingExt.kt
 */

// App Model to Local
fun Note.toLocal() = LocalNotes(
    uid = UUID.fromString(id),
    title = title,
    content = content
)

fun LocalNotes.toDomain() = Note(
    id = uid.toString(),
    title = title,
    content = content
)