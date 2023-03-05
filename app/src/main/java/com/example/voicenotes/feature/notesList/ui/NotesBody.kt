package com.example.voicenotes.feature.notesList.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voicenotes.R
import com.example.voicenotes.core.theme.VoiceNotesTheme
import com.example.voicenotes.domain.models.notes.NoteUi
import com.example.voicenotes.feature.notesList.NoteEvent
import com.example.voicenotes.feature.notesList.NotesScreenEvent
import com.example.voicenotes.feature.notesList.RecorderEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
private fun NotesBodyPreview() {
    NotesBody(
        modifier = Modifier.padding(bottom = 10.dp),
        notes = persistentListOf(
            NoteUi(
                name = "Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату ",
                date = "Сегодня в 12:51",
                progressAsString = "5:32",
                isPlaying = false,
                progress = 0.4f,
                duration = "",
                id = 1L,
            ),
            NoteUi(
                name = "Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату Поход к адвокату ",
                date = "Сегодня в 12:51",
                progressAsString = "5:32",
                isPlaying = true,
                progress = 0.4f,
                duration = "",
                id = 2L,
            ),
        ),
        recordButtonScale = 1.6f,
        recordProgress = "0:00",
        onEvent = {},
    )
}

@Composable
fun NotesBody(
    recordProgress: String,
    recordButtonScale: Float,
    notes: ImmutableList<NoteUi>,
    onEvent: (NotesScreenEvent) -> Unit,
    isRecording: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(modifier = Modifier.padding(start = 16.dp, bottom = 30.dp, end = 16.dp))
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.padding(vertical = 12.dp),
                scale = recordButtonScale,
                textProgress = recordProgress,
                isRecording = isRecording,
                onRecord = remember { { onEvent(RecorderEvent.ChangeStatus) } },
                onCancel = remember { { onEvent(RecorderEvent.Cancel) } },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = Color.White,
    ) {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = notes,
                key = { note -> note.id }
            ) { model ->
                Note(
                    model = model,
                    onPlay = remember { { onEvent(NoteEvent.Click(model.id)) } }
                )
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.notes_header),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        ),
    )
}

@Composable
private fun BottomBar(
    scale: Float,
    textProgress: String,
    isRecording: Boolean,
    onRecord: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val showAdditionalInformation by remember(isRecording) {
        derivedStateOf { isRecording }
    }
    val progress by remember(textProgress) {
        derivedStateOf { textProgress }
    }
    val scaleState by remember(scale){
        derivedStateOf{scale}
    }
    Box(modifier.fillMaxWidth()) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 32.dp),
            visible = showAdditionalInformation,
        ) {
            Text(
                text = progress,
                style = MaterialTheme.typography.body2,
            )
        }

        RecordButtonWithAnimation(
            modifier = Modifier.align(Alignment.Center),
            scale = scaleState,
            isRecording = showAdditionalInformation,
            onClick = onRecord,
        )

        AnimatedVisibility(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(CircleShape)
                .align(Alignment.CenterEnd),
            visible = showAdditionalInformation,
        ) {
            IconButton(
                modifier = Modifier
                    .background(VoiceNotesTheme.colors.cardBackground),
                enabled = isRecording,
                onClick = onCancel,
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = null,
                )
            }
        }
    }
}