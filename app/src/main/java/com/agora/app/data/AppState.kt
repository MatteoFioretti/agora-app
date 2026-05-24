package com.agora.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    var newMeetingBooked by mutableStateOf(false)
    var newMeeting: Meeting? = null
    var cancelledMeetingIds by mutableStateOf(setOf<Int>())
    var requestedStudentIds by mutableStateOf(setOf<Int>())
}