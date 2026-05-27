package com.agora.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agora.app.data.FakeData
import com.agora.app.data.Student
import com.agora.app.data.AppState
import com.agora.app.ui.theme.*

private val predefinedSkills = listOf(
    "Programming", "Mathematics", "Statistics", "Physics",
    "Economics", "Chemistry", "Biology", "Law",
    "Essay writing", "CAD", "Languages", "Research methods",
    "Calculus", "Algorithms"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OfferHelpScreen() {
    var isAvailable by remember {
        mutableStateOf(AppState.userOffers?.isEmpty() != true)
    }
    var showUnavailableDialog by remember { mutableStateOf(false) }
    var selectedOffers by remember { mutableStateOf(AppState.userOffers ?: FakeData.currentUser.offers) }
    var selectedNeeds by remember { mutableStateOf(AppState.userNeeds ?: FakeData.currentUser.needs) }
    var customOfferInput by remember { mutableStateOf("") }
    var customNeedInput by remember { mutableStateOf("") }
    var offerPosted by remember { mutableStateOf(false) }

    val previewStudent = Student(
        id = -1,
        name = FakeData.currentUser.name,
        year = FakeData.currentUser.year,
        faculty = FakeData.currentUser.faculty,
        offers = if (!isAvailable) listOf("Unavailable")
        else if (selectedOffers.isEmpty()) listOf("Your skills")
        else selectedOffers,
        needs = if (!isAvailable) emptyList() else selectedNeeds,
        rating = 0.0,
        conversationCount = 0
    )

    if (showUnavailableDialog) {
        AlertDialog(
            onDismissRequest = { showUnavailableDialog = false },
            title = { Text("Become unavailable?") },
            text = {
                Text("If you proceed, your offer will be removed and other students will no longer be able to find you.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isAvailable = false
                        AppState.userOffers = emptyList()
                        AppState.userNeeds = emptyList()
                        showUnavailableDialog = false
                    }
                ) {
                    Text("OK", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnavailableDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

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
                    .background(AgoraPrimary)
                    .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 28.dp)
            ) {
                Text(
                    text = "Offer help",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Share what you know — earn a credit for every conversation",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = AgoraText)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Available to help",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isAvailable) "You are currently offering help"
                            else "You are currently unavailable",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isAvailable) AgoraAccentDark else Color.Gray
                        )
                    }
                    Switch(
                        checked = isAvailable,
                        onCheckedChange = { newValue ->
                            if (!newValue) {
                                showUnavailableDialog = true
                            } else {
                                isAvailable = true
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AgoraPrimary
                        )
                    )
                }
            }
        }

        if (offerPosted) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = AgoraAccent.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("✦", color = AgoraAccentDark)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Your offer is live! Students can now find you.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AgoraAccentDark,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item {
            Box(modifier = Modifier.graphicsLayer { alpha = if (isAvailable) 1f else 0.4f }) {
                SectionCard(title = "What can you offer?") {
                    if (selectedOffers.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedOffers.forEach { skill ->
                                InputChip(
                                    selected = true,
                                    onClick = {
                                        if (isAvailable) selectedOffers = selectedOffers - skill
                                    },
                                    label = { Text(skill) },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Outlined.Close,
                                            contentDescription = "Remove",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    colors = InputChipDefaults.inputChipColors(
                                        selectedContainerColor = AgoraPrimary.copy(alpha = 0.12f),
                                        selectedLabelColor = AgoraPrimary
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = customOfferInput,
                            onValueChange = { customOfferInput = it },
                            placeholder = { Text("Add a custom skill...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            enabled = isAvailable
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (customOfferInput.isNotBlank() &&
                                    customOfferInput !in selectedOffers &&
                                    customOfferInput !in selectedNeeds) {
                                    selectedOffers = selectedOffers + customOfferInput.trim()
                                    customOfferInput = ""
                                }
                            },
                            enabled = isAvailable
                        ) {
                            Icon(Icons.Outlined.Add, contentDescription = "Add", tint = AgoraPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Or pick from common skills",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        predefinedSkills.forEach { skill ->
                            val isSelected = skill in selectedOffers
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedOffers = if (isSelected) {
                                        selectedOffers - skill
                                    } else {
                                        selectedOffers + skill
                                    }
                                },
                                enabled = isAvailable && skill !in selectedNeeds,
                                label = { Text(skill, style = MaterialTheme.typography.labelSmall) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AgoraPrimary.copy(alpha = 0.12f),
                                    selectedLabelColor = AgoraPrimary
                                )
                            )
                        }
                    }
                }
            }
        }

        item {
            Box(modifier = Modifier.graphicsLayer { alpha = if (isAvailable) 1f else 0.4f }) {
                SectionCard(title = "What do you need help with?") {
                    if (selectedNeeds.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedNeeds.forEach { skill ->
                                InputChip(
                                    selected = true,
                                    onClick = {
                                        if (isAvailable) selectedNeeds = selectedNeeds - skill
                                    },
                                    label = { Text(skill) },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Outlined.Close,
                                            contentDescription = "Remove",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    colors = InputChipDefaults.inputChipColors(
                                        selectedContainerColor = AgoraAccent.copy(alpha = 0.12f),
                                        selectedLabelColor = AgoraAccentDark
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = customNeedInput,
                            onValueChange = { customNeedInput = it },
                            placeholder = { Text("Add a custom subject...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            enabled = isAvailable
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (customNeedInput.isNotBlank() &&
                                    customNeedInput !in selectedNeeds &&
                                    customNeedInput !in selectedOffers) {
                                    selectedNeeds = selectedNeeds + customNeedInput.trim()
                                    customNeedInput = ""
                                }
                            },
                            enabled = isAvailable
                        ) {
                            Icon(Icons.Outlined.Add, contentDescription = "Add", tint = AgoraAccentDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Or pick from common subjects",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        predefinedSkills.forEach { skill ->
                            val isSelected = skill in selectedNeeds
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedNeeds = if (isSelected) {
                                        selectedNeeds - skill
                                    } else {
                                        selectedNeeds + skill
                                    }
                                },
                                enabled = isAvailable && skill !in selectedOffers,
                                label = { Text(skill, style = MaterialTheme.typography.labelSmall) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AgoraAccent.copy(alpha = 0.12f),
                                    selectedLabelColor = AgoraAccentDark
                                )
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Your card preview",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                )
                StudentCard(student = previewStudent)
            }
        }

        item {
            Button(
                onClick = {
                    AppState.userOffers = selectedOffers
                    AppState.userNeeds = selectedNeeds
                    offerPosted = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgoraPrimary),
                enabled = isAvailable && selectedOffers.isNotEmpty()
            ) {
                Text("Post offer", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
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