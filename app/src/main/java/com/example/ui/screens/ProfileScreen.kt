package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.curriculum.CurriculumRepository
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterProgress
import com.example.data.model.EducationBoard
import com.example.data.model.HomeworkTask
import com.example.data.model.QuizAttempt
import com.example.data.model.StudentProfile

@Composable
fun ProfileScreen(
    profile: StudentProfile,
    accountEmail: String?,
    chapterProgress: List<ChapterProgress>,
    quizAttempts: List<QuizAttempt>,
    homework: List<HomeworkTask>,
    onUpdateProfile: (String, String, Int, AcademicStream, EducationBoard, Int) -> Unit,
    onSignOut: () -> Unit,
) {
    var showEditDialog by remember { mutableStateOf(false) }

    val completedChapters = chapterProgress.count { it.completed }
    val bookmarkedChapters = chapterProgress.count { it.bookmarked }
    val avgQuizScore = if (quizAttempts.isEmpty()) 0 else quizAttempts.map { it.accuracyPercent.toInt() }.average().toInt()
    val completedHw = homework.count { it.done }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen_list"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Student Identity Card
        item {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape,
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(66.dp)
                                .testTag("profile_academy_dp"),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_academy_dp),
                                contentDescription = "Aryan Aseer Academy DP",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                            )
                            Text(
                                text = profile.displayName,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                            Text(
                                text = "Class ${profile.classInt()} • ${profile.resolvedBoard().label}" +
                                    if (profile.classInt() >= 11) " • ${profile.resolvedStream().label}" else "",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primaryContainer,
                            )
                            if (profile.schoolName.isNotBlank()) {
                                Text(
                                    text = profile.schoolName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                                )
                            }
                            if (!accountEmail.isNullOrBlank()) {
                                Text(
                                    text = accountEmail,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.72f),
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { showEditDialog = true },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_profile_button"),
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Class, School & Study Goal")
                    }
                }
            }
        }

        // Academic Progress Analytics
        item {
            Text(
                text = "Academic Progress Snapshot",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ProfileMetricCard(
                        value = "$completedChapters",
                        label = "Chapters Mastered",
                        modifier = Modifier.weight(1f),
                    )
                    ProfileMetricCard(
                        value = if (quizAttempts.isEmpty()) "—" else "$avgQuizScore%",
                        label = "Avg Quiz Accuracy",
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ProfileMetricCard(
                        value = "$bookmarkedChapters",
                        label = "Saved Bookmarks",
                        modifier = Modifier.weight(1f),
                    )
                    ProfileMetricCard(
                        value = "$completedHw / ${homework.size}",
                        label = "Homework Done",
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        // Academic Details Card
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            Icons.Filled.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text("Study Configuration", style = MaterialTheme.typography.titleMedium)
                    }
                    ProfileInfoRow("Active Class", "Class ${profile.classInt()}")
                    ProfileInfoRow("Board", profile.resolvedBoard().label)
                    ProfileInfoRow("Stream", profile.resolvedStream().label)
                    ProfileInfoRow("Daily Self-Study Goal", "${profile.dailyGoalMinutes} mins / day")
                    ProfileInfoRow("Quizzes Attempted", "${quizAttempts.size}")
                }
            }
        }

        // Sign Out Action
        item {
            OutlinedButton(
                onClick = onSignOut,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("sign_out_button"),
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign Out")
            }
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            profile = profile,
            onDismiss = { showEditDialog = false },
            onSave = { name, school, cls, stream, board, goal ->
                onUpdateProfile(name, school, cls, stream, board, goal)
                showEditDialog = false
            },
        )
    }
}

@Composable
private fun ProfileMetricCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun EditProfileDialog(
    profile: StudentProfile,
    onDismiss: () -> Unit,
    onSave: (String, String, Int, AcademicStream, EducationBoard, Int) -> Unit,
) {
    var name by remember { mutableStateOf(profile.displayName) }
    var school by remember { mutableStateOf(profile.schoolName) }
    var selectedClass by remember { mutableIntStateOf(profile.classInt()) }
    var stream by remember { mutableStateOf(profile.resolvedStream()) }
    var board by remember { mutableStateOf(profile.resolvedBoard()) }
    var goalMinutes by remember { mutableIntStateOf(profile.dailyGoalMinutes.toInt().coerceIn(15, 360)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Student Profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(60) },
                    label = { Text("Student Name *") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = school,
                    onValueChange = { school = it.take(80) },
                    label = { Text("School Name") },
                    singleLine = true,
                )
                Text("Class (7 - 12)", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(CurriculumRepository.supportedClasses) { cls ->
                        FilterChip(
                            selected = selectedClass == cls,
                            onClick = {
                                selectedClass = cls
                                if (cls <= 10) stream = AcademicStream.GENERAL
                                else if (stream == AcademicStream.GENERAL) stream = AcademicStream.SCIENCE
                            },
                            label = { Text("$cls") },
                        )
                    }
                }
                if (selectedClass >= 11) {
                    Text("Stream", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(AcademicStream.SCIENCE, AcademicStream.COMMERCE, AcademicStream.ARTS).forEach { s ->
                            FilterChip(
                                selected = stream == s,
                                onClick = { stream = s },
                                label = { Text(s.label.substringBefore(" ")) },
                            )
                        }
                    }
                }
                Text("Board", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    EducationBoard.entries.forEach { b ->
                        FilterChip(
                            selected = board == b,
                            onClick = { board = b },
                            label = { Text(b.name) },
                        )
                    }
                }
                Text("Daily Study Goal", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(45, 60, 90, 120).forEach { mins ->
                        FilterChip(
                            selected = goalMinutes == mins,
                            onClick = { goalMinutes = mins },
                            label = { Text("${mins}m") },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(name, school, selectedClass, stream, board, goalMinutes) },
                enabled = name.trim().isNotEmpty(),
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}
