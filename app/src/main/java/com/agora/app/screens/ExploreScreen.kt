package com.agora.app.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.agora.app.data.FakeData
import com.agora.app.data.Student
import com.agora.app.ui.theme.*

@Composable
fun ExploreScreen(onRequestConversation: (Int) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }

    val perfectMatches = FakeData.getPerfectMatches()
    val regularStudents = FakeData.getRegularStudents()
    val isSearching = searchQuery.isNotEmpty()
    val searchResults = if (isSearching) {
        FakeData.allStudents.filter { student ->
            student.offers.any { it.contains(searchQuery, ignoreCase = true) }
        }
    } else emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AgoraBackground),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AgoraPrimary)
                    .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 28.dp)
            ) {
                Text(
                    text = "Hello, Matteo 👋",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "Who can you learn from today?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Search by skill...", color = Color.White.copy(alpha = 0.6f))
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White.copy(alpha = 0.6f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White
                    ),
                    singleLine = true
                )
            }
        }

        if (isSearching) {
            item {
                Text(
                    text = "${searchResults.size} results for \"$searchQuery\"",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }
            items(searchResults) { student ->
                StudentCard(
                    student = student,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    onRequestConversation = onRequestConversation
                )
            }
        } else {
            item {
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    Row(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✦  Perfect matches for you",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AgoraAccentDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AgoraAccent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${perfectMatches.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = AgoraAccentDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (perfectMatches.isEmpty()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = AgoraAccent.copy(alpha = 0.08f)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No perfect matches yet",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AgoraAccentDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Update your offer to find students who match both ways",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(perfectMatches, key = { it.id }) { student ->
                                PerfectMatchCard(
                                    student = student,
                                    onRequestConversation = onRequestConversation
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Other matches",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp, top = 28.dp, bottom = 8.dp
                    ),
                    color = AgoraText
                )
            }

            items(regularStudents, key = { it.id }) { student ->
                StudentCard(
                    student = student,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    onRequestConversation = onRequestConversation
                )
            }
        }
    }
}

@Composable
fun PerfectMatchCard(student: Student, onRequestConversation: (Int) -> Unit = {}) {
    Card(
        modifier = Modifier.width(220.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText),
        border = BorderStroke(1.5.dp, AgoraAccent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = AgoraAccent.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "✦  Perfect match",
                    style = MaterialTheme.typography.labelSmall,
                    color = AgoraAccentDark,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarCircle(name = student.name, color = AgoraAccent)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        student.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AgoraText
                    )
                    Text(
                        "${student.year} · ${student.faculty}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Offers", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            SkillChips(skills = student.offers, color = AgoraAccent)
            Spacer(modifier = Modifier.height(10.dp))
            RatingRow(rating = student.rating, count = student.conversationCount)
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { onRequestConversation(student.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AgoraAccentDark
                )
            ) {
                Text("Request", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun StudentCard(student: Student, modifier: Modifier = Modifier, onRequestConversation: (Int) -> Unit = {}) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarCircle(name = student.name, color = AgoraPrimary)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        student.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AgoraText
                    )
                    Text(
                        "${student.year} · ${student.faculty}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                RatingRow(rating = student.rating, count = student.conversationCount)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Offers", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    SkillChips(skills = student.offers, color = AgoraPrimary)
                }
                if (student.needs.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Needs", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        SkillChips(skills = student.needs, color = AgoraAccent)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { onRequestConversation(student.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, AgoraPrimary)
            ) {
                Text(
                    "Request conversation",
                    style = MaterialTheme.typography.labelMedium,
                    color = AgoraPrimary
                )
            }
        }
    }
}

@Composable
fun AvatarCircle(name: String, color: Color) {
    val initials = name.split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString("")
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(color.copy(alpha = 0.15f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillChips(skills: List<String>, color: Color) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        skills.forEach { skill ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = color.copy(alpha = 0.1f)
            ) {
                Text(
                    text = skill,
                    style = MaterialTheme.typography.labelSmall,
                    color = color,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RatingRow(rating: Double, count: Int, small: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.Star,
            contentDescription = null,
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(if (small) 13.dp else 20.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = rating.toString(),
            style = if (small) MaterialTheme.typography.labelSmall else MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = AgoraText
        )
        Text(
            text = " (${count})",
            style = if (small) MaterialTheme.typography.labelSmall else MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}