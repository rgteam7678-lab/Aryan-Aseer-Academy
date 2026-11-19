package com.example.data.curriculum

import com.example.data.model.AcademicStream
import com.example.data.model.ChapterData
import com.example.data.model.FormulaItem
import com.example.data.model.QuizQuestion
import com.example.data.model.SolvedExample
import com.example.data.model.SubjectCategory
import com.example.data.model.SubjectData

internal object CurriculumUrduAll {

    private val allStreams = setOf(
        AcademicStream.GENERAL,
        AcademicStream.SCIENCE,
        AcademicStream.COMMERCE,
        AcademicStream.ARTS,
    )

    val subjects: List<SubjectData> = listOf(
        // ==================== CLASS 7 URDU ====================
        SubjectData(
            id = "c7_urdu",
            classLevel = 7,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Apni Zaban: Hamd, Qawaid, Muhavre & Mazmoon",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c7_urdu_ch1",
                    classLevel = 7,
                    subjectId = "c7_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Hamd aur Naat (حمد و نعت) — Central Idea",
                    estimatedMinutes = 18,
                    weightage = "10 Marks",
                    summary = "Understand the difference between Hamd (praise of Allah) and Naat (praise of Prophet Muhammad SAW) with couplet explanations.",
                    keyPoints = listOf(
                        "Hamd (حمد): Wo nazm jisme Khuda (Allah Ta'ala) ki tareef aur qudrat ka bayan ho.",
                        "Naat (نعت): Wo nazm jisme Hazrat Muhammad (SAW) ki tareef, seerat aur akhlaq ka zikr ho.",
                        "Nazm ka Markazi Khayal (Central Idea) 4 se 5 mukhtasar aur saaf jumlon mein likha jata hai.",
                        "Ash'aar (اشعار) ki tashreeh karte waqt shayar ka naam aur nazm ka unwan zaroor likhein.",
                    ),
                    formulas = listOf(
                        FormulaItem("Hamd vs Naat", "حمد = اللہ کی تعریف  |  نعت = نبی اکرم ﷺ کی تعریف", "Two foundational Urdu poetic genres"),
                        FormulaItem("Tashreeh Format", "حوالہ (Reference) → سیاق و سباق → تشریح (Explanation)", "Standard full-marks exam structure"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Hamd aur Naat mein kya buniyadi farq hai? Mukhtasar jawab likhein.",
                        steps = listOf(
                            "Pehle Hamd ki tareef likhein: Jis nazm mein Allah Ta'ala ki hamd-o-sana aur qudrat bayan ki jaye use Hamd kehte hain.",
                            "Phir Naat ki tareef likhein: Jis nazm mein Huzoor Pak (SAW) ki tareef bayan ki jaye use Naat kehte hain.",
                        ),
                        answer = "Hamd Allah ki tareef ki nazm hai, jabke Naat Hazrat Muhammad (SAW) ki tareef ki nazm hai.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c7_u1_q1",
                            prompt = "Jis nazm mein Allah Ta'ala ki tareef bayan ki jaye use kya kehte hain?",
                            options = listOf("Naat (نعت)", "Hamd (حمد)", "Qasida (قصیدہ)", "Marsiya (مرثیہ)"),
                            correctIndex = 1,
                            explanation = "Hamd (حمد) wo nazm hai jisme Allah Ta'ala ki tareef aur shaan bayan ki jati hai.",
                        ),
                        QuizQuestion(
                            id = "c7_u1_q2",
                            prompt = "Naat (نعت) kis ki tareef mein likhi jati hai?",
                            options = listOf("Badshah ki", "Hazrat Muhammad (SAW) ki", "Mausam ki", "Watan ki"),
                            correctIndex = 1,
                            explanation = "Naat Huzoor Akram (SAW) ki seerat aur tareef mein likhi jati hai.",
                        ),
                        QuizQuestion(
                            id = "c7_u1_q3",
                            prompt = "Shair (شعر) ke ek line (satar) ko kya kehte hain?",
                            options = listOf("Misra (مصرع)", "Band (بند)", "Qita (قطعہ)", "Khulasa"),
                            correctIndex = 0,
                            explanation = "Ek shair do misron (دو مصرعوں) se mil kar banta hai.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c7_urdu_ch2",
                    classLevel = 7,
                    subjectId = "c7_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Urdu Qawaid (اردو قواعد): Ism, Zameer, Sifat, Fail",
                    estimatedMinutes = 22,
                    weightage = "12 Marks",
                    summary = "Master the four core parts of speech in Urdu grammar: Ism (Noun), Zameer (Pronoun), Sifat (Adjective), and Fail (Verb).",
                    keyPoints = listOf(
                        "Ism (اسم): Kisi shakhs, jagah ya cheez ka naam (maslan: Delhi, Kitab, Aryan). Ism-e-Marfa (Proper) aur Ism-e-Nakira (Common).",
                        "Zameer (ضمیر): Wo lafz jo ism ki jagah istemal ho (maslan: main, tum, wo, hum).",
                        "Sifat (صفت): Jo kisi ism ki khubi, khami ya ta'dad bataye (maslan: nek larka, meetha aam, surkh gulab).",
                        "Fail (فعل): Wo kalma jisse kisi kaam ka karna ya hona zahir ho (maslan: parhna, likhna, daurna).",
                    ),
                    formulas = listOf(
                        FormulaItem("Kalma ki Qismein", "اسم (Noun) • ضمیر (Pronoun) • صفت (Adjective) • فعل (Verb)", "Core Urdu Parts of Speech"),
                        FormulaItem("Ism-e-Marfa vs Nakira", "معرفہ (خاص نام: دہلی، گنگا)  |  نکرہ (عام نام: شہر، دریا)", "Proper vs Common Noun"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Jumle mein Ism, Sifat aur Fail alag karein: 'Aryan ne nayi kitab parhi.'",
                        steps = listOf(
                            "Ism (اسم): Aryan (Ism-e-Marfa), kitab (Ism-e-Nakira).",
                            "Sifat (صفت): nayi (kitab ki khubi bata rahi hai).",
                            "Fail (فعل): parhi (kaam ka hona zahir karta hai).",
                        ),
                        answer = "Ism: Aryan, kitab | Sifat: nayi | Fail: parhi",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c7_u2_q1",
                            prompt = "Ism ki jagah istemal hone wale lafz ko kya kehte hain?",
                            options = listOf("Sifat (صفت)", "Zameer (ضمیر)", "Fail (فعل)", "Harf (حرف)"),
                            correctIndex = 1,
                            explanation = "Zameer (ضمیر) ism ki jagah aata hai, jaise 'wo', 'main', 'tum'.",
                        ),
                        QuizQuestion(
                            id = "c7_u2_q2",
                            prompt = "'Taj Mahal' qawaid ke lehaz se kaunsa Ism hai?",
                            options = listOf("Ism-e-Nakira", "Ism-e-Marfa (خاص)", "Fail", "Sifat"),
                            correctIndex = 1,
                            explanation = "Taj Mahal ek khas imarat ka naam hai, isliye ye Ism-e-Marfa hai.",
                        ),
                        QuizQuestion(
                            id = "c7_u2_q3",
                            prompt = "'Thanda pani' mein lafz 'thanda' kya hai?",
                            options = listOf("Fail", "Zameer", "Sifat (صفت)", "Mausoof"),
                            correctIndex = 2,
                            explanation = "'Thanda' pani ki khasusiyat (quality) batata hai, isliye Sifat hai.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c7_urdu_ch3",
                    classLevel = 7,
                    subjectId = "c7_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 3,
                    title = "Wahid-Jama, Mutazad, Muhavre aur Darkhwast",
                    estimatedMinutes = 20,
                    weightage = "12 Marks",
                    summary = "Build strong Urdu vocabulary with singular-plural (واحد جمع), antonyms (متضاد), idioms (محاورے), and school application writing.",
                    keyPoints = listOf(
                        "Wahid-Jama: Kitab → Kutub (کتاب → کتب), Waqt → Auqat (وقت → اوقات), Shair → Ash'aar (شعر → اشعار), Ilm → Uloom (علم → علوم).",
                        "Mutazad (Antonyms): Subah × Shaam, Roshni × Andhera, Azaadi × Ghulami, Ilm × Jahalat.",
                        "Muhavre (Idioms): Aankh ka tara (bahut pyara), Nau do gyarah hona (bhag jana), Kaan bharna (chughli karna).",
                        "Darkhwast (درخواست) format: Ba-khidmat Janab Principal Sahab → Unwan → Janab-e-Aali → Asl Mazmoon → Aap ka farmanbardar shagird.",
                    ),
                    formulas = listOf(
                        FormulaItem("Important Wahid-Jama", "کتاب/کتب • علم/علوم • وقت/اوقات • قلم/اقلام • قوم/اقوام", "High-frequency Class 7 exam pairs"),
                        FormulaItem("Darkhwast Opening", "بخدمت جناب پرنسپل صاحب ← عنوان ← جنابِ عالی!", "Always include Date & Class at bottom"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Muhavre 'Aankh ka tara hona' ka matlab likhein aur jumle mein istemal karein.",
                        steps = listOf(
                            "Matlab (Meaning): Bahut pyara hona.",
                            "Jumla (Sentence): Har mehnati baccha apne walidain ki aankh ka tara hota hai.",
                        ),
                        answer = "Matlab: Bahut pyara hona.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c7_u3_q1",
                            prompt = "Lafz 'Ilm' (علم) ki sahi Jama (Plural) kya hai?",
                            options = listOf("Ilmein", "Uloom (علوم)", "Alim", "Ilmiyat"),
                            correctIndex = 1,
                            explanation = "'Ilm' ki jama 'Uloom' (علوم) hoti hai.",
                        ),
                        QuizQuestion(
                            id = "c7_u3_q2",
                            prompt = "'Nau do gyarah hona' muhavre ka sahi matlab kya hai?",
                            options = listOf("Ginti karna", "Bhag jana", "Khush hona", "Madad karna"),
                            correctIndex = 1,
                            explanation = "'Nau do gyarah hona' ka matlab hai foran bhag jana.",
                        ),
                        QuizQuestion(
                            id = "c7_u3_q3",
                            prompt = "'Azaadi' (آزادی) ka mutazad (opposite) kya hai?",
                            options = listOf("Khushi", "Ghulami (غلامی)", "Kamyabi", "Bahaaduri"),
                            correctIndex = 1,
                            explanation = "Azaadi ka mutazad Ghulami hota hai.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 8 URDU ====================
        SubjectData(
            id = "c8_urdu",
            classLevel = 8,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Jaan Pehchan: Zamane, Rubai, Khat & Mazmoon",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c8_urdu_ch1",
                    classLevel = 8,
                    subjectId = "c8_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Fail aur Zamane (فعل اور زمانے: ماضی، حال، مستقبل)",
                    estimatedMinutes = 22,
                    weightage = "12 Marks",
                    summary = "Master the three Urdu tenses — Maazi (Past), Haal (Present), and Mustaqbil (Future) — along with Fail-e-Maroof and Majhool.",
                    keyPoints = listOf(
                        "Fail-e-Maazi (فعلِ ماضی): Guzre hue zamane mein hone wala kaam (maslan: Usne khat likha).",
                        "Fail-e-Haal (فعلِ حال): Maujuda zamane mein hone wala kaam (maslan: Wo sabaq parh raha hai).",
                        "Fail-e-Mustaqbil (فعلِ مستقبل): Aane wale zamane mein hone wala kaam (maslan: Hum kal imtehan denge).",
                        "Fail-e-Amr (حکم: Kitab parho!) aur Fail-e-Nahi (ممانعت: Jhoot mat bolo!).",
                    ),
                    formulas = listOf(
                        FormulaItem("3 Zamane (Tenses)", "ماضی (گیا وقت) • حال (موجودہ وقت) • مستقبل (آنے والا وقت)", "Identify by ending: تھا / ہے / گا"),
                        FormulaItem("Amr vs Nahi", "فعلِ امر = حکم دینا (آؤ، پڑھو)  |  فعلِ نہی = منع کرنا (مت جاؤ)", "Imperative affirmative vs negative"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Jumle 'Wo school jata hai' ko Maazi aur Mustaqbil mein tabdeel karein.",
                        steps = listOf(
                            "Maazi (Past): Wo school gaya (ya: Wo school jata tha).",
                            "Mustaqbil (Future): Wo school jayega.",
                        ),
                        answer = "Maazi: Wo school gaya | Mustaqbil: Wo school jayega",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c8_u1_q1",
                            prompt = "'Hum kal Delhi jayenge' kis zamane ka jumla hai?",
                            options = listOf("Fail-e-Maazi", "Fail-e-Haal", "Fail-e-Mustaqbil (مستقبل)", "Fail-e-Amr"),
                            correctIndex = 2,
                            explanation = "'Jayenge' aane wale zamane (Future) ko zahir karta hai.",
                        ),
                        QuizQuestion(
                            id = "c8_u1_q2",
                            prompt = "'Shor mat machao!' qawaid mein kya kehlata hai?",
                            options = listOf("Fail-e-Nahi (نہی)", "Fail-e-Amr", "Ism-e-Sifat", "Fail-e-Maazi"),
                            correctIndex = 0,
                            explanation = "Jis jumle mein kisi kaam se roka jaye use Fail-e-Nahi kehte hain.",
                        ),
                        QuizQuestion(
                            id = "c8_u1_q3",
                            prompt = "Guzre hue zamane ko Urdu qawaid mein kya kehte hain?",
                            options = listOf("Haal", "Mustaqbil", "Maazi (ماضی)", "Hukm"),
                            correctIndex = 2,
                            explanation = "Past tense ko Urdu mein Maazi (ماضی) kaha jata hai.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c8_urdu_ch2",
                    classLevel = 8,
                    subjectId = "c8_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Rubai, Qita, Khat Naveesi aur Mazmoon (خط و مضمون)",
                    estimatedMinutes = 20,
                    weightage = "12 Marks",
                    summary = "Understand Rubai (4-line stanza), personal/formal letter structure (خط نویسی), and high-scoring essay writing.",
                    keyPoints = listOf(
                        "Rubai (رباعی) char misron (4 lines) ki nazm hoti hai jisme 1st, 2nd aur 4th misre hum-qafiya hote hain.",
                        "Khat (خط) ke 5 hisse hote hain: Pata aur Tareekh, Alqab-o-Aadaab, Nafs-e-Mazmoon, Ikhtitam, aur Khat Likhne Wale ka Naam.",
                        "Mazmoon (مضمون) ke 3 hisse: Tamheed (Intro), Nafs-e-Mazmoon (Main Body), aur Khatima (Conclusion).",
                    ),
                    formulas = listOf(
                        FormulaItem("Rubai Structure", "4 Misre (چار مصرعے) — Qafiya in lines 1, 2 & 4", "Famous Rubai poets: Mir Anis, Josh, Firaq"),
                        FormulaItem("Khat Structure", "مقام و تاریخ ← القاب و آداب ← نفسِ مضمون ← اختتام", "Use 'Azeez Dost' for friends, 'Mohtaram Walid' for father"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Rubai ke kin misron mein qafiya lana zaroori hota hai?",
                        steps = listOf(
                            "Rubai mein kul 4 misre (do shair) hote hain.",
                            "Pehle, doosre aur chauthe misre mein qafiya lazmi hota hai (teesra azaad ho sakta hai).",
                        ),
                        answer = "Pehle (1st), doosre (2nd) aur chauthe (4th) misre mein.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c8_u2_q1",
                            prompt = "Rubai (رباعی) mein kul kitne misre hote hain?",
                            options = listOf("2", "4", "6", "8"),
                            correctIndex = 1,
                            explanation = "Arabi lafz 'Ruba' ka matlab 4 hai; Rubai mein 4 misre hote hain.",
                        ),
                        QuizQuestion(
                            id = "c8_u2_q2",
                            prompt = "Mazmoon (Essay) ke shuruati hisse ko kya kehte hain?",
                            options = listOf("Khatima", "Tamheed (تمہید)", "Alqab", "Maqta"),
                            correctIndex = 1,
                            explanation = "Tamheed (Introduction) mazmoon ka aaghaz hota hai.",
                        ),
                        QuizQuestion(
                            id = "c8_u2_q3",
                            prompt = "Dost ko khat likhte waqt kaunsa Alqab (salutation) munasib hai?",
                            options = listOf("Janab-e-Aali", "Pyare Dost / Azeez Dost", "Ba-khidmat Principal", "Farmanbardar"),
                            correctIndex = 1,
                            explanation = "Ghair-rasmi (informal) khat mein dost ke liye 'Pyare Dost' ya 'Azeez Dost' likhte hain.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c8_urdu_ch3",
                    classLevel = 8,
                    subjectId = "c8_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 3,
                    title = "Sabiqe-Lahiqe aur Tazkeer-o-Tanees (سابقے، لاحقے، تذکیر و تانیث)",
                    estimatedMinutes = 20,
                    weightage = "10 Marks",
                    summary = "Master Urdu prefixes (Sabiqe), suffixes (Lahiqe), and masculine-feminine (Muzakkar-Muannas) rules for exams.",
                    keyPoints = listOf(
                        "Sabiqa (سابقہ — Prefix): Lafz ke shuru mein lag kar naya ma'ani banata hai (maslan: Ba-adab, Be-khauf, Khush-khabri, Na-kam).",
                        "Lahiqa (لاحقہ — Suffix): Lafz ke aakhir mein lagta hai (maslan: Dard-mand, Khidmat-gar, Watan-parast, Kutub-khana).",
                        "Tazkeer-o-Tanees: Muzakkar (مذکر — Masculine) aur Muannas (مؤنث — Feminine), jaise Ustad/Ustani, Shair/Shaira, Sher/Sherni.",
                    ),
                    formulas = listOf(
                        FormulaItem("Sabiqa (Prefix)", "با + ادب = باادب  |  بے + خوف = بے خوف  |  خوش + بو = خوشبو", "Added BEFORE root word"),
                        FormulaItem("Lahiqa (Suffix)", "کتب + خانہ = کتب خانہ  |  عقل + مند = عقلمند  |  باغ + بان = باغبان", "Added AFTER root word"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Lafz 'Be-' (بے) se do Sabiqe aur '-gar' (گار) se do Lahiqe banayein.",
                        steps = listOf(
                            "Sabiqe with 'Be-': Be-wafa (بے وفا), Be-gunah (بے گناہ).",
                            "Lahiqe with '-gar': Khidmat-gar (خدمت گار), Madad-gar (مددگار).",
                        ),
                        answer = "Sabiqe: Be-wafa, Be-gunah | Lahiqe: Khidmat-gar, Madad-gar",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c8_u3_q1",
                            prompt = "Lafz 'An-padh' (ان پڑھ) mein 'An' kya hai?",
                            options = listOf("Lahiqa", "Sabiqa (سابقہ)", "Mutazad", "Fail"),
                            correctIndex = 1,
                            explanation = "'An' lafz ke shuru mein juda hai, isliye Sabiqa (Prefix) hai.",
                        ),
                        QuizQuestion(
                            id = "c8_u3_q2",
                            prompt = "'Shair' (شاعر) ka Muannas (Feminine) kya hoga?",
                            options = listOf("Shairi", "Shaira (شاعرہ)", "Ash'aar", "Sher"),
                            correctIndex = 1,
                            explanation = "Shair (male poet) ka muannas Shaira (شاعرہ) hota hai.",
                        ),
                        QuizQuestion(
                            id = "c8_u3_q3",
                            prompt = "'Dawa-khana' mein '-khana' kya hai?",
                            options = listOf("Sabiqa", "Lahiqa (لاحقہ)", "Zameer", "Radif"),
                            correctIndex = 1,
                            explanation = "'-khana' lafz ke aakhir mein lag kar jagah zahir karta hai — ye Lahiqa hai.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 9 URDU ====================
        SubjectData(
            id = "c9_urdu",
            classLevel = 9,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Nawa-e-Urdu: Ghazal, Mir, Ghalib & Premchand",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c9_urdu_ch1",
                    classLevel = 9,
                    subjectId = "c9_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Ghazal ke Ajza: Matla, Maqta, Qafiya, Radif (غزل کے اجزاء)",
                    estimatedMinutes = 24,
                    weightage = "14 Marks",
                    summary = "Master the structural pillars of Urdu Ghazal — Matla, Husn-e-Matla, Maqta, Takhallus, Qafiya, and Radif.",
                    keyPoints = listOf(
                        "Matla (مطلع): Ghazal ka pehla shair jiske dono misre hum-qafiya aur hum-radif hote hain.",
                        "Maqta (مقطع): Ghazal ka aakhiri shair jisme shayar apna Takhallus (قلمی نام, jaise Ghalib, Mir) istemal karta hai.",
                        "Qafiya (قافیہ): Hum-awaz alfaz jo radif se pehle aate hain (jaise: dil, manzil, mushkil).",
                        "Radif (ردیف): Wo lafz ya alfaz jo har shair ke aakhir mein qafiya ke baad bilkul ek jaise dohraye jayein.",
                    ),
                    formulas = listOf(
                        FormulaItem("Ghazal Formula", "مطلع (1st) ... قافیہ (Rhyming word) + ردیف (Refrain) ... مقطع (Last + تخلص)", "Every couplet is thematically independent"),
                        FormulaItem("Bait-ul-Ghazal", "غزل کا سب سے بہترین شعر = بیت الغزل / شاہ بیت", "Also called Shah-Bait"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Shair 'Hasti apni hubab ki si hai / Ye numaish sarab ki si hai' mein Qafiya aur Radif pehchanein.",
                        steps = listOf(
                            "Dono misron ke aakhir mein 'ki si hai' (کی سی ہے) bilkul ek jaisa repeat ho raha hai → Radif.",
                            "Radif se theek pehle hum-awaz alfaz 'hubab' (حباب) aur 'sarab' (سراب) aaye hain → Qafiya.",
                        ),
                        answer = "Qafiya: hubab, sarab | Radif: ki si hai",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_u1_q1",
                            prompt = "Ghazal ke pehle shair ko jiske dono misre hum-qafiya hon, kya kehte hain?",
                            options = listOf("Maqta", "Matla (مطلع)", "Qita", "Band"),
                            correctIndex = 1,
                            explanation = "Matla ghazal ka aaghazi (pehla) shair hota hai.",
                        ),
                        QuizQuestion(
                            id = "c9_u1_q2",
                            prompt = "Shayar apna Takhallus (pen name) ghazal ke kis shair mein lata hai?",
                            options = listOf("Matla", "Maqta (مقطع)", "Radif", "Hamd"),
                            correctIndex = 1,
                            explanation = "Maqta wo aakhiri shair hai jisme shayar apna takhallus istemal karta hai.",
                        ),
                        QuizQuestion(
                            id = "c9_u1_q3",
                            prompt = "Ghazal ke sabse umda aur behtareen shair ko kya kehte hain?",
                            options = listOf("Bait-ul-Ghazal (بیت الغزل)", "Matla", "Rubai", "Qasida"),
                            correctIndex = 0,
                            explanation = "Sabse khubsurat shair ko Bait-ul-Ghazal ya Shah-Bait kehte hain.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c9_urdu_ch2",
                    classLevel = 9,
                    subjectId = "c9_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Mir Taqi Mir & Munshi Premchand — Adabi Khidmat",
                    estimatedMinutes = 24,
                    weightage = "12 Marks",
                    summary = "Study Khuda-e-Sukhan Mir Taqi Mir's soulful Ghazals and Premchand's realistic Urdu Afsana writing.",
                    keyPoints = listOf(
                        "Mir Taqi Mir (1723–1810, Agra) ko 'Khuda-e-Sukhan' (خداے سخن) kaha jata hai; unki shairi mein soz-o-gudaz aur sadgi hai.",
                        "Munshi Premchand (1880–1936, Banaras) Urdu afsane aur novel ke baani-e-awwal mein shumar hote hain.",
                        "Premchand ke mashhoor afsane aur novel: Idgah, Kafan, Poos ki Raat, Godan, Bazar-e-Husn.",
                        "Afsana (افسانہ) ek mukhtasar kahani hoti hai jo ek hi nashist (sitting) mein parhi ja sake aur wahdat-e-tasur rakhti ho.",
                    ),
                    formulas = listOf(
                        FormulaItem("Titles & Works", "میر تقی میر = خداے سخن  |  پریم چند = افسانہ نگاری (عیدگاہ، کفن، گودان)", "Key Class 9 NCERT exam facts"),
                        FormulaItem("Afsana Elements", "پلاٹ (Plot) • کردار (Characters) • مکالمہ (Dialogue) • وحدتِ تاثر", "Four pillars of an Urdu short story"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Mir Taqi Mir ko 'Khuda-e-Sukhan' kyun kaha jata hai?",
                        steps = listOf(
                            "Mir ne Urdu ghazal ko nihayat saaf, dil-nashin aur dard-bhari zaban di.",
                            "Ghalib aur Zauq jaise bade shayaron ne bhi Mir ki ustadi aur shairi ki azmat ka aitraf kiya.",
                        ),
                        answer = "Unki saadgi, soz-o-gudaz aur Urdu ghazal mein buland maqam ki wajah se.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c9_u2_q1",
                            prompt = "'Khuda-e-Sukhan' kis mashhoor Urdu shayar ka laqab hai?",
                            options = listOf("Mirza Ghalib", "Mir Taqi Mir (میر تقی میر)", "Allama Iqbal", "Faiz Ahmad Faiz"),
                            correctIndex = 1,
                            explanation = "Mir Taqi Mir ko Urdu adab mein Khuda-e-Sukhan kaha jata hai.",
                        ),
                        QuizQuestion(
                            id = "c9_u2_q2",
                            prompt = "Mashhoor Urdu afsana 'Idgah' (عیدگاہ) kisne likha?",
                            options = listOf("Krishan Chander", "Munshi Premchand", "Saadat Hasan Manto", "Rajinder Singh Bedi"),
                            correctIndex = 1,
                            explanation = "Idgah Munshi Premchand ka mashhoor aur sabaq-amoz Urdu afsana hai.",
                        ),
                        QuizQuestion(
                            id = "c9_u2_q3",
                            prompt = "Afsane ki sabse ahem khubi kya hoti hai?",
                            options = listOf("Lambai", "Wahdat-e-Tasur (وحدتِ تاثر)", "Qafiya aur Radif", "Rajaon ke qisse"),
                            correctIndex = 1,
                            explanation = "Afsana ek markazi khayal aur ek hi tasur (unity of impression) paida karta hai.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 10 URDU (BOARD EXAM FOCUS) ====================
        SubjectData(
            id = "c10_urdu",
            classLevel = 10,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Board Prep: Ghalib, Iqbal, San'atein & Board Writing",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c10_urdu_ch1",
                    classLevel = 10,
                    subjectId = "c10_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Mirza Ghalib & Allama Iqbal — Board Ghazal & Nazm",
                    estimatedMinutes = 28,
                    weightage = "16 Marks (Board High-Yield)",
                    summary = "Master board-exam Tashreeh, biographies, and central themes of Mirza Asadullah Khan Ghalib and Dr. Allama Iqbal.",
                    keyPoints = listOf(
                        "Mirza Asadullah Khan Ghalib (1797–1869, Agra/Delhi): Urdu ghazal mein falsafa, sho'khi, aur zinda-dili ke imam. Mashhoor kitab: Diwan-e-Ghalib, Urdu-e-Mualla (khutoot).",
                        "Allama Muhammad Iqbal (1877–1938, Sialkot): 'Shair-e-Mashriq' (شاعرِ مشرق). Falsafa-e-Khudi aur hubb-ul-watani ('Sare Jahan se Accha').",
                        "Iqbal ke mashhoor Urdu majmue: Bang-e-Dara, Bal-e-Jibril, Zarb-e-Kaleem.",
                        "Board Exam Tip: Tashreeh mein hamesha shayar ka mukhtasar ta'aruf aur shair ka gehra ma'ani saaf Urdu mein likhein.",
                    ),
                    formulas = listOf(
                        FormulaItem("Ghalib Key Facts", "پیدائش: 1797 آگرہ | وفات: 1869 دہلی | خطوط: عودِ ہندی، اردوئے معلیٰ", "Ghalib revolutionized Urdu letter writing"),
                        FormulaItem("Iqbal Key Works", "بانگِ درا (1924) • بالِ جبریل • ضربِ کلیم | لقب: شاعرِ مشرق", "Concept of Khudi (خودی — self-realization)"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Mirza Ghalib ke khutoot (letters) ki sabse badi khasusiyat kya hai?",
                        steps = listOf(
                            "Ghalib ne purane mushkil aur takalluf wale alqab-o-aadab ko khatam kar diya.",
                            "Unhone 'murasla ko mukalma bana diya' — yaani khat ko aam bol-chal aur dostana guftagu ka andaz diya.",
                        ),
                        answer = "Unhone murasla (letter) ko mukalma (natural conversation) bana diya.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_u1_q1",
                            prompt = "Kis shayar ne kaha ke maine 'murasla ko mukalma bana diya'?",
                            options = listOf("Mir Taqi Mir", "Mirza Ghalib (مرزا غالب)", "Sir Syed", "Altaf Husain Hali"),
                            correctIndex = 1,
                            explanation = "Mirza Ghalib ne Urdu khat-naveesi ko sada aur be-takalluf guftagu ka rang diya.",
                        ),
                        QuizQuestion(
                            id = "c10_u1_q2",
                            prompt = "'Bang-e-Dara' (بانگِ درا) kis shayar ka majmua-e-kalam hai?",
                            options = listOf("Mirza Ghalib", "Allama Iqbal (علامہ اقبال)", "Josh Malihabadi", "Faiz"),
                            correctIndex = 1,
                            explanation = "Bang-e-Dara Allama Iqbal ka pehla aur mashhoor Urdu shairi majmua hai.",
                        ),
                        QuizQuestion(
                            id = "c10_u1_q3",
                            prompt = "Mirza Ghalib ki paidaish kis shehar mein hui thi?",
                            options = listOf("Lucknow", "Agra (آگرہ)", "Lahore", "Hyderabad"),
                            correctIndex = 1,
                            explanation = "Mirza Ghalib 1797 mein Agra mein paida hue aur unki zindagi ka bada hissa Delhi mein guzra.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c10_urdu_ch2",
                    classLevel = 10,
                    subjectId = "c10_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Board Qawaid: Tashbeeh, Istiara, Talmeeh & Tazaad (صنعتیں)",
                    estimatedMinutes = 26,
                    weightage = "14 Marks (Board High-Yield)",
                    summary = "Score full marks in Class 10 Board Urdu grammar by mastering poetic devices (San'atein / Figures of Speech).",
                    keyPoints = listOf(
                        "Tashbeeh (تشبیہ — Simile): Kisi ek cheez ko mushtarik khubi ki bina par doosri jaisa batana (harf-e-tashbeeh: jaisa, ki tarah, manind). Maslan: 'Baccha chaand ki tarah khubsurat hai.'",
                        "Istiara (استعارہ — Metaphor): Kisi cheez ko seedha doosri cheez keh dena bina 'jaisa/tarah' ke. Maslan: 'Mera chaand aa gaya.'",
                        "Talmeeh (تلمیح — Allusion): Shair mein kisi tareekhi ya mazhabi waqia ki taraf ishara karna (jaise: Aatish-e-Namrood, Koh-e-Toor, Yusuf-o-Zulekha).",
                        "Tazaad (تضاد — Antithesis): Ek hi shair mein do mutazad (opposite) alfaz lana (jaise: subah-shaam, khizan-bahar, zindagi-maut).",
                    ),
                    formulas = listOf(
                        FormulaItem("Arkan-e-Tashbeeh (5)", "مشبّہ • مشبّہ بہ • حرفِ تشبیہ • وجہِ شبہ • غرضِ تشبیہ", "Must have comparing word like 'tarah/sa/manind'"),
                        FormulaItem("Quick San'at Map", "تشبیہ (Simile) | استعارہ (Metaphor) | تلمیح (Historical ref) | تضاد (Opposites)", "Guaranteed 4-6 marks in 10th Board"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Pehchanein kaunsi San'at hai: 'Subah hoti hai shaam hoti hai / Umar yun hi tamam hoti hai.'",
                        steps = listOf(
                            "Shair mein 'subah' (صبح) aur 'shaam' (شام) do ek-doosre ke mutazad (opposite) alfaz istemal hue hain.",
                            "Jis shair mein do mutazad alfaz aayein wahan San'at-e-Tazaad hoti hai.",
                        ),
                        answer = "San'at-e-Tazaad (صنعتِ تضاد)",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_u2_q1",
                            prompt = "'Mera beta sher hai' — is jumle mein kaunsi san'at istemal hui hai?",
                            options = listOf("Tashbeeh", "Istiara (استعارہ)", "Talmeeh", "Tazaad"),
                            correctIndex = 1,
                            explanation = "Bina 'jaisa/tarah' lagaye bete ko seedha 'sher' kaha gaya hai — ye Istiara hai.",
                        ),
                        QuizQuestion(
                            id = "c10_u2_q2",
                            prompt = "Shair mein kisi tareekhi ya mazhabi qisse ki taraf ishara karna kya kehlata hai?",
                            options = listOf("Tazaad", "Talmeeh (تلمیح)", "Husn-e-Talil", "Mubaligha"),
                            correctIndex = 1,
                            explanation = "Talmeeh mein ek lafz ke zariye poore tareekhi waqia ki taraf ishara hota hai.",
                        ),
                        QuizQuestion(
                            id = "c10_u2_q3",
                            prompt = "Tashbeeh ke kul kitne Arkan (parts) hote hain?",
                            options = listOf("2", "3", "5", "7"),
                            correctIndex = 2,
                            explanation = "Tashbeeh ke 5 arkan hain: Mushabbah, Mushabbah bihi, Harf-e-Tashbeeh, Wajah-e-Shabah, Gharaz-e-Tashbeeh.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c10_urdu_ch3",
                    classLevel = 10,
                    subjectId = "c10_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 3,
                    title = "Board Nasr & Composition: Inshaiya, Khulasa & Mazmoon",
                    estimatedMinutes = 22,
                    weightage = "14 Marks",
                    summary = "Master prose forms (Inshaiya, Sawaneh, Khaka) and Board 10th composition formats for full marks.",
                    keyPoints = listOf(
                        "Inshaiya (انشائیہ): Halka-phulka, shagufta aur ghair-rasmi mazmoon jisme musannif ki shakhsiyat jhalakti hai.",
                        "Sawaneh Umri (سوانح عمری): Kisi mashhoor shakhsiyat ki zindagi ke halat (jaise Altaf Husain Hali ki 'Hayat-e-Javed').",
                        "Khaka (خاکہ): Kisi shakhs ki aadatein, khubiyan aur shakhsiyat ki dilchasp tasveer-kashi.",
                        "Sabaq ka Khulasa hamesha mukhtasar (approx. 1/3rd) aur apne aasan alfaz mein likhein.",
                    ),
                    formulas = listOf(
                        FormulaItem("Prose Forms", "داستان (طویل قصہ) • ناول (زندگی کا عکاس) • افسانہ (مختصر) • خاکہ (شخصیت)", "Distinguish carefully in Board MCQs"),
                        FormulaItem("First Urdu Novel", "مراۃ العروس (Mirat-ul-Uroos, 1869) — Deputy Nazir Ahmad", "Most repeated Board 1-mark MCQ"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Urdu ka pehla novel kaunsa hai aur uske musannif kaun hain?",
                        steps = listOf(
                            "Urdu ka pehla novel 'Mirat-ul-Uroos' (مراۃ العروس) hai jo 1869 mein shaya hua.",
                            "Iske musannif Deputy Nazir Ahmad (ڈپٹی نذیر احمد) hain.",
                        ),
                        answer = "Mirat-ul-Uroos — Deputy Nazir Ahmad",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c10_u3_q1",
                            prompt = "Urdu ka pehla novel 'Mirat-ul-Uroos' kisne likha?",
                            options = listOf("Mir Amman", "Deputy Nazir Ahmad", "Premchand", "Sir Syed"),
                            correctIndex = 1,
                            explanation = "Deputy Nazir Ahmad Urdu ke pehle novel nigar hain jinhone 1869 mein Mirat-ul-Uroos likha.",
                        ),
                        QuizQuestion(
                            id = "c10_u3_q2",
                            prompt = "'Hayat-e-Javed' kis ki sawaneh umri (biography) hai?",
                            options = listOf("Mirza Ghalib", "Sir Syed Ahmad Khan", "Allama Iqbal", "Mir Taqi Mir"),
                            correctIndex = 1,
                            explanation = "Maulana Altaf Husain Hali ne Sir Syed Ahmad Khan ki sawaneh 'Hayat-e-Javed' likhi.",
                        ),
                        QuizQuestion(
                            id = "c10_u3_q3",
                            prompt = "Shagufta aur ghair-rasmi andaz mein likha gaya chhota mazmoon kya kehlata hai?",
                            options = listOf("Inshaiya (انشائیہ)", "Dastan", "Marsiya", "Qasida"),
                            correctIndex = 0,
                            explanation = "Inshaiya (Light essay) mein be-takallufi aur musannif ka zati andaz numayan hota hai.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 11 URDU ====================
        SubjectData(
            id = "c11_urdu",
            classLevel = 11,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Gulistan-e-Adab: Tareekh, Classical Ghazal & Ilm-e-Bayan",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c11_urdu_ch1",
                    classLevel = 11,
                    subjectId = "c11_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Urdu Zaban ka Aaghaz, Wali Dakani & Classical Ghazal",
                    estimatedMinutes = 26,
                    weightage = "15 Marks",
                    summary = "Trace the origins of Urdu in North India & Deccan, Fort William College (1800), and classical masters Wali, Mir & Dard.",
                    keyPoints = listOf(
                        "Urdu lafz Turki zaban ka hai jiska ma'ani 'Lashkar' (لشکر) hai; ye Hindustan mein mukhtalif zabanon ke mel-jol se paida hui.",
                        "Wali Dakani (ولی دکنی) ko 'Baba-e-Rekhta' aur Urdu ghazal ka pehla sahib-e-diwan shayar mana jata hai.",
                        "Fort William College (Calcutta, 1800) mein Mir Amman Dehlavi ne mashhoor dastan 'Bagh-o-Bahar' (باغ و بہار) sada nasr mein likhi.",
                        "Khwaja Mir Dard (خواجہ میر درد) Urdu mein Tasawwuf (سوفیانہ شاعری) ke sabse bade shayar hain.",
                    ),
                    formulas = listOf(
                        FormulaItem("Literary Landmarks", "1800: Fort William College | باغ و بہار: میر امن | باباے اردو: مولوی عبدالحق", "Core Class 11 Urdu history milestones"),
                        FormulaItem("Dabistan-e-Delhi vs Lucknow", "دہلی: داخلی، سوز و گداز (میر، درد)  |  لکھنؤ: خارجی، زبان کی صفائی (ناسخ، آتش)", "Classic comparison exam question"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Dabistan-e-Delhi aur Dabistan-e-Lucknow ki shairi mein do buniyadi farq likhein.",
                        steps = listOf(
                            "Dabistan-e-Delhi mein dakhiliyat (jazbat, dard, tasawwuf aur sadgi) par zor tha — jaise Mir aur Dard.",
                            "Dabistan-e-Lucknow mein kharijiyat (zaban ki safai, muamla-bandi aur san'at-gari) ghalib thi — jaise Nasikh aur Atish.",
                        ),
                        answer = "Delhi: Dakhiliyat aur soz-o-gudaz | Lucknow: Kharijiyat aur zaban ki aaraish.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c11_u1_q1",
                            prompt = "Mashhoor kitab 'Bagh-o-Bahar' (باغ و بہار) kisne tasneef ki?",
                            options = listOf("Mir Amman Dehlavi", "Mirza Ruswa", "Sir Syed", "Premchand"),
                            correctIndex = 0,
                            explanation = "Mir Amman ne Fort William College mein 1801–1803 mein Bagh-o-Bahar likhi.",
                        ),
                        QuizQuestion(
                            id = "c11_u1_q2",
                            prompt = "Urdu mein Tasawwuf (صوفیانہ شاعری) ka sabse bada shayar kise kaha jata hai?",
                            options = listOf("Atish", "Khwaja Mir Dard (خواجہ میر درد)", "Nasikh", "Momin"),
                            correctIndex = 1,
                            explanation = "Khwaja Mir Dard ko Tasawwuf aur dakhiliyat ka imam mana jata hai.",
                        ),
                        QuizQuestion(
                            id = "c11_u1_q3",
                            prompt = "Fort William College kis shehar aur kis saal mein qaim hua?",
                            options = listOf("Delhi, 1857", "Calcutta, 1800", "Lucknow, 1820", "Aligarh, 1875"),
                            correctIndex = 1,
                            explanation = "Fort William College 1800 mein Calcutta mein qaim hua jisne sada Urdu nasr ko farogh diya.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c11_urdu_ch2",
                    classLevel = 11,
                    subjectId = "c11_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Ilm-e-Bayan & Masnavi (علمِ بیان، مثنوی اور نظم)",
                    estimatedMinutes = 26,
                    weightage = "15 Marks",
                    summary = "Master the 4 pillars of Ilm-e-Bayan (Tashbeeh, Istiara, Majaz-e-Mursal, Kinaya) plus Masnavi & Nazeer Akbarabadi.",
                    keyPoints = listOf(
                        "Ilm-e-Bayan ke 4 hisse hain: Tashbeeh (تشبیہ), Istiara (استعارہ), Majaz-e-Mursal (مجازِ مرسل), aur Kinaya (کنایہ).",
                        "Kinaya: Wo lafz jiske haqeeqi ma'ani bhi durust hon magar murad majazi ma'ani hon (maslan: 'Baal safed ho gaye' = budhapa aa gaya).",
                        "Masnavi (مثنوی): Lambi qissa-go nazm jiske har shair ka qafiya alag hota hai (maslan: Mir Hasan ki 'Sahr-ul-Bayan').",
                        "Nazeer Akbarabadi ko 'Awami Shayar' (عوامی شاعر) kaha jata hai (mashhoor nazmein: Banjara Nama, Aadmi Nama, Roti).",
                    ),
                    formulas = listOf(
                        FormulaItem("4 Pillars of Ilm-e-Bayan", "تشبیہ • استعارہ • مجازِ مرسل • کنایہ", "Senior Secondary Rhetoric Essentials"),
                        FormulaItem("Famous Masnavi", "سحر البیان (میر حسن)  |  گلزارِ نسیم (دیا شنکر نسیم)", "Each couplet has its own rhyming pair"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Masnavi ki taareef aur do mashhoor Urdu masnaviyon ke naam likhein.",
                        steps = listOf(
                            "Masnavi wo taveel (long) nazm hai jisme koi qissa, dastan ya tareekhi waqia tasalsul se bayan ho aur har shair ke dono misre alag hum-qafiya hon.",
                            "Mashhoor Masnaviyan: 1) Sahr-ul-Bayan (Mir Hasan), 2) Gulzar-e-Naseem (Daya Shankar Naseem).",
                        ),
                        answer = "Sahr-ul-Bayan (Mir Hasan) aur Gulzar-e-Naseem (Daya Shankar Naseem).",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c11_u2_q1",
                            prompt = "Mashhoor masnavi 'Sahr-ul-Bayan' (سحر البیان) ke shayar kaun hain?",
                            options = listOf("Mir Anis", "Mir Hasan (میر حسن)", "Nazeer Akbarabadi", "Ghalib"),
                            correctIndex = 1,
                            explanation = "Mir Hasan ki Sahr-ul-Bayan Urdu ki sabse mashhoor masnavi mani jati hai.",
                        ),
                        QuizQuestion(
                            id = "c11_u2_q2",
                            prompt = "Urdu ka 'Awami Shayar' (عوامی شاعر) kise kaha jata hai?",
                            options = listOf("Mirza Ghalib", "Nazeer Akbarabadi (نظیر اکبرآبادی)", "Momin", "Zauq"),
                            correctIndex = 1,
                            explanation = "Nazeer Akbarabadi ne aam logon, tehwaron aur rozmarra zindagi par mashhoor nazmein likhein.",
                        ),
                        QuizQuestion(
                            id = "c11_u2_q3",
                            prompt = "Ilm-e-Bayan ke kul kitne hisse (arkan) hote hain?",
                            options = listOf("2", "3", "4", "6"),
                            correctIndex = 2,
                            explanation = "Ilm-e-Bayan ke 4 hisse hain: Tashbeeh, Istiara, Majaz-e-Mursal aur Kinaya.",
                        ),
                    ),
                ),
            ),
        ),

        // ==================== CLASS 12 URDU (BOARD EXAM MASTERY) ====================
        SubjectData(
            id = "c12_urdu",
            classLevel = 12,
            streams = allStreams,
            name = "Urdu (اردو)",
            subtitle = "Board 12th: Marsiya, Sir Syed, Firaq, Ilm-e-Badi & Talkhees",
            category = SubjectCategory.URDU,
            chapters = listOf(
                ChapterData(
                    id = "c12_urdu_ch1",
                    classLevel = 12,
                    subjectId = "c12_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 1,
                    title = "Marsiya (Mir Anis), Qasida (Sauda) & Jadeed Ghazal (Firaq)",
                    estimatedMinutes = 30,
                    weightage = "18 Marks (Board High-Yield)",
                    summary = "Master the 8 Ajza of Marsiya by Mir Anis, Qasida by Mirza Rafi Sauda, and modern Ghazal by Firaq & Faiz.",
                    keyPoints = listOf(
                        "Marsiya (مرثیہ): Kisi marhoom (khas tor par Shuhada-e-Karbala) ke gham mein likhi gayi nazm. Mir Babar Ali Anis aur Mirza Dabir marsiya ke do bade ustad hain.",
                        "Marsiya ke 8 Ajza-e-Tarkeebi: Chehra, Sarapa, Rukhsat, Aamad, Rajaz, Jang, Shahadat, aur Bain.",
                        "Qasida (قصیدہ): Kisi ki madah (tareef) ya hajw mein likhi jane wali nazm; Mirza Muhammad Rafi Sauda Qasida ke badshah kehlate hain.",
                        "Firaq Gorakhpuri (فراق گورکھپوری — Gul-e-Naghma) ko Urdu ka pehla Jnanpith Award (1969) mila.",
                    ),
                    formulas = listOf(
                        FormulaItem("8 Ajza of Marsiya", "چہرہ ← سراپا ← رخصت ← آمد ← رجز ← جنگ ← شہادت ← بین", "Memorize exact sequence for 12th Board!"),
                        FormulaItem("4 Ajza of Qasida", "تشبیب (Tashbeeb) ← گریز (Gurez) ← مدح (Madah) ← دعا (Dua)", "Top Qasida poets: Sauda & Zauq"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "Qasida ke char (4) ajza-e-tarkeebi tartib se likhein.",
                        steps = listOf(
                            "1. Tashbeeb (تشبیب): Shuruati hissa jisme bahar, husn ya falsafe ka zikr ho.",
                            "2. Gurez (گریز): Tashbeeb se mamdoh (tareef) ki taraf khubsurat mod.",
                            "3. Madah (مدح): Mamdoh ki tareef (qaside ka sabse ahem hissa).",
                            "4. Dua / Husn-e-Talab (دعا): Aakhir mein mamdoh ke liye dua aur apna maqsad bayan karna.",
                        ),
                        answer = "Tashbeeb → Gurez → Madah → Dua",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c12_u1_q1",
                            prompt = "Urdu Marsiya ke kul kitne Ajza-e-Tarkeebi (parts) hote hain?",
                            options = listOf("4", "6", "8", "10"),
                            correctIndex = 2,
                            explanation = "Marsiya ke 8 ajza hain: Chehra, Sarapa, Rukhsat, Aamad, Rajaz, Jang, Shahadat, Bain.",
                        ),
                        QuizQuestion(
                            id = "c12_u1_q2",
                            prompt = "Urdu ka pehla Jnanpith Award (گیان پیٹھ ایوارڈ) kis shayar ko unki kitab 'Gul-e-Naghma' par mila?",
                            options = listOf("Allama Iqbal", "Firaq Gorakhpuri (فراق گورکھپوری)", "Faiz Ahmad Faiz", "Ali Sardar Jafri"),
                            correctIndex = 1,
                            explanation = "Firaq Gorakhpuri ko 1969 mein Gul-e-Naghma par Urdu ka pehla Jnanpith Award mila.",
                        ),
                        QuizQuestion(
                            id = "c12_u1_q3",
                            prompt = "Urdu Qasida-goi ka sabse bada ustad kise mana jata hai?",
                            options = listOf("Mirza Rafi Sauda (سودا)", "Mir Dard", "Mir Hasan", "Premchand"),
                            correctIndex = 0,
                            explanation = "Mirza Muhammad Rafi Sauda ko Urdu qaside ka badshah kaha jata hai.",
                        ),
                    ),
                ),
                ChapterData(
                    id = "c12_urdu_ch2",
                    classLevel = 12,
                    subjectId = "c12_urdu",
                    subjectName = "Urdu (اردو)",
                    number = 2,
                    title = "Aligarh Tehreek (Sir Syed), Taraqqi Pasand & Talkhees Nigari",
                    estimatedMinutes = 28,
                    weightage = "16 Marks (Board High-Yield)",
                    summary = "Analyze Sir Syed Ahmad Khan's modern Urdu prose, Progressive Writers' Movement (1936), Ilm-e-Badi, and Precis Writing (تلخیص).",
                    keyPoints = listOf(
                        "Sir Syed Ahmad Khan (1817–1898) ne Aligarh Tehreek aur risala 'Tehzeeb-ul-Akhlaq' (1870) ke zariye jadeed, ilmi aur sada Urdu nasr ki buniyad rakhi.",
                        "Anjuman Taraqqi Pasand Musannifeen (1936, Lucknow) ki pehli conference ki sadarat Munshi Premchand ne ki.",
                        "Ilm-e-Badi ki ahem san'atein: Husn-e-Talil (حسنِ تعلیل), Miraat-ul-Nazeer (مراعاۃ النظیر), Laf-o-Nashr, aur Tajnees.",
                        "Talkhees Nigari (تلخیص نگاری — Precis): Asl ibarat ka 1/3rd (ek-tihai) hissa, munasib Unwan (Title) ke sath apne alfaz mein likhna.",
                    ),
                    formulas = listOf(
                        FormulaItem("Talkhees (Precis) Rule", "طوالت = اصل عبارت کا ایک تہائی (1/3) + مناسب عنوان (Title)", "Never copy sentences verbatim"),
                        FormulaItem("Key Prose Milestones", "تہذیب الاخلاق (سر سید، 1870) • امراؤ جان ادا (مرزا ہادی رسوا، 1899) • ترقی پسند تحریک (1936)", "Guaranteed Class 12 Board questions"),
                    ),
                    solvedExample = SolvedExample(
                        problem = "San'at-e-Miraat-ul-Nazeer (مراعاۃ النظیر) ki tareef misal ke sath likhein.",
                        steps = listOf(
                            "Jab shair mein aise alfaz ek sath laye jayein jinmein aapas mein gehra rishta ya munasibat ho (mutazad na hon).",
                            "Misal: 'Patta patta, boota boota haal hamara jane hai' ya 'Chaman, Phool, Kali, Bulbul' ka ek sath aana.",
                        ),
                        answer = "Baaham munasibat rakhne wale alfaz (jaise phool, kali, chaman, bagh) ko ek shair mein jama karna.",
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "c12_u2_q1",
                            prompt = "1936 mein Taraqqi Pasand Musannifeen ki pehli conference (Lucknow) ki sadarat kisne ki?",
                            options = listOf("Sajjad Zaheer", "Munshi Premchand", "Josh Malihabadi", "Sir Syed"),
                            correctIndex = 1,
                            explanation = "Munshi Premchand ne April 1936 mein Lucknow mein pehli conference ka khutba-e-sadarat parha.",
                        ),
                        QuizQuestion(
                            id = "c12_u2_q2",
                            prompt = "Risala 'Tehzeeb-ul-Akhlaq' (تہذیب الاخلاق) kisne jari kiya?",
                            options = listOf("Shibli Nomani", "Sir Syed Ahmad Khan", "Abul Kalam Azad", "Hali"),
                            correctIndex = 1,
                            explanation = "Sir Syed Ahmad Khan ne 1870 mein Tehzeeb-ul-Akhlaq jari kiya.",
                        ),
                        QuizQuestion(
                            id = "c12_u2_q3",
                            prompt = "Talkhees (تلخیص) likhte waqt asl ibarat ka kitna hissa likha jata hai?",
                            options = listOf("Aadha (1/2)", "Ek tihai (1/3)", "Dugna (2x)", "Poori ibarat"),
                            correctIndex = 1,
                            explanation = "Talkhees (Precis) hamesha asl passage ke ek-tihai (one-third) ke barabar aur munasib unwan ke sath hoti hai.",
                        ),
                    ),
                ),
            ),
        ),
    )
}
