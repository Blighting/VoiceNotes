package com.example.voicenotes.domain.usecase.record

import android.content.Context
import android.media.MediaRecorder
import android.os.Build

class AudioRecordProvider(val context: Context) {
    val filesDirPath: String
        get() = context.getExternalFilesDir(null)!!.absolutePath

    fun provideAudioRecord(name: String): MediaRecorder {
        val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(name)
            prepare()
        }
        return recorder
    }
}