package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.SubjectBiologyColor
import com.example.ui.theme.SubjectChemistryColor
import com.example.ui.theme.SubjectCommerceColor
import com.example.ui.theme.SubjectComputerColor
import com.example.ui.theme.SubjectEnglishColor
import com.example.ui.theme.SubjectHindiColor
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectPhysicsColor
import com.example.ui.theme.SubjectScienceColor
import com.example.ui.theme.SubjectSocialColor
import com.example.ui.theme.SubjectUrduColor

enum class SubjectCategory(val accentColor: Color, val shortBadge: String) {
    MATH(SubjectMathColor, "MATH"),
    URDU(SubjectUrduColor, "اردو"),
    SCIENCE(SubjectScienceColor, "SCI"),
    PHYSICS(SubjectPhysicsColor, "PHY"),
    CHEMISTRY(SubjectChemistryColor, "CHEM"),
    BIOLOGY(SubjectBiologyColor, "BIO"),
    ENGLISH(SubjectEnglishColor, "ENG"),
    SOCIAL(SubjectSocialColor, "SST"),
    COMMERCE(SubjectCommerceColor, "COM"),
    COMPUTER(SubjectComputerColor, "CS"),
    HINDI(SubjectHindiColor, "HIN"),
}

data class FormulaItem(
    val title: String,
    val expression: String,
    val note: String,
)

data class SolvedExample(
    val problem: String,
    val steps: List<String>,
    val answer: String,
)

data class QuizQuestion(
    val id: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

data class ChapterData(
    val id: String,
    val classLevel: Int,
    val subjectId: String,
    val subjectName: String,
    val number: Int,
    val title: String,
    val estimatedMinutes: Int,
    val weightage: String,
    val summary: String,
    val keyPoints: List<String>,
    val formulas: List<FormulaItem>,
    val solvedExample: SolvedExample,
    val quizQuestions: List<QuizQuestion>,
)

data class SubjectData(
    val id: String,
    val classLevel: Int,
    val streams: Set<AcademicStream>,
    val name: String,
    val subtitle: String,
    val category: SubjectCategory,
    val chapters: List<ChapterData>,
)
