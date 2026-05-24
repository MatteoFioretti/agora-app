package com.agora.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agora.app.data.FakeData
import com.agora.app.data.AppState
import com.agora.app.data.CompletedSession
import com.agora.app.ui.theme.*



private val sessionHistory = listOf(
    CompletedSession("Marco R.", "Python debugging", false, 4.9, "Last Monday"),
    CompletedSession("Luca B.", "Programming basics", true, 5.0, "2 weeks ago"),
    CompletedSession("Sofia L.", "Linear Algebra", false, 4.8, "3 weeks ago"),
    CompletedSession("Pietro G.", "Algorithms", true, 4.7, "Last month"),
    CompletedSession("Asel K.", "Data structures", true, 5.0, "Last month")
)

@Composable
fun ProfileScreen() {
    val user = FakeData.currentUser
    val allHistory = AppState.dynamicConversationHistory + sessionHistory
    val helpedCount = allHistory.count { it.wasHelper }
    val receivedCount = allHistory.count { !it.wasHelper }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AgoraBackground),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background( AgoraPrimary)
                    .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.first().uppercaseChar().toString(),
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${user.year} · ${user.faculty}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your credits",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${AppState.creditBalance}",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgoraAccentDark
                    )
                    Text(
                        text = "credits available",
                        style = MaterialTheme.typography.bodySmall,
                        color = AgoraAccent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(value = "$helpedCount", label = "conversations\nhelped")
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(40.dp)
                                .background(Color.LightGray.copy(alpha = 0.5f))
                        )
                        StatItem(value = "$receivedCount", label = "conversations\nreceived")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "I offer",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        user.offers.forEach { skill ->
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color =  AgoraPrimary.copy(alpha = 0.1f),
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = skill,
                                    style = MaterialTheme.typography.labelSmall,
                                    color =  AgoraPrimary,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp, vertical = 4.dp
                                    )
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(60.dp)
                            .align(Alignment.CenterVertically)
                            .background(Color.LightGray.copy(alpha = 0.5f))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "I need",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        user.needs.forEach { need ->
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = AgoraAccent.copy(alpha = 0.1f),
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = need,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AgoraAccentDark,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp, vertical = 4.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Conversation history",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp
                )
            )
        }

        items(allHistory) { session ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarCircle(
                        name = session.studentName,
                        color = if (session.wasHelper)  AgoraPrimary else AgoraAccent
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = session.studentName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = session.subject,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = session.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.LightGray
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (session.wasHelper)
                                 AgoraPrimary.copy(alpha = 0.1f)
                            else
                                AgoraAccent.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = if (session.wasHelper) "helped" else "received",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (session.wasHelper)  AgoraPrimary else AgoraAccentDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = session.rating.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color =  AgoraPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}