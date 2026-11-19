package com.example.data.curriculum

import com.example.data.model.AcademicStream
import com.example.data.model.ChapterData
import com.example.data.model.FormulaItem
import com.example.data.model.QuizQuestion
import com.example.data.model.SubjectData

data class IndexedFormula(
    val classLevel: Int,
    val subjectId: String,
    val subjectName: String,
    val chapterTitle: String,
    val formula: FormulaItem,
)

object CurriculumRepository {

    val supportedClasses: List<Int> = listOf(7, 8, 9, 10, 11, 12)

    private val prioritySubjects: List<SubjectData> by lazy {
        CurriculumMathComplete.subjects + CurriculumUrduAll.subjects
    }

    private val allSubjects: List<SubjectData> by lazy {
        val overriddenIds = prioritySubjects.map { it.id }.toSet()
        prioritySubjects + (
            CurriculumMiddleSchool.subjects +
                CurriculumSecondarySchool.subjects +
                CurriculumSeniorSecondary.subjects
            ).filterNot { it.id in overriddenIds }
    }

    private val subjectsById: Map<String, SubjectData> by lazy {
        allSubjects.associateBy { it.id }
    }

    private val chaptersById: Map<String, ChapterData> by lazy {
        allSubjects.flatMap { it.chapters }.associateBy { it.id }
    }

    fun getSubjectsFor(classLevel: Int, stream: AcademicStream): List<SubjectData> {
        val normalizedClass = classLevel.coerceIn(7, 12)
        val effectiveStream = if (normalizedClass <= 10) {
            AcademicStream.GENERAL
        } else {
            if (stream == AcademicStream.GENERAL) AcademicStream.SCIENCE else stream
        }
        return allSubjects.filter { subject ->
            subject.classLevel == normalizedClass && effectiveStream in subject.streams
        }
    }

    fun getSubjectById(subjectId: String): SubjectData? = subjectsById[subjectId]

    fun getChapterById(chapterId: String): ChapterData? = chaptersById[chapterId]

    fun getAllChaptersFor(classLevel: Int, stream: AcademicStream): List<ChapterData> =
        getSubjectsFor(classLevel, stream).flatMap { it.chapters }

    fun getFormulasFor(classLevel: Int, stream: AcademicStream): List<IndexedFormula> =
        getSubjectsFor(classLevel, stream).flatMap { subject ->
            subject.chapters.flatMap { chapter ->
                chapter.formulas.map { formula ->
                    IndexedFormula(
                        classLevel = subject.classLevel,
                        subjectId = subject.id,
                        subjectName = subject.name,
                        chapterTitle = chapter.title,
                        formula = formula,
                    )
                }
            }
        }

    fun getSubjectQuizQuestions(subjectId: String): List<QuizQuestion> =
        getSubjectById(subjectId)?.chapters?.flatMap { it.quizQuestions } ?: emptyList()

    fun searchChapters(classLevel: Int, stream: AcademicStream, query: String): List<ChapterData> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return getAllChaptersFor(classLevel, stream).filter { chapter ->
            chapter.title.lowercase().contains(q) ||
                chapter.subjectName.lowercase().contains(q) ||
                chapter.summary.lowercase().contains(q) ||
                chapter.formulas.any {
                    it.title.lowercase().contains(q) || it.expression.lowercase().contains(q)
                }
        }
    }
}
