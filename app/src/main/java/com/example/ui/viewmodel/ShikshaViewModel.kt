package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.curriculum.CurriculumRepository
import com.example.data.firestore.ShikshaRepository
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterData
import com.example.data.model.ChapterProgress
import com.example.data.model.EducationBoard
import com.example.data.model.HomeworkPriority
import com.example.data.model.HomeworkTask
import com.example.data.model.QuizAttempt
import com.example.data.model.QuizQuestion
import com.example.data.model.StudentProfile
import com.example.data.model.StudyNote
import com.example.data.model.TimetableEntry
import com.example.data.model.UiState
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ActiveQuizSession(
    val classLevel: Int,
    val subjectId: String,
    val subjectName: String,
    val chapterId: String,
    val title: String,
    val questions: List<QuizQuestion>,
    val currentIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val startedAtMillis: Long = System.currentTimeMillis(),
    val finished: Boolean = false,
    val savedToCloud: Boolean = false,
)

class ShikshaViewModel(
    private val repository: ShikshaRepository,
    val currentUserId: String,
    private val initialDisplayName: String,
) : ViewModel() {

    private fun <T> Flow<T>.toSafeUiState(
        label: String,
    ): StateFlow<UiState<T>> = map<T, UiState<T>> { UiState.Success(it) }
        .catch { error ->
            val firestoreError = generateSequence(error) { it.cause }
                .filterIsInstance<FirebaseFirestoreException>()
                .firstOrNull()
            if (firestoreError == null && error is CancellationException) throw error
            val rootError = firestoreError ?: error
            Log.w(TAG, "Error observing $label", rootError)
            emit(UiState.Error(rootError.message ?: "Failed to load $label"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = UiState.Loading,
        )

    val profileState: StateFlow<UiState<StudentProfile>> =
        repository.observeStudentProfile(currentUserId)
            .map { profile ->
                profile ?: StudentProfile(
                    id = currentUserId,
                    userId = currentUserId,
                    displayName = initialDisplayName.ifBlank { "Student" },
                    selectedClass = 10L,
                    stream = AcademicStream.GENERAL.name,
                    board = EducationBoard.CBSE.name,
                    dailyGoalMinutes = 60L,
                )
            }
            .toSafeUiState("profile")

    val chapterProgressState: StateFlow<UiState<List<ChapterProgress>>> =
        repository.observeChapterProgress(currentUserId).toSafeUiState("chapter progress")

    val quizAttemptsState: StateFlow<UiState<List<QuizAttempt>>> =
        repository.observeQuizAttempts(currentUserId).toSafeUiState("quiz history")

    val homeworkState: StateFlow<UiState<List<HomeworkTask>>> =
        repository.observeHomework(currentUserId).toSafeUiState("homework")

    val studyNotesState: StateFlow<UiState<List<StudyNote>>> =
        repository.observeStudyNotes(currentUserId).toSafeUiState("study notes")

    val timetableState: StateFlow<UiState<List<TimetableEntry>>> =
        repository.observeTimetable(currentUserId).toSafeUiState("timetable")

    private val _activeQuiz = MutableStateFlow<ActiveQuizSession?>(null)
    val activeQuiz: StateFlow<ActiveQuizSession?> = _activeQuiz.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureStudentProfile(currentUserId, initialDisplayName)
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Profile sync pending."
                }
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

    fun selectClassAndStream(newClass: Int, newStream: AcademicStream? = null) {
        val current = (profileState.value as? UiState.Success)?.data
        val safeClass = newClass.coerceIn(7, 12)
        val resolvedStream = when {
            safeClass <= 10 -> AcademicStream.GENERAL
            newStream != null && newStream != AcademicStream.GENERAL -> newStream
            current != null && current.resolvedStream() != AcademicStream.GENERAL -> current.resolvedStream()
            else -> AcademicStream.SCIENCE
        }
        viewModelScope.launch {
            repository.updateStudentProfile(
                userId = currentUserId,
                displayName = current?.displayName ?: initialDisplayName.ifBlank { "Student" },
                schoolName = current?.schoolName.orEmpty(),
                selectedClass = safeClass,
                stream = resolvedStream,
                board = current?.resolvedBoard() ?: EducationBoard.CBSE,
                dailyGoalMinutes = current?.dailyGoalMinutes?.toInt() ?: 60,
            ).onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not switch class."
            }
        }
    }

    fun updateProfileDetails(
        displayName: String,
        schoolName: String,
        selectedClass: Int,
        stream: AcademicStream,
        board: EducationBoard,
        dailyGoalMinutes: Int,
    ) {
        viewModelScope.launch {
            repository.updateStudentProfile(
                userId = currentUserId,
                displayName = displayName,
                schoolName = schoolName,
                selectedClass = selectedClass,
                stream = stream,
                board = board,
                dailyGoalMinutes = dailyGoalMinutes,
            ).onSuccess {
                _snackbarMessage.value = "Student profile updated"
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Failed to save profile"
            }
        }
    }

    fun toggleChapterCompleted(chapter: ChapterData, existing: ChapterProgress?) {
        val nextCompleted = !(existing?.completed ?: false)
        val currentBookmarked = existing?.bookmarked ?: false
        viewModelScope.launch {
            repository.upsertChapterProgress(
                userId = currentUserId,
                chapterId = chapter.id,
                classLevel = chapter.classLevel,
                subjectId = chapter.subjectId,
                completed = nextCompleted,
                bookmarked = currentBookmarked,
            ).onSuccess {
                _snackbarMessage.value = if (nextCompleted) {
                    "Marked '${chapter.title}' completed"
                } else {
                    "Marked chapter in progress"
                }
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not update chapter status"
            }
        }
    }

    fun toggleChapterBookmark(chapter: ChapterData, existing: ChapterProgress?) {
        val currentCompleted = existing?.completed ?: false
        val nextBookmarked = !(existing?.bookmarked ?: false)
        viewModelScope.launch {
            repository.upsertChapterProgress(
                userId = currentUserId,
                chapterId = chapter.id,
                classLevel = chapter.classLevel,
                subjectId = chapter.subjectId,
                completed = currentCompleted,
                bookmarked = nextBookmarked,
            ).onSuccess {
                _snackbarMessage.value = if (nextBookmarked) "Bookmarked for revision" else "Removed bookmark"
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not update bookmark"
            }
        }
    }

    // --- Interactive Quiz Session ---

    fun startChapterQuiz(chapter: ChapterData) {
        if (chapter.quizQuestions.isEmpty()) return
        _activeQuiz.value = ActiveQuizSession(
            classLevel = chapter.classLevel,
            subjectId = chapter.subjectId,
            subjectName = chapter.subjectName,
            chapterId = chapter.id,
            title = chapter.title,
            questions = chapter.quizQuestions,
        )
    }

    fun startSubjectQuiz(subjectId: String) {
        val subject = CurriculumRepository.getSubjectById(subjectId) ?: return
        val questions = CurriculumRepository.getSubjectQuizQuestions(subjectId)
        if (questions.isEmpty()) return
        _activeQuiz.value = ActiveQuizSession(
            classLevel = subject.classLevel,
            subjectId = subject.id,
            subjectName = subject.name,
            chapterId = "${subject.id}_mock",
            title = "${subject.name} Full Practice Test",
            questions = questions,
        )
    }

    fun selectQuizAnswer(optionIndex: Int) {
        val session = _activeQuiz.value ?: return
        if (session.finished) return
        _activeQuiz.value = session.copy(
            selectedAnswers = session.selectedAnswers + (session.currentIndex to optionIndex)
        )
    }

    fun navigateQuizQuestion(delta: Int) {
        val session = _activeQuiz.value ?: return
        val next = (session.currentIndex + delta).coerceIn(0, session.questions.lastIndex)
        _activeQuiz.value = session.copy(currentIndex = next)
    }

    fun submitActiveQuiz() {
        val session = _activeQuiz.value ?: return
        if (session.finished) return
        val score = session.questions.indices.count { idx ->
            session.selectedAnswers[idx] == session.questions[idx].correctIndex
        }
        val durationSec = ((System.currentTimeMillis() - session.startedAtMillis) / 1000L)
            .toInt().coerceIn(1, 7200)
        _activeQuiz.value = session.copy(finished = true)

        viewModelScope.launch {
            repository.recordQuizAttempt(
                userId = currentUserId,
                classLevel = session.classLevel,
                subjectId = session.subjectId,
                subjectName = session.subjectName,
                chapterId = session.chapterId,
                chapterTitle = session.title,
                score = score,
                totalQuestions = session.questions.size,
                durationSeconds = durationSec,
            ).onSuccess {
                _activeQuiz.value = _activeQuiz.value?.copy(savedToCloud = true)
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Quiz finished (cloud save failed)"
            }
        }
    }

    fun closeActiveQuiz() {
        _activeQuiz.value = null
    }

    // --- Homework CRUD ---

    fun addHomework(
        classLevel: Int,
        subjectName: String,
        title: String,
        details: String,
        dueDateLabel: String,
        priority: HomeworkPriority,
    ) {
        viewModelScope.launch {
            repository.addHomework(
                userId = currentUserId,
                classLevel = classLevel,
                subjectName = subjectName,
                title = title,
                details = details,
                dueDateLabel = dueDateLabel,
                priority = priority,
            ).onSuccess {
                _snackbarMessage.value = "Homework added"
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not add homework"
            }
        }
    }

    fun toggleHomeworkDone(task: HomeworkTask) {
        viewModelScope.launch {
            repository.toggleHomeworkDone(currentUserId, task.id, !task.done)
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Could not update homework"
                }
        }
    }

    fun deleteHomework(taskId: String) {
        viewModelScope.launch {
            repository.deleteHomework(currentUserId, taskId)
                .onSuccess { _snackbarMessage.value = "Homework removed" }
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Could not delete homework"
                }
        }
    }

    // --- Study Notes CRUD ---

    fun saveStudyNote(
        noteId: String?,
        classLevel: Int,
        subjectName: String,
        title: String,
        content: String,
        pinned: Boolean,
    ) {
        viewModelScope.launch {
            repository.saveStudyNote(
                userId = currentUserId,
                noteId = noteId,
                classLevel = classLevel,
                subjectName = subjectName,
                title = title,
                content = content,
                pinned = pinned,
            ).onSuccess {
                _snackbarMessage.value = if (noteId == null) "Revision note saved" else "Note updated"
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not save note"
            }
        }
    }

    fun toggleNotePin(note: StudyNote) {
        viewModelScope.launch {
            repository.toggleNotePin(currentUserId, note.id, !note.pinned)
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Could not update pin"
                }
        }
    }

    fun deleteStudyNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteStudyNote(currentUserId, noteId)
                .onSuccess { _snackbarMessage.value = "Note deleted" }
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Could not delete note"
                }
        }
    }

    // --- Timetable CRUD ---

    fun addTimetableEntry(
        dayOfWeek: Int,
        subjectName: String,
        timeSlot: String,
        topic: String,
    ) {
        viewModelScope.launch {
            repository.addTimetableEntry(
                userId = currentUserId,
                dayOfWeek = dayOfWeek,
                subjectName = subjectName,
                timeSlot = timeSlot,
                topic = topic,
            ).onSuccess {
                _snackbarMessage.value = "Class slot added to timetable"
            }.onFailure { err ->
                _snackbarMessage.value = err.localizedMessage ?: "Could not add timetable slot"
            }
        }
    }

    fun deleteTimetableEntry(entryId: String) {
        viewModelScope.launch {
            repository.deleteTimetableEntry(currentUserId, entryId)
                .onSuccess { _snackbarMessage.value = "Timetable slot removed" }
                .onFailure { err ->
                    _snackbarMessage.value = err.localizedMessage ?: "Could not remove slot"
                }
        }
    }

    private companion object {
        const val TAG = "ShikshaVM"
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
