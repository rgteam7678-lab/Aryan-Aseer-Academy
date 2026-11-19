package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.SubjectData
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectMathDeepColor
import com.example.ui.theme.SubjectUrduColor
import com.example.ui.theme.SubjectUrduDeepColor

private fun gradeStageLabel(grade: Int): String = when (grade) {
    7, 8 -> "Middle"
    9 -> "Secondary"
    10 -> "Board"
    11 -> "Senior"
    12 -> "Board"
    else -> "School"
}

/**
 * Adaptive Grade 7-12 Grid Selector (3x2 on phones, 6x1 on wide tablets/foldables).
 */
@Composable
fun GradeSelectionGrid(
    grades: List<Int>,
    selectedGrade: Int,
    onSelectGrade: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("grade_selection_grid"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.grade_grid_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(R.string.grade_grid_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "Class $selectedGrade",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                )
            }
        }

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val columns = if (maxWidth >= 560.dp) 6 else 3
            val rows = grades.chunked(columns)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                rows.forEach { rowGrades ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        rowGrades.forEach { grade ->
                            GradeGridTile(
                                grade = grade,
                                isSelected = grade == selectedGrade,
                                onClick = { onSelectGrade(grade) },
                                modifier = Modifier.weight(1f),
                            )
                        }
                        repeat(columns - rowGrades.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GradeGridTile(
    grade: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "grade_tile_bg_$grade",
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "grade_tile_fg_$grade",
    )
    val secondaryTextColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.82f)
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "grade_tile_sub_$grade",
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "grade_tile_border_$grade",
    )
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 1.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "grade_tile_elev_$grade",
    )

    val stage = gradeStageLabel(grade)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        shadowElevation = elevation,
        modifier = modifier
            .heightIn(min = 62.dp)
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(16.dp))
            .semantics {
                selected = isSelected
                contentDescription = "Select Grade $grade ($stage)"
            }
            .clickable(role = Role.Button, onClick = onClick)
            .testTag("grade_grid_tile_$grade"),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopEnd),
                )
            }
            Column(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "Class $grade",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = contentColor,
                    maxLines = 1,
                )
                Text(
                    text = stage,
                    style = MaterialTheme.typography.labelSmall,
                    color = secondaryTextColor,
                    maxLines = 1,
                )
            }
        }
    }
}

/**
 * Two Primary Action Buttons for the selected grade's Mathematics and Urdu (اردو) subjects.
 */
@Composable
fun PrimaryMathUrduButtons(
    selectedGrade: Int,
    mathSubject: SubjectData?,
    mathCompletedChapters: Int,
    urduSubject: SubjectData?,
    urduCompletedChapters: Int,
    onOpenSubject: (SubjectData) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("primary_subjects_section"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Class $selectedGrade • Math & Urdu",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = stringResource(R.string.core_subjects_badge),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
        }

        val snapshot = PrimarySubjectsSnapshot(
            grade = selectedGrade,
            mathSubject = mathSubject,
            mathDone = mathCompletedChapters,
            urduSubject = urduSubject,
            urduDone = urduCompletedChapters,
        )

        AnimatedContent(
            targetState = snapshot,
            contentKey = { it.grade },
            transitionSpec = {
                (fadeIn(animationSpec = tween(220)) +
                    slideInVertically(animationSpec = tween(220)) { it / 8 }) togetherWith
                    fadeOut(animationSpec = tween(140))
            },
            label = "primary_subject_buttons_transition",
        ) { state ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                state.mathSubject?.let { math ->
                    PrimarySubjectActionButton(
                        title = "Mathematics",
                        subtitle = "Class ${state.grade} • ${math.chapters.size} Chapters",
                        actionLabel = stringResource(R.string.action_open_math),
                        completedChapters = state.mathDone,
                        totalChapters = math.chapters.size,
                        gradientColors = listOf(SubjectMathColor, SubjectMathDeepColor),
                        badgeContent = {
                            Icon(
                                imageVector = Icons.Filled.Calculate,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp),
                            )
                        },
                        accessibilityLabel = "Open Class ${state.grade} Mathematics subject",
                        onClick = { onOpenSubject(math) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("primary_math_button"),
                    )
                }

                state.urduSubject?.let { urdu ->
                    PrimarySubjectActionButton(
                        title = "Urdu • اردو",
                        subtitle = "Class ${state.grade} • ${urdu.chapters.size} Chapters",
                        actionLabel = stringResource(R.string.action_open_urdu),
                        completedChapters = state.urduDone,
                        totalChapters = urdu.chapters.size,
                        gradientColors = listOf(SubjectUrduColor, SubjectUrduDeepColor),
                        badgeContent = {
                            Text(
                                text = "اردو",
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White,
                            )
                        },
                        accessibilityLabel = "Open Class ${state.grade} Urdu subject",
                        onClick = { onOpenSubject(urdu) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("primary_urdu_button"),
                    )
                }
            }
        }
    }
}

private data class PrimarySubjectsSnapshot(
    val grade: Int,
    val mathSubject: SubjectData?,
    val mathDone: Int,
    val urduSubject: SubjectData?,
    val urduDone: Int,
)

@Composable
private fun PrimarySubjectActionButton(
    title: String,
    subtitle: String,
    actionLabel: String,
    completedChapters: Int,
    totalChapters: Int,
    gradientColors: List<Color>,
    badgeContent: @Composable () -> Unit,
    accessibilityLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val safeTotal = totalChapters.coerceAtLeast(1)
    val progressRatio = completedChapters.toFloat() / safeTotal.toFloat()

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = gradientColors.first(),
        shadowElevation = 4.dp,
        modifier = modifier
            .heightIn(min = 148.dp)
            .minimumInteractiveComponentSize()
            .clip(RoundedCornerShape(20.dp))
            .semantics { contentDescription = accessibilityLabel }
            .clickable(role = Role.Button, onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(colors = gradientColors))
                .drawBehind {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.09f),
                        radius = size.minDimension * 0.52f,
                        center = Offset(size.width * 0.92f, size.height * 0.14f),
                    )
                }
                .padding(13.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        badgeContent()
                    }
                    Surface(
                        color = Color.White.copy(alpha = 0.18f),
                        shape = RoundedCornerShape(99.dp),
                    ) {
                        Text(
                            text = "$completedChapters/$safeTotal Ch",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.84f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                LinearProgressIndicator(
                    progress = { progressRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(99.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f),
                )

                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = actionLabel,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = gradientColors.last(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(gradientColors.first().copy(alpha = 0.14f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = gradientColors.last(),
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
