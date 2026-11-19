package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.curriculum.CurriculumRepository
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterData
import com.example.data.model.ChapterProgress
import com.example.data.model.StudentProfile
import com.example.data.model.SubjectCategory
import com.example.data.model.SubjectData
import com.example.ui.components.GradeSelectionGrid
import com.example.ui.components.PrimaryMathUrduButtons
import com.example.ui.theme.HeroOverlayEnd
import com.example.ui.theme.HeroOverlayStart
import com.example.ui.theme.HeroSubtitleTint
import com.example.ui.theme.HeroWarmHighlight

@Composable
fun LearnHomeScreen(
    profile: StudentProfile,
    progressList: List<ChapterProgress>,
    pendingHomeworkCount: Int,
    onSelectClass: (Int) -> Unit,
    onSelectStream: (AcademicStream) -> Unit,
    onOpenSubject: (SubjectData) -> Unit,
    onOpenChapter: (ChapterData) -> Unit,
    onOpenFormulaVault: () -> Unit,
    onOpenQuizzesTab: () -> Unit,
) {
    // Sync with Firestore profile while providing instantaneous feedback when tapping the grade grid
    var activeClass by rememberSaveable(profile.selectedClass) {
        mutableIntStateOf(profile.classInt())
    }
    var activeStream by remember(profile.stream, activeClass) {
        mutableStateOf(
            if (activeClass <= 10) AcademicStream.GENERAL
            else profile.resolvedStream().takeIf { it != AcademicStream.GENERAL } ?: AcademicStream.SCIENCE
        )
    }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val subjects = remember(activeClass, activeStream) {
        CurriculumRepository.getSubjectsFor(activeClass, activeStream)
    }
    val mathSubject = remember(subjects) {
        subjects.firstOrNull { it.category == SubjectCategory.MATH }
    }
    val urduSubject = remember(subjects) {
        subjects.firstOrNull { it.category == SubjectCategory.URDU }
    }
    val additionalSubjects = remember(subjects) {
        subjects.filter { it.category != SubjectCategory.MATH && it.category != SubjectCategory.URDU }
    }

    val allClassChapters = remember(subjects) { subjects.flatMap { it.chapters } }
    val progressByChapter = remember(progressList) { progressList.associateBy { it.chapterId } }

    val completedInClass = remember(allClassChapters, progressByChapter) {
        allClassChapters.count { progressByChapter[it.id]?.completed == true }
    }
    val mathCompleted = remember(mathSubject, progressByChapter) {
        mathSubject?.chapters?.count { progressByChapter[it.id]?.completed == true } ?: 0
    }
    val urduCompleted = remember(urduSubject, progressByChapter) {
        urduSubject?.chapters?.count { progressByChapter[it.id]?.completed == true } ?: 0
    }
    val bookmarkedChapters = remember(allClassChapters, progressByChapter) {
        allClassChapters.filter { progressByChapter[it.id]?.bookmarked == true }
    }
    val searchResults = remember(activeClass, activeStream, searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else CurriculumRepository.searchChapters(activeClass, activeStream, searchQuery)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("learn_home_list"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // 1. Compact Editorial Academy Brand Hero Banner
        item {
            AcademyHeroBanner(
                profile = profile,
                activeClass = activeClass,
                activeStream = activeStream,
                completedChapters = completedInClass,
                totalChapters = allClassChapters.size,
            )
        }

        // 2. Grade 7-12 Interactive Selection Grid
        item {
            GradeSelectionGrid(
                grades = CurriculumRepository.supportedClasses,
                selectedGrade = activeClass,
                onSelectGrade = { cls ->
                    activeClass = cls
                    activeStream = if (cls <= 10) {
                        AcademicStream.GENERAL
                    } else if (activeStream == AcademicStream.GENERAL) {
                        AcademicStream.SCIENCE
                    } else {
                        activeStream
                    }
                    onSelectClass(cls)
                },
            )
        }

        // 3. Two Primary Subject Buttons: Mathematics & Urdu (اردو)
        item {
            PrimaryMathUrduButtons(
                selectedGrade = activeClass,
                mathSubject = mathSubject,
                mathCompletedChapters = mathCompleted,
                urduSubject = urduSubject,
                urduCompletedChapters = urduCompleted,
                onOpenSubject = onOpenSubject,
            )
        }

        // 4. Quick Search Across Selected Grade
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search Class $activeClass Math, Urdu, formulas...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search chapters")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("curriculum_search_input"),
            )
        }

        if (searchQuery.isNotBlank()) {
            item {
                Text(
                    text = "Search Results (${searchResults.size})",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            if (searchResults.isEmpty()) {
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "No Class $activeClass chapters matched \"$searchQuery\". Try switching grade above or searching Math, Urdu, Qawaid, Trigonometry, or Algebra.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            } else {
                items(searchResults, key = { it.id }) { chapter ->
                    CompactChapterCard(
                        chapter = chapter,
                        completed = progressByChapter[chapter.id]?.completed == true,
                        onClick = { onOpenChapter(chapter) },
                    )
                }
            }
        } else {
            // 5. Quick Revision Tools
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    QuickToolTile(
                        title = "Formula & Qawaid",
                        subtitle = "Class $activeClass Math & Urdu",
                        icon = Icons.Filled.Functions,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onClick = onOpenFormulaVault,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("open_formula_vault_card"),
                    )
                    QuickToolTile(
                        title = "Practice Quiz",
                        subtitle = if (pendingHomeworkCount > 0) "$pendingHomeworkCount homework due" else "Test mastery now",
                        icon = Icons.Filled.Quiz,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        onClick = onOpenQuizzesTab,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("open_quick_quiz_card"),
                    )
                }
            }

            // 6. Stream Selector (Classes 11 & 12) + Additional Subjects
            if (additionalSubjects.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "More Class $activeClass Subjects",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                text = "${additionalSubjects.size} more",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        if (activeClass >= 11) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                listOf(
                                    AcademicStream.SCIENCE,
                                    AcademicStream.COMMERCE,
                                    AcademicStream.ARTS,
                                ).forEach { stream ->
                                    val isSelected = activeStream == stream
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            activeStream = stream
                                            onSelectStream(stream)
                                        },
                                        label = {
                                            Text(
                                                text = stream.label.substringBefore(" "),
                                                style = MaterialTheme.typography.labelMedium,
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        ),
                                        modifier = Modifier.testTag("stream_chip_${stream.name.lowercase()}"),
                                    )
                                }
                            }
                        }
                    }
                }

                items(additionalSubjects, key = { it.id }) { subject ->
                    val doneCount = subject.chapters.count { progressByChapter[it.id]?.completed == true }
                    SubjectShowcaseCard(
                        subject = subject,
                        completedChapters = doneCount,
                        onClick = { onOpenSubject(subject) },
                    )
                }
            }

            // 7. Bookmarked Chapters Shelf
            if (bookmarkedChapters.isNotEmpty()) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = "Bookmarked for Revision",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
                items(bookmarkedChapters, key = { "bm_${it.id}" }) { chapter ->
                    CompactChapterCard(
                        chapter = chapter,
                        completed = progressByChapter[chapter.id]?.completed == true,
                        onClick = { onOpenChapter(chapter) },
                    )
                }
            }
        }
    }
}

@Composable
private fun AcademyHeroBanner(
    profile: StudentProfile,
    activeClass: Int,
    activeStream: AcademicStream,
    completedChapters: Int,
    totalChapters: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(22.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_school_hero_1795081725421),
            contentDescription = "Class $activeClass study workspace banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(HeroOverlayStart, HeroOverlayEnd)
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("home_academy_dp"),
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
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = HeroWarmHighlight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Assalamu Alaikum, ${profile.displayName.substringBefore(" ")} • Class $activeClass",
                        style = MaterialTheme.typography.bodySmall,
                        color = HeroSubtitleTint,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = if (activeClass == 10 || activeClass == 12) "BOARD" else "7–12",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                val ratio = if (totalChapters == 0) 0f
                else completedChapters.toFloat() / totalChapters.toFloat()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Class $activeClass Syllabus Mastery",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                    )
                    Text(
                        text = "$completedChapters / $totalChapters chapters",
                        style = MaterialTheme.typography.labelMedium,
                        color = HeroWarmHighlight,
                    )
                }
                LinearProgressIndicator(
                    progress = { ratio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .clip(RoundedCornerShape(99.dp)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = Color.White.copy(alpha = 0.25f),
                )
            }
        }
    }
}

@Composable
private fun QuickToolTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(24.dp),
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = contentColor,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun SubjectShowcaseCard(
    subject: SubjectData,
    completedChapters: Int,
    onClick: () -> Unit,
) {
    val total = subject.chapters.size.coerceAtLeast(1)
    val accent = subject.category.accentColor

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("subject_card_${subject.id}"),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(accent.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = subject.category.shortBadge,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = accent,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = subject.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    LinearProgressIndicator(
                        progress = { completedChapters.toFloat() / total.toFloat() },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(99.dp)),
                        color = accent,
                        trackColor = accent.copy(alpha = 0.16f),
                    )
                    Text(
                        text = "$completedChapters/$total ch",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open ${subject.name}",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun CompactChapterCard(
    chapter: ChapterData,
    completed: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (completed) MaterialTheme.colorScheme.tertiaryContainer
                        else MaterialTheme.colorScheme.primaryContainer
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (completed) Icons.Filled.CheckCircle else Icons.Filled.AutoStories,
                    contentDescription = null,
                    tint = if (completed) MaterialTheme.colorScheme.tertiary
                    else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${chapter.subjectName} • Ch ${chapter.number} • ${chapter.estimatedMinutes} min",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open chapter",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
