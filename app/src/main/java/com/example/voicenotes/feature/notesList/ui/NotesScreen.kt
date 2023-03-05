package com.example.voicenotes.feature.notesList.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.voicenotes.R
import com.example.voicenotes.core.utils.CollectEffect
import com.example.voicenotes.feature.notesList.NotesEffect
import com.example.voicenotes.feature.notesList.NotesViewModel
import com.example.voicenotes.feature.notesList.SaveDialogEvent
import com.example.voicenotes.shared.SaveDialog
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SaveDialog(
        value = state.dialogState.name,
        visible = state.dialogState.visible,
        correct = state.dialogState.isError,
        actionButtonEnabled = !state.dialogState.isError,
        onSave = remember { { viewModel.onEvent(SaveDialogEvent.Save) } },
        onCancel = remember { { viewModel.onEvent(SaveDialogEvent.Cancel) } },
        onValueChange = remember { { viewModel.onEvent(SaveDialogEvent.ValueChange(it)) } },
    )
    NotesBody(
        notes = state.notesState.notes.values.toImmutableList(),
        isRecording = state.recorderState.recording,
        recordButtonScale = state.recorderState.recordButtonScale,
        recordProgress = state.recorderState.progress,
        onEvent = viewModel::onEvent,
    )
    val context = LocalContext.current
    val noSuchTrackText = stringResource(R.string.dialog_note_no_such_track)
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is NotesEffect.NoSuchTrack -> Toast.makeText(
                context,
                noSuchTrackText,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}