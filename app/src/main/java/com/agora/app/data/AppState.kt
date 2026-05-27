package com.agora.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class CompletedSession(
    val studentName: String,
    val subject: String,
    val wasHelper: Boolean,
    val rating: Double,
    val date: String
)

object AppState {
    var newMeetings by mutableStateOf(listOf<Meeting>())
    var nextMeetingId by mutableStateOf(100)
    var cancelledMeetingIds by mutableStateOf(setOf<Int>())
    var completedMeetingIds by mutableStateOf(setOf<Int>())
    var requestedStudentIds by mutableStateOf(setOf<Int>())
    var creditBalance by mutableStateOf(3)
    var dynamicConversationHistory by mutableStateOf(listOf<CompletedSession>())
    var userOffers: List<String>? by mutableStateOf(null)
    var userNeeds: List<String>? by mutableStateOf(null)
}