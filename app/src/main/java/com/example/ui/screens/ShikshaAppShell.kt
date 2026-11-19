package com.example.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.curriculum.CurriculumRepository
import com.example.data.model.AcademicStream
import com.example.data.model.EducationBoard
import com.example.data.model.StudentProfile
import com.example.data.model.UiState
import com.example.ui.viewmodel.ShikshaViewModel

enum class ShikshaTab(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String,
) {
    LEARN("Learn", Icons.Filled.AutoStories, Icons.Outlined.AutoStories, "nav_learn"),
    QUIZZES("Quizzes", Icons.Filled.Quiz, Icons.Outlined.Quiz, "nav_quizzes"),
    PLANNER("Planner", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth, "nav_planner"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile"),
}

@Composable
fun ShikshaAppShell(
    viewModel: ShikshaViewModel,
    accountEmail: String?,
    onSignOut: () -> Unit,
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val progressState by viewModel.chapterProgressState.collectAsStateWithLifecycle()
    val quizAttemptsState by viewModel.quizAttemptsState.collectAsStateWithLifecycle()
    val homeworkState by viewModel.homeworkState.collectAsStateWithLifecycle()
    val notesState by viewModel.studyNotesState.collectAsStateWithLifecycle()
    val timetableState by viewModel.timetableState.collectAsStateWithLifecycle()
    val activeQuiz by viewModel.activeQuiz.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    var currentTab by rememberSaveable { mutableStateOf(ShikshaTab.LEARN) }
    var selectedSubjectId by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedChapterId by rememberSaveable { mutableStateOf<String?>(null) }
    var showFormulaVault by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage != null) {
            snackbarHostState.showSnackbar(snackbarMessage!!)
            viewModel.clearSnackbar()
        }
    }

    val profile = (profileState as? UiState.Success)?.data ?: StudentProfile(
        id = viewModel.currentUserId,
        userId = viewModel.currentUserId,
        displayName = "Student",
        selectedClass = 10L,
        stream = AcademicStream.GENERAL.name,
        board = EducationBoard.CBSE.name,
    )
    val progressList = (progressState as? UiState.Success)?.data.orEmpty()
    val progressByChapter = remember(progressList) { progressList.associateBy { it.chapterId } }
    val quizAttempts = (quizAttemptsState as? UiState.Success)?.data.orEmpty()
    val homework = (homeworkState as? UiState.Success)?.data.orEmpty()
    val notes = (notesState as? UiState.Success)?.data.orEmpty()
    val timetable = (timetableState as? UiState.Success)?.data.orEmpty()

    // Surface stream-level Firestore error banner if any stream fails
    val streamError = listOf(profileState, progressState, quizAttemptsState, homeworkState, notesState, timetableState)
        .filterIsInstance<UiState.Error>()
        .firstOrNull()?.message

    // 1. Highest priority overlay: Active Quiz Session
    val quizSession = activeQuiz
    if (quizSession != null) {
        QuizSessionScreen(
            session = quizSession,
            onSelectAnswer = viewModel::selectQuizAnswer,
            onNavigateQuestion = viewModel::navigateQuizQuestion,
            onSubmitQuiz = viewModel::submitActiveQuiz,
            onClose = viewModel::closeActiveQuiz,
        )
        return
    }

    // 2. Formula Vault secondary screen
    if (showFormulaVault) {
        FormulaVaultScreen(
            classLevel = profile.classInt(),
            stream = profile.resolvedStream(),
            onBack = { showFormulaVault = false },
        )
        return
    }

    // 3. Chapter Reader secondary screen
    val activeChapter = selectedChapterId?.let { CurriculumRepository.getChapterById(it) }
    if (activeChapter != null) {
        val chProgress = progressByChapter[activeChapter.id]
        ChapterReaderScreen(
            chapter = activeChapter,
            progress = chProgress,
            onBack = { selectedChapterId = null },
            onToggleCompleted = { viewModel.toggleChapterCompleted(activeChapter, chProgress) },
            onToggleBookmark = { viewModel.toggleChapterBookmark(activeChapter, chProgress) },
            onStartQuiz = { viewModel.startChapterQuiz(activeChapter) },
        )
        return
    }

    // 4. Subject Detail secondary screen
    val activeSubject = selectedSubjectId?.let { CurriculumRepository.getSubjectById(it) }
    if (activeSubject != null) {
        SubjectDetailScreen(
            subject = activeSubject,
            progressByChapter = progressByChapter,
            onBack = { selectedSubjectId = null },
            onOpenChapter = { ch -> selectedChapterId = ch.id },
            onToggleCompleted = { ch, p -> viewModel.toggleChapterCompleted(ch, p) },
            onToggleBookmark = { ch, p -> viewModel.toggleChapterBookmark(ch, p) },
            onStartSubjectQuiz = { viewModel.startSubjectQuiz(activeSubject.id) },
        )
        return
    }

    // 5. Primary Adaptive Shell (NavigationBar on Compact, NavigationRail on Medium/Expanded)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavRail = maxWidth >= 600.dp

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (!useNavRail) {
                    NavigationBar(
                        windowInsets = WindowInsets.navigationBars,
                        containerColor = MaterialTheme.colorScheme.surface,
                    ) {
                        ShikshaTab.entries.forEach { tab ->
                            val selected = currentTab == tab
                            NavigationBarItem(
                                selected = selected,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label,
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag(tab.testTag),
                            )
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if (useNavRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        ShikshaTab.entries.forEach { tab ->
                            val selected = currentTab == tab
                            NavigationRailItem(
                                selected = selected,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label,
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag(tab.testTag),
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 760.dp),
                    ) {
                        if (streamError != null) {
                            Surface(
                                color = MaterialTheme.colorScheme.errorContainer,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "Sync issue: $streamError",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                )
                            }
                        }

                        if (profileState is UiState.Loading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            when (currentTab) {
                                ShikshaTab.LEARN -> LearnHomeScreen(
                                    profile = profile,
                                    progressList = progressList,
                                    pendingHomeworkCount = homework.count { !it.done },
                                    onSelectClass = { cls -> viewModel.selectClassAndStream(cls) },
                                    onSelectStream = { st -> viewModel.selectClassAndStream(profile.classInt(), st) },
                                    onOpenSubject = { subj -> selectedSubjectId = subj.id },
                                    onOpenChapter = { ch -> selectedChapterId = ch.id },
                                    onOpenFormulaVault = { showFormulaVault = true },
                                    onOpenQuizzesTab = { currentTab = ShikshaTab.QUIZZES },
                                )

                                ShikshaTab.QUIZZES -> QuizzesScreen(
                                    profile = profile,
                                    attempts = quizAttempts,
                                    onStartSubjectQuiz = { subj -> viewModel.startSubjectQuiz(subj.id) },
                                    onStartChapterQuiz = { ch -> viewModel.startChapterQuiz(ch) },
                                )

                                ShikshaTab.PLANNER -> PlannerScreen(
                                    activeClass = profile.classInt(),
                                    homework = homework,
                                    notes = notes,
                                    timetable = timetable,
                                    onAddHomework = { subj, title, details, due, priority ->
                                        viewModel.addHomework(profile.classInt(), subj, title, details, due, priority)
                                    },
                                    onToggleHomeworkDone = viewModel::toggleHomeworkDone,
                                    onDeleteHomework = viewModel::deleteHomework,
                                    onSaveNote = { id, subj, title, content, pinned ->
                                        viewModel.saveStudyNote(id, profile.classInt(), subj, title, content, pinned)
                                    },
                                    onToggleNotePin = viewModel::toggleNotePin,
                                    onDeleteNote = viewModel::deleteStudyNote,
                                    onAddTimetableEntry = viewModel::addTimetableEntry,
                                    onDeleteTimetableEntry = viewModel::deleteTimetableEntry,
                                )

                                ShikshaTab.PROFILE -> ProfileScreen(
                                    profile = profile,
                                    accountEmail = accountEmail,
                                    chapterProgress = progressList,
                                    quizAttempts = quizAttempts,
                                    homework = homework,
                                    onUpdateProfile = viewModel::updateProfileDetails,
                                    onSignOut = onSignOut,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
