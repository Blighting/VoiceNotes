package com.example.voicenotes.domain.usecase.notes

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.voicenotes.data.database.NotesDao
import com.example.voicenotes.domain.models.notes.NoteEntity
import java.io.File
import java.io.FileInputStream

class SaveNoteUseCaseImpl(
    private val dao: NotesDao,
    private val context: Context,
) : SaveNoteUseCase {
    override suspend fun saveNote(name: String, oldPath: String) {
        val noteEntity = saveIntoFiles(name, oldPath)
        if (noteEntity != null) dao.insertNote(noteEntity)
    }

    private fun saveIntoFiles(name: String, oldPath: String): NoteEntity? {
        val timestamp = System.currentTimeMillis()
        val resolver = context.contentResolver

        val values = prepareValues(name, timestamp, oldPath)
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val newUri = resolver.insert(audioUri, values) ?: return null

        writeFileToNewPath(newUri, resolver, oldPath)
        val length = getLength(context, newUri) ?: return null
        return NoteEntity(
            name = name,
            timestamp = timestamp,
            uri = newUri.toString(),
            duration = length
        )
    }

    private fun writeFileToNewPath(
        newUri: Uri,
        resolver: ContentResolver,
        oldPath: String
    ) {
        val outputStream = newUri.let { resolver.openOutputStream(it) }
        val inputStream = FileInputStream(oldPath)
        val buffer = ByteArray(4096)
        var bytesRead = 0
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream?.write(buffer, 0, bytesRead)
        }
        outputStream?.close()
        inputStream.close()
    }

    private fun prepareValues(
        name: String,
        timestamp: Long,
        oldPath: String
    ): ContentValues {
        val values = ContentValues()
        values.apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, name)
            put(MediaStore.Audio.Media.MIME_TYPE, "audio/mpeg")
            put(MediaStore.Audio.Media.DATE_ADDED, timestamp)
            put(MediaStore.Audio.Media.SIZE, File(oldPath).length())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
            }
        }
        return values
    }

    private fun getLength(context: Context, uri: Uri): Long? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return duration?.toLong()
    }
}