package com.example.voicenotes.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.voicenotes.R
import com.example.voicenotes.core.theme.VoiceNotesTheme

@Preview
@Composable
private fun CreationDialogPreview() {
    SaveDialog(
        value = "",
        visible = true,
        onSave = { /*TODO*/ },
        onCancel = { /*TODO*/ },
        onValueChange = {},
        correct = true
    )
}

@Composable
fun SaveDialog(
    value: String,
    visible: Boolean,
    correct: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    actionButtonEnabled: Boolean = false,
) {
    if (visible) {
        Dialog(onDismissRequest = onCancel) {
            Column(
                modifier = modifier.background(Color.White)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.dialog_note_save_title),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Black,
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    value = value,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    isError = correct,
                    keyboardActions = KeyboardActions(onDone = {
                        if (actionButtonEnabled) {
                            onSave()
                        }
                    }),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = VoiceNotesTheme.colors.text,
                        errorBorderColor = VoiceNotesTheme.colors.error,
                        unfocusedBorderColor = VoiceNotesTheme.colors.cardBackground,
                        focusedBorderColor = VoiceNotesTheme.colors.primary,
                    ),
                    onValueChange = onValueChange,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end = 16.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    ActionButton(
                        modifier = modifier.weight(1f),
                        text = stringResource(R.string.dialog_note_save_action_cancel),
                        textColor = VoiceNotesTheme.colors.error,
                        onAction = onCancel,
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    ActionButton(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f),
                        text = stringResource(R.string.dialog_note_save_action_save),
                        enabled = actionButtonEnabled,
                        textColor = VoiceNotesTheme.colors.primary,
                        onAction = onSave,
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    onAction: () -> Unit,
    text: String,
    textColor: Color,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onAction,
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else VoiceNotesTheme.colors.cardBackground,
            style = MaterialTheme.typography.button
        )
    }
}
