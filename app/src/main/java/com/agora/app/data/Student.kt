package com.agora.app.data

data class Student(
    val id: Int,
    val name: String,
    val year: String,
    val faculty: String,
    val offers: List<String>,
    val needs: List<String>,
    val rating: Double,
    val conversationCount: Int = 0
)