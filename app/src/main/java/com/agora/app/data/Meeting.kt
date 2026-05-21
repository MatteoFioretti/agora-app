package com.agora.app.data

enum class MeetingStatus { PENDING, CONFIRMED, COMPLETED }

data class Meeting(
    val id: Int,
    val otherStudent: Student,
    val subject: String,
    val date: String,
    val time: String,
    val place: String,
    val status: MeetingStatus,
    val rating: Double? = null,
    val feedbackNote: String? = null
)