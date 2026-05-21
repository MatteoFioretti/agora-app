package com.agora.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agora.app.data.FakeData
import com.agora.app.data.Meeting
import com.agora.app.data.MeetingStatus

private val AgoraBlue = Color(0xFF1A73E8)
private val AgoraTeal = Color(0xFF00BFA5)
private val AgoraTealDark = Color(0xFF00897B)
private val AgoraBackground = Color(0xFFF8F9FA)
private val AgoraAmber = Color(0xFFFFA000)

@Composable
fun MeetingsScreen(onConfirmSession: (Int) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Upcoming", "Past")

    val upcomingMeetings = FakeData.getAllMeetings().filter {
        it.status == MeetingStatus.PENDING || it.status == MeetingStatus.CONFIRMED
    }
    val pastMeetings = FakeData.getAllMeetings().filter {
        it.status == MeetingStatus.COMPLETED
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgoraBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AgoraBlue)
                .padding(start = 20.dp, end = 20.dp, top = 24.dp)
        ) {
            Text(
                text = "My Meetings",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your upcoming and past conversations",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = AgoraBlue,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.6f),
                                fontWeight = if (selectedTab == index)
                                    FontWeight.Bold
                                else
                                    FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        val currentList = if (selectedTab == 0) upcomingMeetings else pastMeetings

        if (currentList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (selectedTab == 0) "No upcoming meetings" else "No past meetings",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (selectedTab == 0)
                            "Request a conversation from Explore"
                        else
                            "Completed meetings will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(currentList) { meeting ->
                    MeetingCard(meeting = meeting, onConfirmSession = onConfirmSession)
                }
            }
        }
    }
}

@Composable
fun MeetingCard(meeting: Meeting, onConfirmSession: (Int) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarCircle(name = meeting.otherStudent.name, color = AgoraBlue)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        meeting.otherStudent.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "${meeting.otherStudent.year} · ${meeting.otherStudent.faculty}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                StatusBadge(status = meeting.status)
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = meeting.subject,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${meeting.date} · ${meeting.time}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (meeting.place == "Online")
                        Icons.Outlined.VideoCall
                    else
                        Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = meeting.place,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (meeting.status) {
                MeetingStatus.PENDING -> {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Cancel request",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                MeetingStatus.CONFIRMED -> {
                    Button(
                        onClick = { onConfirmSession(meeting.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AgoraTealDark)
                    ) {
                        Text(
                            "Confirm completed session",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                MeetingStatus.COMPLETED -> {
                    meeting.rating?.let { rating ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RatingRow(rating = rating, count = 0)
                            Spacer(modifier = Modifier.width(8.dp))
                            meeting.feedbackNote?.let { note ->
                                Text(
                                    text = "\"$note\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: MeetingStatus) {
    val text = when (status) {
        MeetingStatus.PENDING -> "Pending"
        MeetingStatus.CONFIRMED -> "Confirmed"
        MeetingStatus.COMPLETED -> "Completed"
    }
    val color = when (status) {
        MeetingStatus.PENDING -> AgoraAmber
        MeetingStatus.CONFIRMED -> AgoraBlue
        MeetingStatus.COMPLETED -> AgoraTealDark
    }
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}