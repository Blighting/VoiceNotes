package com.example.voicenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.voicenotes.core.theme.VoiceNotesTheme
import com.example.voicenotes.feature.notesList.ui.NotesScreen
import com.example.voicenotes.feature.permissions.RequestFileAndAudioRecordPermission

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceNotesTheme {
                NotesScreen()
            }
        }
    }
}