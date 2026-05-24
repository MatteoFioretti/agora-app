package com.agora.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.agora.app.data.AppState
import com.agora.app.data.Meeting
import com.agora.app.data.MeetingStatus
import com.agora.app.data.Student
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.VideoCall
import androidx.compose.material3.HorizontalDivider
import com.agora.app.ui.theme.*

private val dateOptions = listOf("Today", "Tomorrow", "This Friday", "Next Monday")
private val timeOptions = listOf("10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM")
private val placeOptions = listOf("Library", "Online", "Café")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookingScreen(
    student: Student,
    onBack: () -> Unit,
    onRequestSent: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedPlace by remember { mutableStateOf("") }
    var customPlace by remember { mutableStateOf("") }
    var requestSent by remember { mutableStateOf(false) }
    var showRecap by remember { mutableStateOf(false) }
    val canSend = selectedDate.isNotEmpty() &&
            selectedTime.isNotEmpty() &&
            (selectedPlace.isNotEmpty() || customPlace.isNotBlank())

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
                    text = "Request a conversation",
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
                    AvatarCircle(name = student.name, color = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            student.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${student.year} · ${student.faculty}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            "Offers: ${student.offers.joinToString(", ")}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        if (requestSent) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(AgoraAccent.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✦",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AgoraAccentDark
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Request sent!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AgoraAccentDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${student.name.split(" ").first()} will be notified and can accept or decline.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onRequestSent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgoraPrimary)
                ) {
                    Text("View in Meetings", style = MaterialTheme.typography.labelLarge)
                }
            }
        } else if (showRecap) {
            val finalPlace = if (customPlace.isNotBlank()) customPlace else selectedPlace
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Review your request",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AvatarCircle(name = student.name, color = AgoraPrimary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    student.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "${student.year} · ${student.faculty}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "About: ${student.offers.first()}",
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
                                text = "$selectedDate · $selectedTime",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.DarkGray
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (finalPlace == "Online")
                                    Icons.Outlined.VideoCall
                                else
                                    Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = finalPlace,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { showRecap = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Modify", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            val newId = AppState.nextMeetingId
                            AppState.nextMeetingId += 1
                            AppState.requestedStudentIds = AppState.requestedStudentIds + student.id
                            AppState.newMeetings = AppState.newMeetings + Meeting(
                                id = newId,
                                otherStudent = student,
                                subject = student.offers.first(),
                                date = selectedDate,
                                time = selectedTime,
                                place = finalPlace,
                                status = MeetingStatus.PENDING
                            )
                            requestSent = true
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AgoraPrimary)
                    ) {
                        Text("Confirm & send")
                    }
                }
            }
        } else {

            BookingSection(title = "When?") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dateOptions.forEach { option ->
                        FilterChip(
                            selected = option == selectedDate,
                            onClick = { selectedDate = option },
                            label = { Text(option, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor =  AgoraPrimary.copy(alpha = 0.12f),
                                selectedLabelColor =  AgoraPrimary
                            )
                        )
                    }
                }
            }

            BookingSection(title = "What time?") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    timeOptions.forEach { option ->
                        FilterChip(
                            selected = option == selectedTime,
                            onClick = { selectedTime = option },
                            label = { Text(option, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor =  AgoraPrimary.copy(alpha = 0.12f),
                                selectedLabelColor =  AgoraPrimary
                            )
                        )
                    }
                }
            }

            BookingSection(title = "Where?") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    placeOptions.forEach { option ->
                        FilterChip(
                            selected = option == selectedPlace,
                            onClick = {
                                selectedPlace = option
                                customPlace = ""
                            },
                            label = { Text(option, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor =  AgoraPrimary.copy(alpha = 0.12f),
                                selectedLabelColor =  AgoraPrimary
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = customPlace,
                    onValueChange = {
                        customPlace = it
                        if (it.isNotBlank()) selectedPlace = ""
                    },
                    placeholder = { Text("Or type a custom location...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            Button(
                onClick = { showRecap = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgoraPrimary),
                enabled = canSend
            ) {
                Text("Preview request", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BookingSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}