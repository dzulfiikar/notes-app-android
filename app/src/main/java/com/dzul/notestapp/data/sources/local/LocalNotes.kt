package com.dzul.notestapp.data.sources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "notes"
)
data class LocalNotes(
    @PrimaryKey val uid: UUID,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
)
