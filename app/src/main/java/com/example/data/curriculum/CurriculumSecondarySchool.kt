package com.example.data.curriculum

import com.example.data.model.AcademicStream
import com.example.data.model.ChapterData
import com.example.data.model.FormulaItem
import com.example.data.model.QuizQuestion
import com.example.data.model.SolvedExample
import com.example.data.model.SubjectCategory
import com.example.data.model.SubjectData

internal object CurriculumSecondarySchool {

    private val generalStreams = setOf(
        AcademicStream.GENERAL,
        AcademicStream.SCIENCE,
        AcademicStream.COMMERCE,
        AcademicStream.ARTS,
    )

    val subjects: List<SubjectData> = listOf(
        // ==================== CLASS 9 ====================
        SubjectData(
            id = "c9_math",
            classLevel = 9,
            streams = generalStreams,
            name = "Mathematics",
            subtitle = "Polynomials, Heron's Formula & Circles",
            category = SubjectCategory.MATH,
            chapters = listOf(
                ChapterData(
                    id = "c9_math_ch1",
                    classLevel = 9,
                    subjectId = "c9_math",
                    subjectName = "Mathematics",
                    number = 1,
                    title = "Polynomials & Factor Theorem",
                    estimatedMinutes = 28,
                    weightage = "10 Marks",
                    summary = "Master zeros of polynomials, Remainder & Factor Theorems, and cubic algebraic identities.",
                    keyPoints = listOf(
                        "Degree of a polynomial is the highest power of the variable with a non-zero coefficient.",
                        "Factor Theorem: (x - a) is a factor of p(x) if and only if p(a) = 0.",
                        "If a + b + c = 0, then a³ + b³ + c³ = 3abc (frequent 3-mark exam question).",
                    ),
                    formulas = listOf(
                        FormulaItem("Cube of Sum", "(x + y)³ = x³ + y³ + 3xy(x + y)", "Similarly (x - y)³ = x³ - y³ - 3xy(x - y)"),
                        FormulaItem("Sum of Cubes", "x³ + y³ = (x + y)(x² - xy + y²)", "x³ - y³ = (x - y)(x² + xy + y²)"),
                        FormulaItem("Conditional Cubic Identity", "a + b + c = 0 ⇒ a³ + b³ + c³ = 3abc", "Shortcut for numerical cubes"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Without calculating cubes, find the value of (-12)³ + 7³ + 5³.",
                        steps = listOf(
                            "Let a = -12, b = 7, c = 5.",
                            "Check sum: a + b + c = -12 + 7 + 5 = 0.",
                            "Since a + b + c = 0, a³ + b³ + c³ = 3abc = 3 × (-12) × 7 × 5 = -1260.",
                        ),
                        answer = "-1260",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_m1_q1",
                            prompt = "Find the zero of the polynomial p(x) = 3x - 9.",
                            options = listOf("-3", "3", "9", "1/3"),
                            correctIndex = 1,
                            explanation = "Set 3x - 9 = 0 ⇒ 3x = 9 ⇒ x = 3.",
                        ),
                        QuizQuestion(
                            id = "c9_m1_q2",
                            prompt = "If (x - 2) is a factor of x² - kx + 6, find k.",
                            options = listOf("3", "5", "-5", "6"),
                            correctIndex = 1,
                            explanation = "By Factor Theorem, p(2) = 0 ⇒ 4 - 2k + 6 = 0 ⇒ 2k = 10 ⇒ k = 5.",
                        ),
                        QuizQuestion(
                            id = "c9_m1_q3",
                            prompt = "What is the degree of a non-zero constant polynomial like p(x) = 7?",
                            options = listOf("1", "7", "0", "Not defined"),
                            correctIndex = 2,
                            explanation = "7 = 7x⁰, so the degree of any non-zero constant polynomial is 0.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c9_math_ch2",
                    classLevel = 9,
                    subjectId = "c9_math",
                    subjectName = "Mathematics",
                    number = 2,
                    title = "Heron's Formula & Surface Areas",
                    estimatedMinutes = 25,
                    weightage = "10 Marks",
                    summary = "Compute triangle areas from three side lengths and surface area/volume of cones and spheres.",
                    keyPoints = listOf(
                        "Heron's formula computes triangle area directly from sides a, b, c without needing height.",
                        "Semi-perimeter s = (a + b + c) / 2.",
                        "Slant height of a right circular cone: l = √(r² + h²).",
                    ),
                    formulas = listOf(
                        FormulaItem("Heron's Formula", "Area = √[s(s - a)(s - b)(s - c)]", "s = (a + b + c) / 2"),
                        FormulaItem("Cone Volume & CSA", "V = (1/3)πr²h,  CSA = πrl", "l = √(r² + h²)"),
                        FormulaItem("Sphere & Hemisphere", "V_sphere = (4/3)πr³,  TSA_hemi = 3πr²", "CSA_hemi = 2πr²"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Find the area of a triangle with sides 13 cm, 14 cm, and 15 cm.",
                        steps = listOf(
                            "Semi-perimeter s = (13 + 14 + 15) / 2 = 21 cm.",
                            "Area = √[21 × (21 - 13) × (21 - 14) × (21 - 15)] = √(21 × 8 × 7 × 6).",
                            "Factorize: √(7 × 3 × 8 × 7 × 2 × 3) = 7 × 3 × 4 = 84 cm².",
                        ),
                        answer = "84 cm²",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_m2_q1",
                            prompt = "What is the slant height of a cone of radius 3 cm and height 4 cm?",
                            options = listOf("5 cm", "7 cm", "12 cm", "6 cm"),
                            correctIndex = 0,
                            explanation = "l = √(3² + 4²) = √(9 + 16) = √25 = 5 cm.",
                        ),
                        QuizQuestion(
                            id = "c9_m2_q2",
                            prompt = "Total surface area of a solid hemisphere of radius r is:",
                            options = listOf("2πr²", "3πr²", "4πr²", "πr²"),
                            correctIndex = 1,
                            explanation = "Curved area (2πr²) + flat circular base (πr²) = 3πr².",
                        ),
                        QuizQuestion(
                            id = "c9_m2_q3",
                            prompt = "Area of an equilateral triangle with side 4 cm is:",
                            options = listOf("4√3 cm²", "8√3 cm²", "16√3 cm²", "2√3 cm²"),
                            correctIndex = 0,
                            explanation = "Area = (√3 / 4) × a² = (√3 / 4) × 16 = 4√3 cm².",
                        ),
                    ),
                ),
            ),
        ),
        SubjectData(
            id = "c9_sci",
            classLevel = 9,
            streams = generalStreams,
            name = "Science",
            subtitle = "Motion, Newton's Laws, Atoms & Cell",
            category = SubjectCategory.SCIENCE,
            chapters = listOf(
                ChapterData(
                    id = "c9_sci_ch1",
                    classLevel = 9,
                    subjectId = "c9_sci",
                    subjectName = "Science",
                    number = 1,
                    title = "Motion & Laws of Motion",
                    estimatedMinutes = 30,
                    weightage = "12 Marks",
                    summary = "Master the 3 equations of uniformly accelerated motion, Newton's three laws, and conservation of momentum.",
                    keyPoints = listOf(
                        "Displacement is the shortest vector between initial and final position; distance is total scalar path length.",
                        "Area under a velocity-time graph gives displacement; its slope gives acceleration.",
                        "Newton's 2nd Law: F = ma (1 Newton = 1 kg·m/s²).",
                        "Total momentum of an isolated system remains conserved before and after collision.",
                    ),
                    formulas = listOf(
                        FormulaItem("Equations of Motion", "v = u + at  |  s = ut + ½at²  |  v² - u² = 2as", "Valid for uniform acceleration a"),
                        FormulaItem("Force & Momentum", "p = mv,   F = ma = (mv - mu) / t", "SI unit of p is kg·m/s"),
                        FormulaItem("Gravitation", "F = G(m₁m₂) / r²,   g = 9.8 m/s²", "G = 6.67 × 10⁻¹¹ N·m²/kg²"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "A train starting from rest attains a velocity of 72 km/h in 5 minutes. Find its acceleration.",
                        steps = listOf(
                            "u = 0 m/s, v = 72 × (5/18) = 20 m/s, t = 5 × 60 = 300 s.",
                            "a = (v - u) / t = (20 - 0) / 300 = 1/15 m/s² ≈ 0.067 m/s².",
                        ),
                        answer = "1/15 m/s² (≈ 0.067 m/s²)",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_s1_q1",
                            prompt = "The slope of a velocity-time graph represents:",
                            options = listOf("Distance", "Displacement", "Acceleration", "Momentum"),
                            correctIndex = 2,
                            explanation = "Slope = Δv / Δt, which is the definition of acceleration.",
                        ),
                        QuizQuestion(
                            id = "c9_s1_q2",
                            prompt = "A 2 kg object accelerates at 4 m/s². The net force acting on it is:",
                            options = listOf("2 N", "6 N", "8 N", "0.5 N"),
                            correctIndex = 2,
                            explanation = "F = m × a = 2 kg × 4 m/s² = 8 N.",
                        ),
                        QuizQuestion(
                            id = "c9_s1_q3",
                            prompt = "Inertia of a body depends directly on its:",
                            options = listOf("Velocity", "Mass", "Volume", "Shape"),
                            correctIndex = 1,
                            explanation = "Mass is the quantitative measure of inertia — heavier objects resist state change more.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c9_sci_ch2",
                    classLevel = 9,
                    subjectId = "c9_sci",
                    subjectName = "Science",
                    number = 2,
                    title = "Atoms, Molecules & The Cell",
                    estimatedMinutes = 25,
                    weightage = "11 Marks",
                    summary = "Understand chemical formulas, molecular mass, cell organelles (mitochondria, Golgi, ER), and osmosis.",
                    keyPoints = listOf(
                        "Law of Conservation of Mass: mass can neither be created nor destroyed in a chemical reaction.",
                        "Mitochondria are the powerhouse of the cell, generating ATP.",
                        "Lysosomes contain digestive enzymes and are known as suicidal bags of the cell.",
                        "Plant cells possess a cellulose cell wall and plastids (chloroplasts) absent in animal cells.",
                    ),
                    formulas = listOf(
                        FormulaItem("Number of Moles", "n = Given Mass (m) / Molar Mass (M)", "1 mole = 6.022 × 10²³ particles"),
                        FormulaItem("Atomic Mass (H₂O)", "2(1) + 16 = 18 u", "H=1 u, C=12 u, N=14 u, O=16 u"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Calculate the molecular mass of Carbon Dioxide (CO₂).",
                        steps = listOf(
                            "Atomic mass of Carbon (C) = 12 u; Oxygen (O) = 16 u.",
                            "Molecular mass of CO₂ = 12 + 2 × 16 = 12 + 32 = 44 u.",
                        ),
                        answer = "44 u",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_s2_q1",
                            prompt = "Which organelle is called the powerhouse of the cell?",
                            options = listOf("Ribosome", "Mitochondria", "Golgi apparatus", "Vacuole"),
                            correctIndex = 1,
                            explanation = "Mitochondria release cellular energy in the form of ATP molecules.",
                        ),
                        QuizQuestion(
                            id = "c9_s2_q2",
                            prompt = "What is the chemical formula of Calcium Carbonate?",
                            options = listOf("CaO", "Ca(OH)₂", "CaCO₃", "CaCl₂"),
                            correctIndex = 2,
                            explanation = "Ca²⁺ and CO₃²⁻ combine in a 1:1 ratio to form CaCO₃.",
                        ),
                        QuizQuestion(
                            id = "c9_s2_q3",
                            prompt = "What is the molecular mass of water (H₂O)?",
                            options = listOf("16 u", "17 u", "18 u", "20 u"),
                            correctIndex = 2,
                            explanation = "2 × 1 u (H) + 16 u (O) = 18 u.",
                        ),
                    ),
                ),
            ),
        ),
        SubjectData(
            id = "c9_sst",
            classLevel = 9,
            streams = generalStreams,
            name = "Social Science",
            subtitle = "French Revolution, India Drainage & Democracy",
            category = SubjectCategory.SOCIAL,
            chapters = listOf(
                ChapterData(
                    id = "c9_sst_ch1",
                    classLevel = 9,
                    subjectId = "c9_sst",
                    subjectName = "Social Science",
                    number = 1,
                    title = "The French Revolution & Indian Geography",
                    estimatedMinutes = 22,
                    weightage = "10 Marks",
                    summary = "Trace the fall of Bastille (1789), estates system, Liberty-Equality-Fraternity, and India's physiographic divisions.",
                    keyPoints = listOf(
                        "14 July 1789: Storming of the Bastille marked the outbreak of the French Revolution.",
                        "French society had Three Estates; only the Third Estate paid direct tax (Taille) and church tax (Tithe).",
                        "Tropic of Cancer (23°30'N) divides India into almost two equal halves; Standard Meridian is 82°30'E (Mirzapur).",
                    ),
                    formulas = listOf(
                        FormulaItem("India Standard Meridian", "82°30' E (IST = GMT + 5:30)", "Passes through Mirzapur, UP"),
                        FormulaItem("Key Revolution Milestones", "1789: Bastille | 1791: Constitution | 1792: Republic", "Declaration of Rights of Man"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Why does Arunachal Pradesh experience sunrise about 2 hours earlier than Gujarat?",
                        steps = listOf(
                            "India's longitudinal extent spans roughly 30° from Gujarat (68°7'E) to Arunachal Pradesh (97°25'E).",
                            "Earth rotates 15° per hour (4 minutes per degree).",
                            "30° × 4 minutes = 120 minutes = 2 hours time lag.",
                        ),
                        answer = "Due to a ~30° longitudinal difference (2 hours solar time lag).",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_st1_q1",
                            prompt = "On which date was the fortress-prison Bastille stormed?",
                            options = listOf("14 July 1789", "4 July 1776", "26 Jan 1789", "15 Aug 1791"),
                            correctIndex = 0,
                            explanation = "Parisians stormed the Bastille on 14 July 1789, now celebrated as France's national day.",
                        ),
                        QuizQuestion(
                            id = "c9_st1_q2",
                            prompt = "Which latitude divides India into nearly two equal parts?",
                            options = listOf("Equator", "Tropic of Capricorn", "Tropic of Cancer", "Arctic Circle"),
                            correctIndex = 2,
                            explanation = "The Tropic of Cancer (23°30'N) passes across eight Indian states.",
                        ),
                        QuizQuestion(
                            id = "c9_st1_q3",
                            prompt = "Who wrote 'The Social Contract'?",
                            options = listOf("Montesquieu", "Jean-Jacques Rousseau", "John Locke", "Voltaire"),
                            correctIndex = 1,
                            explanation = "Rousseau proposed a government based on a social contract between people and their representatives.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 10 (BOARD EXAM FOCUS) ====================
        SubjectData(
            id = "c10_math",
            classLevel = 10,
            streams = generalStreams,
            name = "Mathematics",
            subtitle = "Board Prep: Quadratics, Trigonometry & AP",
            category = SubjectCategory.MATH,
            chapters = listOf(
                ChapterData(
                    id = "c10_math_ch1",
                    classLevel = 10,
                    subjectId = "c10_math",
                    subjectName = "Mathematics",
                    number = 1,
                    title = "Quadratic Equations & Arithmetic Progressions",
                    estimatedMinutes = 30,
                    weightage = "12 Marks (Board High-Yield)",
                    summary = "Solve quadratic equations by discriminant formula and find nth terms and sums of Arithmetic Progressions.",
                    keyPoints = listOf(
                        "Standard form: ax² + bx + c = 0 (a ≠ 0). Discriminant D = b² - 4ac determines nature of roots.",
                        "D > 0 ⇒ two distinct real roots; D = 0 ⇒ two equal real roots; D < 0 ⇒ no real roots.",
                        "In an AP with first term 'a' and common difference 'd', each consecutive term differs by d.",
                    ),
                    formulas = listOf(
                        FormulaItem("Quadratic Formula", "x = (-b ± √(b² - 4ac)) / 2a", "D = b² - 4ac"),
                        FormulaItem("nth Term of AP", "aₙ = a + (n - 1)d", "Common difference d = a₂ - a₁"),
                        FormulaItem("Sum of n Terms of AP", "Sₙ = (n/2)[2a + (n - 1)d] = (n/2)(a + l)", "l is the last term"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Find the roots of 2x² - 7x + 3 = 0 using the quadratic formula.",
                        steps = listOf(
                            "Here a = 2, b = -7, c = 3.",
                            "Discriminant D = (-7)² - 4(2)(3) = 49 - 24 = 25 (√D = 5).",
                            "x = (7 ± 5) / 4 ⇒ x = 12/4 = 3 or x = 2/4 = 1/2.",
                        ),
                        answer = "x = 3 and x = 1/2",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_m1_q1",
                            prompt = "For what value of k does x² + kx + 9 = 0 have equal real roots?",
                            options = listOf("±3", "±6", "±9", "0"),
                            correctIndex = 1,
                            explanation = "Equal roots require D = 0 ⇒ k² - 4(1)(9) = 0 ⇒ k² = 36 ⇒ k = ±6.",
                        ),
                        QuizQuestion(
                            id = "c10_m1_q2",
                            prompt = "Find the 10th term of the AP: 2, 7, 12, ...",
                            options = listOf("45", "47", "52", "42"),
                            correctIndex = 1,
                            explanation = "a = 2, d = 5 ⇒ a₁₀ = 2 + (10 - 1)×5 = 2 + 45 = 47.",
                        ),
                        QuizQuestion(
                            id = "c10_m1_q3",
                            prompt = "Sum of first 20 natural numbers (1 + 2 + ... + 20) is:",
                            options = listOf("190", "200", "210", "220"),
                            correctIndex = 2,
                            explanation = "Sₙ = n(n + 1)/2 = 20 × 21 / 2 = 210.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c10_math_ch2",
                    classLevel = 10,
                    subjectId = "c10_math",
                    subjectName = "Mathematics",
                    number = 2,
                    title = "Introduction to Trigonometry",
                    estimatedMinutes = 28,
                    weightage = "12 Marks (Board High-Yield)",
                    summary = "Master trigonometric ratios, standard angles (0°, 30°, 45°, 60°, 90°), identities, and Heights & Distances.",
                    keyPoints = listOf(
                        "sin θ = P/H, cos θ = B/H, tan θ = P/B = sin θ / cos θ.",
                        "Memorize exact values: sin 30° = 1/2, sin 45° = 1/√2, sin 60° = √3/2, tan 45° = 1.",
                        "Angle of elevation is measured upwards from the horizontal; angle of depression downwards.",
                    ),
                    formulas = listOf(
                        FormulaItem("Pythagorean Identity 1", "sin²θ + cos²θ = 1", "sin²θ = 1 - cos²θ"),
                        FormulaItem("Pythagorean Identity 2", "1 + tan²θ = sec²θ", "sec²θ - tan²θ = 1"),
                        FormulaItem("Pythagorean Identity 3", "1 + cot²θ = cosec²θ", "cosec²θ - cot²θ = 1"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Evaluate: 2 tan²45° + cos²30° - sin²60°.",
                        steps = listOf(
                            "Substitute standard values: tan 45° = 1, cos 30° = √3/2, sin 60° = √3/2.",
                            "Expression = 2(1)² + (√3/2)² - (√3/2)² = 2 + 3/4 - 3/4.",
                        ),
                        answer = "2",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_m2_q1",
                            prompt = "What is the value of 9 sec²A - 9 tan²A?",
                            options = listOf("1", "9", "8", "0"),
                            correctIndex = 1,
                            explanation = "9(sec²A - tan²A) = 9 × 1 = 9.",
                        ),
                        QuizQuestion(
                            id = "c10_m2_q2",
                            prompt = "If sin A = 3/5, then cos A equals:",
                            options = listOf("4/5", "3/4", "5/4", "2/5"),
                            correctIndex = 0,
                            explanation = "cos A = √(1 - sin²A) = √(1 - 9/25) = √(16/25) = 4/5.",
                        ),
                        QuizQuestion(
                            id = "c10_m2_q3",
                            prompt = "A vertical pole of height 10 m casts a shadow of 10 m. The Sun's angle of elevation is:",
                            options = listOf("30°", "45°", "60°", "90°"),
                            correctIndex = 1,
                            explanation = "tan θ = height / shadow = 10 / 10 = 1 ⇒ θ = 45°.",
                        ),
                    ),
                ),
            ),
        ),
        SubjectData(
            id = "c10_sci",
            classLevel = 10,
            streams = generalStreams,
            name = "Science",
            subtitle = "Electricity, Optics, Reactions & Life Processes",
            category = SubjectCategory.SCIENCE,
            chapters = listOf(
                ChapterData(
                    id = "c10_sci_ch1",
                    classLevel = 10,
                    subjectId = "c10_sci",
                    subjectName = "Science",
                    number = 1,
                    title = "Electricity & Ohm's Law",
                    estimatedMinutes = 30,
                    weightage = "13 Marks (Board High-Yield)",
                    summary = "Master current, potential difference, Ohm's Law, series/parallel resistors, Joule's heating, and electric power.",
                    keyPoints = listOf(
                        "Ohm's Law: At constant temperature, V ∝ I ⇒ V = IR (Resistance unit: Ohm Ω).",
                        "In Series: same current flows through all resistors, R_s = R₁ + R₂ + R₃.",
                        "In Parallel: same voltage across branches, 1/R_p = 1/R₁ + 1/R₂ + 1/R₃; household wiring is parallel.",
                        "1 kWh (commercial unit of electrical energy) = 3.6 × 10⁶ Joules.",
                    ),
                    formulas = listOf(
                        FormulaItem("Ohm's Law & Resistivity", "V = IR,   R = ρ(l / A)", "ρ = resistivity (Ω·m)"),
                        FormulaItem("Electric Power", "P = VI = I²R = V² / R", "SI unit: Watt (W)"),
                        FormulaItem("Joule's Law of Heating", "H = I²Rt", "Used in fuses, irons, heaters"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Two resistors of 6 Ω and 3 Ω are connected in parallel across a 12 V battery. Find total current.",
                        steps = listOf(
                            "1/R_p = 1/6 + 1/3 = 3/6 = 1/2 ⇒ R_p = 2 Ω.",
                            "Total current I = V / R_p = 12 V / 2 Ω = 6 A.",
                        ),
                        answer = "6 A",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_s1_q1",
                            prompt = "What is the equivalent resistance of two 10 Ω resistors connected in parallel?",
                            options = listOf("20 Ω", "10 Ω", "5 Ω", "2.5 Ω"),
                            correctIndex = 2,
                            explanation = "1/R_p = 1/10 + 1/10 = 2/10 ⇒ R_p = 5 Ω.",
                        ),
                        QuizQuestion(
                            id = "c10_s1_q2",
                            prompt = "1 kilowatt-hour (1 kWh) is equal to:",
                            options = listOf("3.6 × 10³ J", "3.6 × 10⁶ J", "1000 J", "3600 J"),
                            correctIndex = 1,
                            explanation = "1 kWh = 1000 W × 3600 s = 3.6 × 10⁶ J.",
                        ),
                        QuizQuestion(
                            id = "c10_s1_q3",
                            prompt = "Which metal is used for the filament of electric bulbs due to its high melting point?",
                            options = listOf("Copper", "Aluminium", "Tungsten", "Nichrome"),
                            correctIndex = 2,
                            explanation = "Tungsten has a very high melting point (~3380°C) and glows brightly without melting.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c10_sci_ch2",
                    classLevel = 10,
                    subjectId = "c10_sci",
                    subjectName = "Science",
                    number = 2,
                    title = "Light: Reflection, Refraction & Chemical Reactions",
                    estimatedMinutes = 28,
                    weightage = "12 Marks",
                    summary = "Apply Mirror and Lens formulas, magnification, Snell's Law, and balance redox & displacement reactions.",
                    keyPoints = listOf(
                        "New Cartesian Sign Convention: distances measured opposite to incident light are negative; focal length of concave mirror is negative.",
                        "Convex mirrors always form virtual, erect, diminished images — ideal as rear-view mirrors.",
                        "Power of a lens P = 1 / f(in meters), measured in Dioptre (D); convex is +D, concave is -D.",
                        "Oxidation is gain of oxygen / loss of electrons; Reduction is loss of oxygen / gain of electrons.",
                    ),
                    formulas = listOf(
                        FormulaItem("Mirror Formula", "1/f = 1/v + 1/u,   m = -v/u", "f = R / 2"),
                        FormulaItem("Lens Formula & Power", "1/f = 1/v - 1/u,   P = 1/f (m)", "m = +v/u for lens"),
                        FormulaItem("Snell's Law", "n = sin i / sin r = c / v", "c = 3 × 10⁸ m/s"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Find the power of a concave lens of focal length 50 cm.",
                        steps = listOf(
                            "Concave lens has negative focal length: f = -50 cm = -0.5 m.",
                            "P = 1 / f = 1 / (-0.5) = -2 D.",
                        ),
                        answer = "-2.0 Dioptre",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_s2_q1",
                            prompt = "Which mirror is used by dentists to see an enlarged image of teeth?",
                            options = listOf("Plane mirror", "Convex mirror", "Concave mirror", "Cylindrical mirror"),
                            correctIndex = 2,
                            explanation = "A concave mirror forms a magnified, erect virtual image when the object is between P and F.",
                        ),
                        QuizQuestion(
                            id = "c10_s2_q2",
                            prompt = "A doctor prescribes a corrective lens of power +2.5 D. Its focal length is:",
                            options = listOf("+40 cm", "-40 cm", "+25 cm", "-25 cm"),
                            correctIndex = 0,
                            explanation = "f = 1 / 2.5 m = 0.4 m = +40 cm (convex lens).",
                        ),
                        QuizQuestion(
                            id = "c10_s2_q3",
                            prompt = "In the reaction Zn + CuSO₄ → ZnSO₄ + Cu, which reaction type occurs?",
                            options = listOf("Combination", "Decomposition", "Displacement", "Double displacement"),
                            correctIndex = 2,
                            explanation = "More reactive Zinc displaces Copper from Copper Sulphate solution.",
                        ),
                    ),
                ),
            ),
        ),
        SubjectData(
            id = "c10_sst",
            classLevel = 10,
            streams = generalStreams,
            name = "Social Science",
            subtitle = "Nationalism in India, Federalism & Sectors",
            category = SubjectCategory.SOCIAL,
            chapters = listOf(
                ChapterData(
                    id = "c10_sst_ch1",
                    classLevel = 10,
                    subjectId = "c10_sst",
                    subjectName = "Social Science",
                    number = 1,
                    title = "Nationalism in India & Sectors of Economy",
                    estimatedMinutes = 24,
                    weightage = "12 Marks (Board High-Yield)",
                    summary = "Review Satyagraha movements, Non-Cooperation, Dandi March, and Primary/Secondary/Tertiary economic sectors.",
                    keyPoints = listOf(
                        "Champaran (1917), Kheda (1917), and Ahmedabad (1918) were Mahatma Gandhi's early Satyagraha movements.",
                        "Dandi Salt March began on 12 March 1930 from Sabarmati Ashram (240 miles) launching Civil Disobedience.",
                        "GDP sums the value of all final goods & services produced within a country during a year.",
                        "Primary = agriculture/mining; Secondary = manufacturing; Tertiary = services (largest GDP contributor in India).",
                    ),
                    formulas = listOf(
                        FormulaItem("Key Freedom Timeline", "1919: Rowlatt • 1920: NCM • 1930: Dandi • 1942: Quit India", "High-frequency Board Map & MCQ dates"),
                        FormulaItem("Per Capita Income", "PCI = Total National Income / Total Population", "Used by World Bank to compare countries"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Why are only 'final goods' counted when calculating GDP?",
                        steps = listOf(
                            "Intermediate goods (like wheat or flour used to make biscuits) already have their value embedded inside the final product price.",
                            "Counting intermediate goods separately would cause double counting and inflate GDP artificially.",
                        ),
                        answer = "To avoid double counting of raw material values.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_st1_q1",
                            prompt = "Why did Mahatma Gandhi call off the Non-Cooperation Movement in Feb 1922?",
                            options = listOf("Rowlatt Act", "Chauri Chaura incident", "Poona Pact", "Simon Commission"),
                            correctIndex = 1,
                            explanation = "Violence broke out at Chauri Chaura in Gorakhpur, prompting Gandhiji to withdraw the movement.",
                        ),
                        QuizQuestion(
                            id = "c10_st1_q2",
                            prompt = "Banking, transport, and software services belong to which sector?",
                            options = listOf("Primary", "Secondary", "Tertiary", "Quaternary only"),
                            correctIndex = 2,
                            explanation = "Activities that support production and provide services fall in the Tertiary sector.",
                        ),
                        QuizQuestion(
                            id = "c10_st1_q3",
                            prompt = "In which year did the Poona Pact take place?",
                            options = listOf("1928", "1930", "1932", "1935"),
                            correctIndex = 2,
                            explanation = "The Poona Pact between Dr. B.R. Ambedkar and Mahatma Gandhi was signed in September 1932.",
                        ),
                    ),
                ),
            ),
        ),
        SubjectData(
            id = "c10_eng",
            classLevel = 10,
            streams = generalStreams,
            name = "English",
            subtitle = "Board Grammar, Formal Letters & Literature",
            category = SubjectCategory.ENGLISH,
            chapters = listOf(
                ChapterData(
                    id = "c10_eng_ch1",
                    classLevel = 10,
                    subjectId = "c10_eng",
                    subjectName = "English",
                    number = 1,
                    title = "Reported Speech, Modals & Analytical Paragraph",
                    estimatedMinutes = 20,
                    weightage = "10 Marks",
                    summary = "Ace CBSE Board integrated grammar (narration, determiners, modals) and 5-mark formal letter formatting.",
                    keyPoints = listOf(
                        "When reporting verb is in past tense, present tense inside quotes shifts one step back (Simple Present → Simple Past).",
                        "Universal truths never change tense in indirect speech.",
                        "Formal Letter format: Sender's Address → Date → Receiver's Address → Subject → Salutation → Body (3 paras) → Complimentary Close.",
                    ),
                    formulas = listOf(
                        FormulaItem("Narration Shift", "said to → told | says to → tells | ? → asked", "this→that, now→then, tomorrow→the next day"),
                        FormulaItem("Modal Usage", "must (obligation) • should (advice) • might (weak possibility)", "Modals are followed by base verb V1"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Convert to Indirect Speech: Rohan said to Meera, 'I am preparing for my board exams.'",
                        steps = listOf(
                            "Change 'said to' to 'told', remove quotes and add 'that'.",
                            "Change pronoun 'I' → 'he', 'my' → 'his', and 'am preparing' → 'was preparing'.",
                        ),
                        answer = "Rohan told Meera that he was preparing for his board exams.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_e1_q1",
                            prompt = "Convert: The teacher said, 'The Earth revolves around the Sun.'",
                            options = listOf(
                                "The teacher said that the Earth revolved around the Sun.",
                                "The teacher said that the Earth revolves around the Sun.",
                                "The teacher told the Earth is revolving around the Sun.",
                                "The teacher asked if the Earth revolves around the Sun.",
                            ),
                            correctIndex = 1,
                            explanation = "Universal scientific truths retain Simple Present tense in indirect speech.",
                        ),
                        QuizQuestion(
                            id = "c10_e1_q2",
                            prompt = "Each of the students _____ received a merit certificate.",
                            options = listOf("have", "has", "were", "are"),
                            correctIndex = 1,
                            explanation = "'Each of + plural noun' always takes a singular verb ('has').",
                        ),
                        QuizQuestion(
                            id = "c10_e1_q3",
                            prompt = "Students _____ carry their admit cards to the examination hall (mandatory).",
                            options = listOf("might", "could", "must", "may"),
                            correctIndex = 2,
                            explanation = "'Must' expresses strict obligation or mandatory rule.",
                        ),
                    ),
                ),
            ),
        ),
    )
}
