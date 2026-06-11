package com.agora.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agora.app.data.AppState
import com.agora.app.data.FakeData
import com.agora.app.data.UserOffer
import com.agora.app.ui.theme.*

private val predefinedSkills = listOf(
    "Programming", "Mathematics", "Statistics", "Physics",
    "Economics", "Chemistry", "Biology", "Law",
    "Essay writing", "CAD", "Languages", "Research methods",
    "Calculus", "Algorithms"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OfferCreationScreen(
    onBack: () -> Unit,
    onOfferPosted: () -> Unit
) {
    var selectedOffers by remember { mutableStateOf(listOf<String>()) }
    var selectedNeeds by remember { mutableStateOf(listOf<String>()) }
    var customOfferInput by remember { mutableStateOf("") }
    var customNeedInput by remember { mutableStateOf("") }

    val previewOffer = UserOffer(
        id = -1,
        offers = if (selectedOffers.isEmpty()) listOf("Your skills") else selectedOffers,
        needs = selectedNeeds
    )

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
                        text = "New offer",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            SectionCard(title = "What can you offer?") {
                if (selectedOffers.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedOffers.forEach { skill ->
                            InputChip(
                                selected = true,
                                onClick = { selectedOffers = selectedOffers - skill },
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
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (customOfferInput.isNotBlank() &&
                            customOfferInput !in selectedOffers &&
                            customOfferInput !in selectedNeeds) {
                            selectedOffers = selectedOffers + customOfferInput.trim()
                            customOfferInput = ""
                        }
                    }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Add", tint = AgoraPrimary)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Or pick from common skills", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
                                selectedOffers = if (isSelected) selectedOffers - skill else selectedOffers + skill
                            },
                            enabled = skill !in selectedNeeds,
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

        item {
            SectionCard(title = "What do you need help with?") {
                if (selectedNeeds.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedNeeds.forEach { skill ->
                            InputChip(
                                selected = true,
                                onClick = { selectedNeeds = selectedNeeds - skill },
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
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (customNeedInput.isNotBlank() &&
                            customNeedInput !in selectedNeeds &&
                            customNeedInput !in selectedOffers) {
                            selectedNeeds = selectedNeeds + customNeedInput.trim()
                            customNeedInput = ""
                        }
                    }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Add", tint = AgoraAccentDark)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Or pick from common subjects", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
                                selectedNeeds = if (isSelected) selectedNeeds - skill else selectedNeeds + skill
                            },
                            enabled = skill !in selectedOffers,
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

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Preview",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                )
                UserOfferCard(offer = previewOffer)
            }
        }

        item {
            Button(
                onClick = {
                    val newId = AppState.nextOfferId
                    AppState.nextOfferId += 1
                    AppState.userOffersList = AppState.userOffersList + UserOffer(
                        id = newId,
                        offers = selectedOffers,
                        needs = selectedNeeds
                    )
                    onOfferPosted()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgoraPrimary),
                enabled = selectedOffers.isNotEmpty()
            ) {
                Text("Post offer", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}