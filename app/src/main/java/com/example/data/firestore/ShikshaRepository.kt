package com.example.data.firestore

import android.content.Context
import com.example.R
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterProgress
import com.example.data.model.EducationBoard
import com.example.data.model.HomeworkPriority
import com.example.data.model.HomeworkTask
import com.example.data.model.QuizAttempt
import com.example.data.model.StudentProfile
import com.example.data.model.StudyNote
import com.example.data.model.TimetableEntry
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ShikshaRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = Firebase.auth,
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val idPattern = Regex("^[a-zA-Z0-9_\\-]{1,128}$")

    private fun requireAuthorizedUid(expectedUid: String): String {
        val signedInUid = auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
        check(signedInUid == expectedUid) { "Signed-in user mismatch; refusing cross-user access." }
        return signedInUid
    }

    private fun <T> Flow<T>.logFirestoreStreamErrors(
        userId: String,
        operationType: OperationType,
        path: String,
    ): Flow<T> = catch { error ->
        val firestoreError = generateSequence(error) { it.cause }
            .filterIsInstance<FirebaseFirestoreException>()
            .firstOrNull()
        if (firestoreError == null && error is CancellationException) throw error
        val exceptionToLog = firestoreError ?: (error as? Exception)
        if (exceptionToLog != null && auth.currentUser?.uid == userId) {
            handleFirestoreError(exceptionToLog, operationType, path)
        }
        throw error
    }

    // --- 1. Student Profile ---

    fun observeStudentProfile(userId: String): Flow<StudentProfile?> {
        val path = "users/$userId"
        return db.collection("users").document(userId)
            .snapshots()
            .map { snapshot ->
                if (!snapshot.exists()) null
                else snapshot.toObject(
                    StudentProfile::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .logFirestoreStreamErrors(userId, OperationType.GET, path)
    }

    suspend fun ensureStudentProfile(
        userId: String,
        defaultDisplayName: String,
    ): Result<Unit> {
        val path = "users/$userId"
        return try {
            val uid = requireAuthorizedUid(userId)
            val docRef = db.collection("users").document(uid)
            val snapshot = docRef.get().await()
            if (!snapshot.exists()) {
                val safeName = defaultDisplayName.trim().take(60).ifEmpty { "Student" }
                val payload = mapOf(
                    "userId" to uid,
                    "displayName" to safeName,
                    "schoolName" to "",
                    "selectedClass" to 10L,
                    "stream" to AcademicStream.GENERAL.name,
                    "board" to EducationBoard.CBSE.name,
                    "dailyGoalMinutes" to 60L,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                )
                docRef.set(payload).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, path)
            Result.failure(e)
        }
    }

    suspend fun updateStudentProfile(
        userId: String,
        displayName: String,
        schoolName: String,
        selectedClass: Int,
        stream: AcademicStream,
        board: EducationBoard,
        dailyGoalMinutes: Int,
    ): Result<Unit> {
        val path = "users/$userId"
        return try {
            val uid = requireAuthorizedUid(userId)
            val safeName = displayName.trim().take(60)
            require(safeName.isNotEmpty()) { "Student name cannot be empty." }
            val safeSchool = schoolName.trim().take(80)
            val safeClass = selectedClass.coerceIn(7, 12)
            val safeStream = if (safeClass <= 10) AcademicStream.GENERAL else {
                if (stream == AcademicStream.GENERAL) AcademicStream.SCIENCE else stream
            }
            val safeGoal = dailyGoalMinutes.coerceIn(15, 360)

            val docRef = db.collection("users").document(uid)
            val snapshot = docRef.get().await()
            if (!snapshot.exists()) {
                val payload = mapOf(
                    "userId" to uid,
                    "displayName" to safeName,
                    "schoolName" to safeSchool,
                    "selectedClass" to safeClass.toLong(),
                    "stream" to safeStream.name,
                    "board" to board.name,
                    "dailyGoalMinutes" to safeGoal.toLong(),
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                )
                docRef.set(payload).await()
            } else {
                docRef.update(
                    mapOf(
                        "displayName" to safeName,
                        "schoolName" to safeSchool,
                        "selectedClass" to safeClass.toLong(),
                        "stream" to safeStream.name,
                        "board" to board.name,
                        "dailyGoalMinutes" to safeGoal.toLong(),
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, path)
            Result.failure(e)
        }
    }

    // --- 2. Chapter Progress & Bookmarks ---

    fun observeChapterProgress(userId: String): Flow<List<ChapterProgress>> {
        val path = "users/$userId/chapter_progress"
        return db.collection("users").document(userId)
            .collection("chapter_progress")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    ChapterProgress::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
            .logFirestoreStreamErrors(userId, OperationType.LIST, path)
    }

    suspend fun upsertChapterProgress(
        userId: String,
        chapterId: String,
        classLevel: Int,
        subjectId: String,
        completed: Boolean,
        bookmarked: Boolean,
    ): Result<Unit> {
        val path = "users/$userId/chapter_progress/$chapterId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(chapterId) && chapterId.length <= 80) { "Invalid chapter ID." }
            require(subjectId.isNotBlank() && subjectId.length <= 60) { "Invalid subject ID." }
            val safeClass = classLevel.coerceIn(7, 12).toLong()

            val docRef = db.collection("users").document(uid)
                .collection("chapter_progress").document(chapterId)
            val existing = docRef.get().await()
            if (!existing.exists()) {
                docRef.set(
                    mapOf(
                        "userId" to uid,
                        "chapterId" to chapterId,
                        "classLevel" to safeClass,
                        "subjectId" to subjectId,
                        "completed" to completed,
                        "bookmarked" to bookmarked,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            } else {
                docRef.update(
                    mapOf(
                        "classLevel" to safeClass,
                        "subjectId" to subjectId,
                        "completed" to completed,
                        "bookmarked" to bookmarked,
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, path)
            Result.failure(e)
        }
    }

    // --- 3. Quiz Attempts ---

    fun observeQuizAttempts(userId: String): Flow<List<QuizAttempt>> {
        val path = "users/$userId/quiz_attempts"
        return db.collection("users").document(userId)
            .collection("quiz_attempts")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    QuizAttempt::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                ).sortedByDescending { it.createdAt?.seconds ?: 0L }
            }
            .logFirestoreStreamErrors(userId, OperationType.LIST, path)
    }

    suspend fun recordQuizAttempt(
        userId: String,
        classLevel: Int,
        subjectId: String,
        subjectName: String,
        chapterId: String,
        chapterTitle: String,
        score: Int,
        totalQuestions: Int,
        durationSeconds: Int,
    ): Result<Unit> {
        var docPath = "users/$userId/quiz_attempts"
        return try {
            val uid = requireAuthorizedUid(userId)
            val safeTotal = totalQuestions.coerceIn(1, 100)
            val safeScore = score.coerceIn(0, safeTotal)
            val accuracy = ((safeScore * 100) / safeTotal).coerceIn(0, 100)
            val docRef = db.collection("users").document(uid)
                .collection("quiz_attempts").document()
            docPath = docRef.path

            docRef.set(
                mapOf(
                    "userId" to uid,
                    "classLevel" to classLevel.coerceIn(7, 12).toLong(),
                    "subjectId" to subjectId.trim().take(60).ifEmpty { "general" },
                    "subjectName" to subjectName.trim().take(60).ifEmpty { "Subject" },
                    "chapterId" to chapterId.trim().take(80).ifEmpty { "quiz" },
                    "chapterTitle" to chapterTitle.trim().take(120).ifEmpty { "Practice Quiz" },
                    "score" to safeScore.toLong(),
                    "totalQuestions" to safeTotal.toLong(),
                    "accuracyPercent" to accuracy.toLong(),
                    "durationSeconds" to durationSeconds.coerceIn(0, 7200).toLong(),
                    "createdAt" to FieldValue.serverTimestamp(),
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    // --- 4. Homework Tasks ---

    fun observeHomework(userId: String): Flow<List<HomeworkTask>> {
        val path = "users/$userId/homework"
        return db.collection("users").document(userId)
            .collection("homework")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    HomeworkTask::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                ).sortedWith(
                    compareBy<HomeworkTask> { it.done }
                        .thenByDescending { it.createdAt?.seconds ?: 0L }
                )
            }
            .logFirestoreStreamErrors(userId, OperationType.LIST, path)
    }

    suspend fun addHomework(
        userId: String,
        classLevel: Int,
        subjectName: String,
        title: String,
        details: String,
        dueDateLabel: String,
        priority: HomeworkPriority,
    ): Result<Unit> {
        var docPath = "users/$userId/homework"
        return try {
            val uid = requireAuthorizedUid(userId)
            val safeTitle = title.trim().take(120)
            require(safeTitle.isNotEmpty()) { "Homework title cannot be empty." }
            val safeSubject = subjectName.trim().take(60).ifEmpty { "General" }
            val safeDetails = details.trim().take(500)
            val safeDue = dueDateLabel.trim().take(40).ifEmpty { "Tomorrow" }

            val docRef = db.collection("users").document(uid)
                .collection("homework").document()
            docPath = docRef.path

            docRef.set(
                mapOf(
                    "userId" to uid,
                    "classLevel" to classLevel.coerceIn(7, 12).toLong(),
                    "subjectName" to safeSubject,
                    "title" to safeTitle,
                    "details" to safeDetails,
                    "dueDateLabel" to safeDue,
                    "priority" to priority.name,
                    "done" to false,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun toggleHomeworkDone(userId: String, homeworkId: String, done: Boolean): Result<Unit> {
        val path = "users/$userId/homework/$homeworkId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(homeworkId)) { "Invalid homework ID." }
            db.collection("users").document(uid)
                .collection("homework").document(homeworkId)
                .update(
                    mapOf(
                        "done" to done,
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, path)
            Result.failure(e)
        }
    }

    suspend fun deleteHomework(userId: String, homeworkId: String): Result<Unit> {
        val path = "users/$userId/homework/$homeworkId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(homeworkId)) { "Invalid homework ID." }
            db.collection("users").document(uid)
                .collection("homework").document(homeworkId)
                .delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, path)
            Result.failure(e)
        }
    }

    // --- 5. Personal Study Notes ---

    fun observeStudyNotes(userId: String): Flow<List<StudyNote>> {
        val path = "users/$userId/study_notes"
        return db.collection("users").document(userId)
            .collection("study_notes")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    StudyNote::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                ).sortedWith(
                    compareByDescending<StudyNote> { it.pinned }
                        .thenByDescending { it.updatedAt?.seconds ?: it.createdAt?.seconds ?: 0L }
                )
            }
            .logFirestoreStreamErrors(userId, OperationType.LIST, path)
    }

    suspend fun saveStudyNote(
        userId: String,
        noteId: String?,
        classLevel: Int,
        subjectName: String,
        title: String,
        content: String,
        pinned: Boolean,
    ): Result<Unit> {
        var docPath = "users/$userId/study_notes"
        return try {
            val uid = requireAuthorizedUid(userId)
            val safeTitle = title.trim().take(120)
            val safeContent = content.trim().take(2000)
            require(safeTitle.isNotEmpty()) { "Note title cannot be empty." }
            require(safeContent.isNotEmpty()) { "Note content cannot be empty." }
            val safeSubject = subjectName.trim().take(60).ifEmpty { "General" }
            val safeClass = classLevel.coerceIn(7, 12).toLong()

            if (noteId.isNullOrBlank()) {
                val docRef = db.collection("users").document(uid)
                    .collection("study_notes").document()
                docPath = docRef.path
                docRef.set(
                    mapOf(
                        "userId" to uid,
                        "classLevel" to safeClass,
                        "subjectName" to safeSubject,
                        "title" to safeTitle,
                        "content" to safeContent,
                        "pinned" to pinned,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            } else {
                require(idPattern.matches(noteId)) { "Invalid note ID." }
                val docRef = db.collection("users").document(uid)
                    .collection("study_notes").document(noteId)
                docPath = docRef.path
                docRef.update(
                    mapOf(
                        "classLevel" to safeClass,
                        "subjectName" to safeSubject,
                        "title" to safeTitle,
                        "content" to safeContent,
                        "pinned" to pinned,
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docPath)
            Result.failure(e)
        }
    }

    suspend fun toggleNotePin(userId: String, noteId: String, pinned: Boolean): Result<Unit> {
        val path = "users/$userId/study_notes/$noteId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(noteId)) { "Invalid note ID." }
            db.collection("users").document(uid)
                .collection("study_notes").document(noteId)
                .update(
                    mapOf(
                        "pinned" to pinned,
                        "updatedAt" to FieldValue.serverTimestamp(),
                    )
                ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, path)
            Result.failure(e)
        }
    }

    suspend fun deleteStudyNote(userId: String, noteId: String): Result<Unit> {
        val path = "users/$userId/study_notes/$noteId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(noteId)) { "Invalid note ID." }
            db.collection("users").document(uid)
                .collection("study_notes").document(noteId)
                .delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, path)
            Result.failure(e)
        }
    }

    // --- 6. Weekly Timetable ---

    fun observeTimetable(userId: String): Flow<List<TimetableEntry>> {
        val path = "users/$userId/timetable"
        return db.collection("users").document(userId)
            .collection("timetable")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    TimetableEntry::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                ).sortedWith(compareBy<TimetableEntry> { it.dayOfWeek }.thenBy { it.timeSlot })
            }
            .logFirestoreStreamErrors(userId, OperationType.LIST, path)
    }

    suspend fun addTimetableEntry(
        userId: String,
        dayOfWeek: Int,
        subjectName: String,
        timeSlot: String,
        topic: String,
    ): Result<Unit> {
        var docPath = "users/$userId/timetable"
        return try {
            val uid = requireAuthorizedUid(userId)
            val safeSubject = subjectName.trim().take(60)
            val safeTime = timeSlot.trim().take(40)
            require(safeSubject.isNotEmpty()) { "Subject name cannot be empty." }
            require(safeTime.isNotEmpty()) { "Time slot cannot be empty." }
            val safeTopic = topic.trim().take(120)
            val safeDay = dayOfWeek.coerceIn(1, 7).toLong()

            val docRef = db.collection("users").document(uid)
                .collection("timetable").document()
            docPath = docRef.path

            docRef.set(
                mapOf(
                    "userId" to uid,
                    "dayOfWeek" to safeDay,
                    "subjectName" to safeSubject,
                    "timeSlot" to safeTime,
                    "topic" to safeTopic,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                )
            ).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun deleteTimetableEntry(userId: String, entryId: String): Result<Unit> {
        val path = "users/$userId/timetable/$entryId"
        return try {
            val uid = requireAuthorizedUid(userId)
            require(idPattern.matches(entryId)) { "Invalid timetable entry ID." }
            db.collection("users").document(uid)
                .collection("timetable").document(entryId)
                .delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, path)
            Result.failure(e)
        }
    }
}
