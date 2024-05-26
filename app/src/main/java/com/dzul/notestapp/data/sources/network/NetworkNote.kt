package com.dzul.notestapp.data.sources.network
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkNote(
    @SerializedName("id")
    var id: String,
    @SerializedName("note_id")
    var noteId: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("created_at")
    var createdAt: Date,
    @SerializedName("created_by")
    var createdBy: String,
    @SerializedName("updated_at")
    var updatedAt: Date,
    @SerializedName("updated_by")
    var updatedBy: String,
    @SerializedName("deleted")
    var deleted: Boolean
)

data class CreateNoteResult(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: NetworkNote,
    @SerializedName("message")
    var message: String
)

data class FetchAllNoteResult(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: List<NetworkNote>,
    @SerializedName("message")
    var message: String
)