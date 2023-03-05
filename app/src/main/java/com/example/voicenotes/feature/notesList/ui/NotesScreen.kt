package com.example.voicenotes.feature.notesList.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
}