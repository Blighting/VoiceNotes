package com.example.voicenotes.domain.utils

import android.text.format.DateUtils

fun Long.convertMillisecondsToString(): String {
    val totalSeconds = this.convertMillisecondsToSeconds()
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
    return "$minutes:$secondsString"
}

private fun Long.convertMillisecondsToSeconds(): Long = this.div(1000)

fun Long.millisecondsToSimpleDate(): String {
    return DateUtils.getRelativeTimeSpanString(
        this,
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    ).toString()
}

fun Int.convertSecondsToString(): String {
    val minutes = this / 60
    val seconds = this % 60
    val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
    return "$minutes:$secondsString"
}