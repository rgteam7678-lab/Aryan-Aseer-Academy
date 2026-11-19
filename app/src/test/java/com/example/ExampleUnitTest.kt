package com.example

import com.example.data.curriculum.CurriculumRepository
import com.example.data.model.AcademicStream
import com.example.data.model.SubjectCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    private val firestoreIdPattern = Regex("^[a-zA-Z0-9_\\-]+$")

    @Test
    fun curriculumCoversMathAndUrduAcrossAllClassesSevenToTwelve() {
        val seenChapterIds = mutableSetOf<String>()

        fun validateClassSubjects(cls: Int, stream: AcademicStream) {
            val subjects = CurriculumRepository.getSubjectsFor(cls, stream)
            assertTrue("Class $cls ($stream) should have subjects", subjects.isNotEmpty())

            // Verify zero duplicate subject IDs in this class/stream view
            val uniqueSubjectIds = subjects.map { it.id }.toSet()
            assertEquals("Duplicate subject IDs in Class $cls ($stream)", subjects.size, uniqueSubjectIds.size)

            // Verify Mathematics is 1st and Urdu (اردو) is 2nd in every Class 7..12 across every stream
            assertEquals("First subject in Class $cls ($stream) should be Mathematics", SubjectCategory.MATH, subjects[0].category)
            assertEquals("Second subject in Class $cls ($stream) should be Urdu", SubjectCategory.URDU, subjects[1].category)
            assertEquals("c${cls}_math", subjects[0].id)
            assertEquals("c${cls}_urdu", subjects[1].id)

            subjects.forEach { subject ->
                assertTrue("Subject ID '${subject.id}' must match Firestore pattern", firestoreIdPattern.matches(subject.id))
                assertTrue("Subject ID '${subject.id}' <= 60 chars", subject.id.length <= 60)
                assertTrue("Subject name '${subject.name}' <= 60 chars", subject.name.length <= 60)
                assertTrue("${subject.name} should have chapters", subject.chapters.isNotEmpty())

                subject.chapters.forEach { chapter ->
                    assertTrue("Chapter ID '${chapter.id}' must match Firestore pattern", firestoreIdPattern.matches(chapter.id))
                    assertTrue("Chapter ID '${chapter.id}' <= 80 chars", chapter.id.length <= 80)
                    assertTrue("${chapter.title} should have key points", chapter.keyPoints.isNotEmpty())
                    assertTrue("${chapter.title} should have formulas/rules", chapter.formulas.isNotEmpty())
                    assertTrue("${chapter.title} should have MCQs", chapter.quizQuestions.isNotEmpty())
                    chapter.quizQuestions.forEach { q ->
                        assertTrue("MCQ ${q.id} correctIndex out of range", q.correctIndex in q.options.indices)
                    }
                    seenChapterIds.add(chapter.id)
                }
            }
        }

        for (cls in 7..10) {
            validateClassSubjects(cls, AcademicStream.GENERAL)
        }
        for (cls in 11..12) {
            listOf(AcademicStream.SCIENCE, AcademicStream.COMMERCE, AcademicStream.ARTS).forEach { stream ->
                validateClassSubjects(cls, stream)
            }
        }

        // Verify Formula & Qawaid vault resolves formulas for every class 7..12
        for (cls in 7..12) {
            val stream = if (cls <= 10) AcademicStream.GENERAL else AcademicStream.SCIENCE
            val formulas = CurriculumRepository.getFormulasFor(cls, stream)
            assertTrue("Class $cls should have Math formulas", formulas.any { it.subjectId == "c${cls}_math" })
            assertTrue("Class $cls should have Urdu Qawaid/rules", formulas.any { it.subjectId == "c${cls}_urdu" })
        }

        assertTrue("Expected rich chapter coverage across Classes 7–12", seenChapterIds.size >= 40)
    }
}
