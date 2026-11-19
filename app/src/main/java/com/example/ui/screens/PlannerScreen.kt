package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.data.model.HomeworkPriority
import com.example.data.model.HomeworkTask
import com.example.data.model.StudyNote
import com.example.data.model.TimetableEntry

private val weekDays = listOf(
    1 to "Mon",
    2 to "Tue",
    3 to "Wed",
    4 to "Thu",
    5 to "Fri",
    6 to "Sat",
    7 to "Sun",
)

@Composable
fun PlannerScreen(
    activeClass: Int,
    homework: List<HomeworkTask>,
    notes: List<StudyNote>,
    timetable: List<TimetableEntry>,
    onAddHomework: (String, String, String, String, HomeworkPriority) -> Unit,
    onToggleHomeworkDone: (HomeworkTask) -> Unit,
    onDeleteHomework: (String) -> Unit,
    onSaveNote: (String?, String, String, String, Boolean) -> Unit,
    onToggleNotePin: (StudyNote) -> Unit,
    onDeleteNote: (String) -> Unit,
    onAddTimetableEntry: (Int, String, String, String) -> Unit,
    onDeleteTimetableEntry: (String) -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedDay by remember { mutableIntStateOf(1) }

    var showAddHomework by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<StudyNote?>(null) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var showTimetableDialog by remember { mutableStateOf(false) }

    val tabs = listOf("Homework", "Timetable", "My Notes")

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("planner_screen_list"),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 92.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Study Planner & Organizer",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = "Class $activeClass homework, daily timetable & personal revision notes",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            item {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    tabs.forEachIndexed { idx, label ->
                        SegmentedButton(
                            selected = selectedTab == idx,
                            onClick = { selectedTab = idx },
                            shape = SegmentedButtonDefaults.itemShape(idx, tabs.size),
                            modifier = Modifier.testTag("planner_tab_$idx"),
                        ) {
                            Text(label, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    if (homework.isEmpty()) {
                        item {
                            PlannerEmptyCard(
                                icon = Icons.AutoMirrored.Filled.Assignment,
                                title = "No homework tasks yet",
                                body = "Tap the + button to track assignments, NCERT exercises, and exam deadlines.",
                            )
                        }
                    } else {
                        items(homework, key = { it.id }) { task ->
                            val priority = task.resolvedPriority()
                            val chipColor = when (priority) {
                                HomeworkPriority.HIGH -> MaterialTheme.colorScheme.errorContainer
                                HomeworkPriority.MEDIUM -> MaterialTheme.colorScheme.secondaryContainer
                                HomeworkPriority.LOW -> MaterialTheme.colorScheme.tertiaryContainer
                            }
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.surface,
                                tonalElevation = 1.dp,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Checkbox(
                                        checked = task.done,
                                        onCheckedChange = { onToggleHomeworkDone(task) },
                                        modifier = Modifier.testTag("hw_checkbox_${task.id}"),
                                    )
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(top = 6.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Surface(
                                                color = chipColor,
                                                shape = RoundedCornerShape(8.dp),
                                            ) {
                                                Text(
                                                    text = priority.label,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                                )
                                            }
                                            Text(
                                                text = "${task.subjectName} • Due: ${task.dueDateLabel}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            textDecoration = if (task.done) TextDecoration.LineThrough else null,
                                        )
                                        if (task.details.isNotBlank()) {
                                            Text(
                                                text = task.details,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                    IconButton(
                                        onClick = { onDeleteHomework(task.id) },
                                        modifier = Modifier.testTag("hw_delete_${task.id}"),
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Delete homework",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(weekDays) { (dayNum, label) ->
                                FilterChip(
                                    selected = selectedDay == dayNum,
                                    onClick = { selectedDay = dayNum },
                                    label = { Text(label) },
                                    modifier = Modifier.testTag("day_chip_$dayNum"),
                                )
                            }
                        }
                    }
                    val daySlots = timetable.filter { it.dayOfWeek.toInt() == selectedDay }
                    if (daySlots.isEmpty()) {
                        item {
                            PlannerEmptyCard(
                                icon = Icons.Filled.Schedule,
                                title = "No periods scheduled for ${weekDays.first { it.first == selectedDay }.second}",
                                body = "Tap + to add school classes, coaching timings, or self-study revision blocks.",
                            )
                        }
                    } else {
                        items(daySlots, key = { it.id }) { slot ->
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.surface,
                                tonalElevation = 1.dp,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(12.dp),
                                    ) {
                                        Text(
                                            text = slot.timeSlot,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = slot.subjectName,
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                        if (slot.topic.isNotBlank()) {
                                            Text(
                                                text = slot.topic,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                    IconButton(onClick = { onDeleteTimetableEntry(slot.id) }) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Remove period",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    if (notes.isEmpty()) {
                        item {
                            PlannerEmptyCard(
                                icon = Icons.AutoMirrored.Filled.EventNote,
                                title = "No personal revision notes yet",
                                body = "Tap + to jot down important derivations, dates, mnemonics, or exam tips.",
                            )
                        }
                    } else {
                        items(notes, key = { it.id }) { note ->
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.surface,
                                tonalElevation = 1.dp,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Class ${note.classLevel} • ${note.subjectName}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary,
                                            )
                                            Text(
                                                text = note.title,
                                                style = MaterialTheme.typography.titleMedium,
                                            )
                                        }
                                        Row {
                                            IconButton(onClick = { onToggleNotePin(note) }) {
                                                Icon(
                                                    imageVector = if (note.pinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                                    contentDescription = "Toggle pin",
                                                    tint = if (note.pinned) MaterialTheme.colorScheme.secondary
                                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                                )
                                            }
                                            IconButton(onClick = {
                                                editingNote = note
                                                showNoteDialog = true
                                            }) {
                                                Icon(Icons.Filled.Edit, contentDescription = "Edit note")
                                            }
                                            IconButton(onClick = { onDeleteNote(note.id) }) {
                                                Icon(Icons.Filled.Delete, contentDescription = "Delete note")
                                            }
                                        }
                                    }
                                    Text(
                                        text = note.content,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                when (selectedTab) {
                    0 -> showAddHomework = true
                    1 -> showTimetableDialog = true
                    else -> {
                        editingNote = null
                        showNoteDialog = true
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("planner_add_fab"),
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add planner item")
        }
    }

    if (showAddHomework) {
        AddHomeworkDialog(
            onDismiss = { showAddHomework = false },
            onSave = { subject, title, details, due, priority ->
                onAddHomework(subject, title, details, due, priority)
                showAddHomework = false
            },
        )
    }

    if (showTimetableDialog) {
        AddTimetableDialog(
            dayLabel = weekDays.first { it.first == selectedDay }.second,
            onDismiss = { showTimetableDialog = false },
            onSave = { subject, time, topic ->
                onAddTimetableEntry(selectedDay, subject, time, topic)
                showTimetableDialog = false
            },
        )
    }

    if (showNoteDialog) {
        NoteEditorDialog(
            initial = editingNote,
            onDismiss = { showNoteDialog = false },
            onSave = { subject, title, content, pinned ->
                onSaveNote(editingNote?.id, subject, title, content, pinned)
                showNoteDialog = false
            },
        )
    }
}

@Composable
private fun PlannerEmptyCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    body: String,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp),
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = body,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private val quickSubjectPresets = listOf(
    "Mathematics",
    "Urdu (اردو)",
    "Science",
    "English",
    "Social Science",
)

@Composable
private fun SubjectQuickPickRow(
    selectedSubject: String,
    onSelect: (String) -> Unit,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(quickSubjectPresets) { preset ->
            FilterChip(
                selected = selectedSubject.equals(preset, ignoreCase = true),
                onClick = { onSelect(preset) },
                label = { Text(preset, style = MaterialTheme.typography.labelSmall) },
            )
        }
    }
}

@Composable
private fun AddHomeworkDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, HomeworkPriority) -> Unit,
) {
    var subject by remember { mutableStateOf("Mathematics") }
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var due by remember { mutableStateOf("Tomorrow") }
    var priority by remember { mutableStateOf(HomeworkPriority.MEDIUM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Homework Task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SubjectQuickPickRow(
                    selectedSubject = subject,
                    onSelect = { subject = it },
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it.take(60) },
                    label = { Text("Subject") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it.take(120) },
                    label = { Text("Assignment Title *") },
                    singleLine = true,
                    modifier = Modifier.testTag("dialog_hw_title_input"),
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it.take(500) },
                    label = { Text("Details / Page Numbers") },
                    maxLines = 3,
                )
                OutlinedTextField(
                    value = due,
                    onValueChange = { due = it.take(40) },
                    label = { Text("Due Date") },
                    singleLine = true,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    HomeworkPriority.entries.forEach { p ->
                        FilterChip(
                            selected = priority == p,
                            onClick = { priority = p },
                            label = { Text(p.label) },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(subject, title, details, due, priority) },
                enabled = title.trim().isNotEmpty(),
                modifier = Modifier.testTag("dialog_hw_save_button"),
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}

@Composable
private fun AddTimetableDialog(
    dayLabel: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit,
) {
    var subject by remember { mutableStateOf("Mathematics") }
    var timeSlot by remember { mutableStateOf("08:00 AM - 08:45 AM") }
    var topic by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add $dayLabel Class Slot") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SubjectQuickPickRow(
                    selectedSubject = subject,
                    onSelect = { subject = it },
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it.take(60) },
                    label = { Text("Subject *") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = timeSlot,
                    onValueChange = { timeSlot = it.take(40) },
                    label = { Text("Time Slot *") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it.take(120) },
                    label = { Text("Teacher / Revision Focus") },
                    singleLine = true,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(subject, timeSlot, topic) },
                enabled = subject.trim().isNotEmpty() && timeSlot.trim().isNotEmpty(),
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}

@Composable
private fun NoteEditorDialog(
    initial: StudyNote?,
    onDismiss: () -> Unit,
    onSave: (String, String, String, Boolean) -> Unit,
) {
    var subject by remember { mutableStateOf(initial?.subjectName ?: "Mathematics") }
    var title by remember { mutableStateOf(initial?.title ?: "") }
    var content by remember { mutableStateOf(initial?.content ?: "") }
    val pinned = initial?.pinned ?: false

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "New Revision Note" else "Edit Revision Note") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SubjectQuickPickRow(
                    selectedSubject = subject,
                    onSelect = { subject = it },
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it.take(60) },
                    label = { Text("Subject") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it.take(120) },
                    label = { Text("Note Heading *") },
                    singleLine = true,
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it.take(2000) },
                    label = { Text("Notes / Formulas *") },
                    minLines = 4,
                    maxLines = 7,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(subject, title, content, pinned) },
                enabled = title.trim().isNotEmpty() && content.trim().isNotEmpty(),
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}
