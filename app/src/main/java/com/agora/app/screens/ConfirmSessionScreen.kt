package com.agora.app.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agora.app.data.Meeting
import com.agora.app.data.AppState
import com.agora.app.data.CompletedSession
import com.agora.app.ui.theme.*

@Composable
fun ConfirmSessionScreen(
    meeting: Meeting,
    onBack: () -> Unit
) {
    var step1Confirmed by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf(0) }
    var feedbackNote by remember { mutableStateOf("") }
    var creditTransferred by remember { mutableStateOf(false) }

    val animatedCredits by animateIntAsState(
        targetValue = AppState.creditBalance,
        animationSpec = tween(durationMillis = 800),
        label = "credits"
    )

    val creditScale by animateFloatAsState(
        targetValue = if (creditTransferred) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgoraBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background( AgoraPrimary)
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Confirm session",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.15f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarCircle(name = meeting.otherStudent.name, color = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            meeting.otherStudent.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            meeting.subject,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            "${meeting.date} · ${meeting.place}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        StepCard(
            stepNumber = 1,
            title = "Did this session take place?",
            isCompleted = step1Confirmed,
            isEnabled = true
        ) {
            if (step1Confirmed) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(AgoraAccent.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                            tint = AgoraAccentDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Session confirmed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AgoraAccentDark,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Button(
                    onClick = { step1Confirmed = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor =  AgoraPrimary)
                ) {
                    Text("Yes, it happened", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        StepCard(
            stepNumber = 2,
            title = "How was the conversation?",
            isCompleted = selectedRating > 0,
            isEnabled = step1Confirmed
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { star ->
                    Icon(
                        imageVector = if (star <= selectedRating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (star <= selectedRating) Color(0xFFFFC107) else Color.LightGray,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(enabled = step1Confirmed) { selectedRating = star }
                    )
                }
            }
            if (selectedRating > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = feedbackNote,
                    onValueChange = { if (it.length <= 80) feedbackNote = it },
                    placeholder = { Text("Leave a note (optional)...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    supportingText = {
                        Text(
                            "${feedbackNote.length}/80",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                )
            }
        }

        StepCard(
            stepNumber = 3,
            title = "Transfer credit to ${meeting.otherStudent.name.split(" ").first()}",
            isCompleted = creditTransferred,
            isEnabled = selectedRating > 0
        ) {
            if (!creditTransferred) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "1 credit will be sent to ${meeting.otherStudent.name.split(" ").first()}.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color =  AgoraPrimary.copy(alpha = 0.1f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Current balance",
                                style = MaterialTheme.typography.labelSmall,
                                color =  AgoraPrimary.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${AppState.creditBalance} credits",
                                style = MaterialTheme.typography.labelMedium,
                                color = AgoraPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        AppState.creditBalance -= 1
                        AppState.completedMeetingIds = AppState.completedMeetingIds + meeting.id
                        AppState.dynamicConversationHistory = AppState.dynamicConversationHistory + CompletedSession(
                            studentName = meeting.otherStudent.name,
                            subject = meeting.subject,
                            wasHelper = false,
                            rating = selectedRating.toDouble(),
                            date = "Today"
                        )
                        creditTransferred = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgoraAccentDark)
                ) {
                    Text("Send 1 credit", style = MaterialTheme.typography.labelLarge)
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer {
                                scaleX = creditScale
                                scaleY = creditScale
                            }
                            .background(AgoraAccent.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✦", fontSize = 32.sp, color = AgoraAccentDark)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Credit sent!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AgoraAccentDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${meeting.otherStudent.name.split(" ").first()} has been credited for helping you",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WalletBox(
                            label = "Your wallet",
                            credits = animatedCredits,
                            color =  AgoraPrimary
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = AgoraAccentDark.copy(alpha = 0.1f)
                            ) {
                                Text(
                                    text = "+1",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AgoraAccentDark,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "to ${meeting.otherStudent.name.split(" ").first()}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun StepCard(
    stepNumber: Int,
    title: String,
    isCompleted: Boolean,
    isEnabled: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer { alpha = if (isEnabled) 1f else 0.4f }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(
                            if (isCompleted) AgoraAccent.copy(alpha = 0.15f)
                            else  AgoraPrimary.copy(alpha = 0.1f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                            tint = AgoraAccentDark,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = "$stepNumber",
                            style = MaterialTheme.typography.labelSmall,
                            color =  AgoraPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun WalletBox(label: String, credits: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = color.copy(alpha = 0.1f)
        ) {
            Text(
                text = "$credits credits",
                style = MaterialTheme.typography.titleMedium,
                color = color,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}