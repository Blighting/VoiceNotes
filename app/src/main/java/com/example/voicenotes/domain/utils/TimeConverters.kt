package com.example.voicenotes.domain.utils

import android.text.format.DateUtils

private const val SECONDS_IN_MINUTE = 60

fun Long.convertMillisecondsToString(): String {
    val totalSeconds = this.convertMillisecondsToSeconds().toInt()
    return totalSeconds.convertSecondsToString()
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
    val minutes = this / SECONDS_IN_MINUTE
    val seconds = this % SECONDS_IN_MINUTE
    val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
    return "$minutes:$secondsString"
}