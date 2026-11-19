package com.example.data.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

enum class AcademicStream(val label: String) {
    GENERAL("General (7-10)"),
    SCIENCE("Science"),
    COMMERCE("Commerce"),
    ARTS("Arts & Humanities");

    companion object {
        fun fromRaw(raw: String?): AcademicStream =
            entries.firstOrNull { it.name == raw } ?: GENERAL
    }
}

enum class EducationBoard(val label: String) {
    CBSE("CBSE / NCERT"),
    ICSE("ICSE / ISC"),
    STATE("State Board");

    companion object {
        fun fromRaw(raw: String?): EducationBoard =
            entries.firstOrNull { it.name == raw } ?: CBSE
    }
}

enum class HomeworkPriority(val label: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    companion object {
        fun fromRaw(raw: String?): HomeworkPriority =
            entries.firstOrNull { it.name == raw } ?: MEDIUM
    }
}

@Keep
@IgnoreExtraProperties
data class StudentProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val displayName: String = "Student",
    val schoolName: String = "",
    val selectedClass: Long = 10L,
    val stream: String = AcademicStream.GENERAL.name,
    val board: String = EducationBoard.CBSE.name,
    val dailyGoalMinutes: Long = 60L,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
) {
    fun classInt(): Int = selectedClass.toInt().coerceIn(7, 12)
    fun resolvedStream(): AcademicStream =
        if (classInt() <= 10) AcademicStream.GENERAL else AcademicStream.fromRaw(stream).let {
            if (it == AcademicStream.GENERAL) AcademicStream.SCIENCE else it
        }
    fun resolvedBoard(): EducationBoard = EducationBoard.fromRaw(board)
}

@Keep
@IgnoreExtraProperties
data class ChapterProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val chapterId: String = "",
    val classLevel: Long = 10L,
    val subjectId: String = "",
    val completed: Boolean = false,
    val bookmarked: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
)

@Keep
@IgnoreExtraProperties
data class QuizAttempt(
    @DocumentId val id: String = "",
    val userId: String = "",
    val classLevel: Long = 10L,
    val subjectId: String = "",
    val subjectName: String = "",
    val chapterId: String = "",
    val chapterTitle: String = "",
    val score: Long = 0L,
    val totalQuestions: Long = 1L,
    val accuracyPercent: Long = 0L,
    val durationSeconds: Long = 0L,
    @ServerTimestamp val createdAt: Timestamp? = null,
)

@Keep
@IgnoreExtraProperties
data class HomeworkTask(
    @DocumentId val id: String = "",
    val userId: String = "",
    val classLevel: Long = 10L,
    val subjectName: String = "",
    val title: String = "",
    val details: String = "",
    val dueDateLabel: String = "Tomorrow",
    val priority: String = HomeworkPriority.MEDIUM.name,
    val done: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
) {
    fun resolvedPriority(): HomeworkPriority = HomeworkPriority.fromRaw(priority)
}

@Keep
@IgnoreExtraProperties
data class StudyNote(
    @DocumentId val id: String = "",
    val userId: String = "",
    val classLevel: Long = 10L,
    val subjectName: String = "",
    val title: String = "",
    val content: String = "",
    val pinned: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
)

@Keep
@IgnoreExtraProperties
data class TimetableEntry(
    @DocumentId val id: String = "",
    val userId: String = "",
    val dayOfWeek: Long = 1L,
    val subjectName: String = "",
    val timeSlot: String = "",
    val topic: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null,
)
