const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "student_alice_10";
const BOB_UID = "student_bob_12";

const [emulatorHost, emulatorPortStr] = (
  process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085"
).split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

function pastDate(offsetMs = 5000) {
  return new Date(Date.now() - offsetMs);
}

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// 1. Unauthenticated rejection across all collections
test("Unauthenticated users cannot read or write any Shiksha collection", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  const now = pastDate();

  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
  await assertFails(unauthDb.collection("users").get());
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Hacker",
      schoolName: "",
      selectedClass: 10,
      stream: "GENERAL",
      board: "CBSE",
      dailyGoalMinutes: 60,
      createdAt: now,
      updatedAt: now,
    })
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("homework").get()
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("quiz_attempts").get()
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("study_notes").get()
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("timetable").get()
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("chapter_progress").get()
  );
});

// 2. Student Profile create, update, bounds, shadow-field & immutability checks
test("StudentProfile: owner create/update succeeds; invalid class, ghost field, future timestamp & cross-user fail", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(10000);

  // Mirror ShikshaRepository.ensureStudentProfile payload (NO @DocumentId `id` field)
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Aarav Sharma",
      schoolName: "Kendriya Vidyalaya",
      selectedClass: 10,
      stream: "GENERAL",
      board: "CBSE",
      dailyGoalMinutes: 60,
      createdAt: created,
      updatedAt: created,
    })
  );

  // Mirror ShikshaRepository.updateStudentProfile partial update
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).update({
      displayName: "Aarav Sharma",
      schoolName: "DPS Public School",
      selectedClass: 12,
      stream: "SCIENCE",
      board: "CBSE",
      dailyGoalMinutes: 90,
      updatedAt: pastDate(1000),
    })
  );

  // Reject listing /users root collection (PII / multi-tenant isolation)
  await assertFails(aliceDb.collection("users").get());

  // Reject cross-user read or write
  await assertFails(bobDb.collection("users").doc(ALICE_UID).get());
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).update({
      displayName: "Hijacked",
      updatedAt: pastDate(500),
    })
  );

  // Reject out-of-range class (only 7-12 permitted)
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      selectedClass: 6,
      updatedAt: pastDate(500),
    })
  );

  // Reject mutating immutable createdAt
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      createdAt: pastDate(500),
      updatedAt: pastDate(500),
    })
  );

  // Reject Shadow Update with Ghost Field
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      isAdmin: true,
      updatedAt: pastDate(500),
    })
  );

  // Reject future timestamp
  await assertFails(
    bobDb.collection("users").doc(BOB_UID).set({
      userId: BOB_UID,
      displayName: "Bob",
      schoolName: "",
      selectedClass: 11,
      stream: "COMMERCE",
      board: "ICSE",
      dailyGoalMinutes: 60,
      createdAt: new Date(Date.now() + 60000),
      updatedAt: new Date(Date.now() + 60000),
    })
  );
});

// 3. Chapter Progress create, update, list & isolation
test("ChapterProgress: owner upsert and list succeed; cross-user and extra id key fail", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(8000);

  await assertSucceeds(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("chapter_progress")
      .doc("c10_math_ch1")
      .set({
        userId: ALICE_UID,
        chapterId: "c10_math_ch1",
        classLevel: 10,
        subjectId: "c10_math",
        completed: true,
        bookmarked: true,
        createdAt: created,
        updatedAt: created,
      })
  );

  // Partial update mirroring ShikshaRepository.upsertChapterProgress
  await assertSucceeds(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("chapter_progress")
      .doc("c10_math_ch1")
      .update({
        classLevel: 10,
        subjectId: "c10_math",
        completed: true,
        bookmarked: false,
        updatedAt: pastDate(1000),
      })
  );

  // List query matching repository snapshots()
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("chapter_progress").get()
  );

  // Cross-user denied
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("chapter_progress").get()
  );

  // Extra @DocumentId 'id' field rejected
  await assertFails(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("chapter_progress")
      .doc("c10_math_ch2")
      .set({
        id: "c10_math_ch2",
        userId: ALICE_UID,
        chapterId: "c10_math_ch2",
        classLevel: 10,
        subjectId: "c10_math",
        completed: false,
        bookmarked: true,
        createdAt: created,
        updatedAt: created,
      })
  );
});

// 4. Quiz Attempts create & immutability
test("QuizAttempt: owner create/list succeed; updates and score > totalQuestions fail", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(5000);

  await assertSucceeds(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("quiz_attempts")
      .doc("attempt_1")
      .set({
        userId: ALICE_UID,
        classLevel: 10,
        subjectId: "c10_math",
        subjectName: "Mathematics",
        chapterId: "c10_math_ch1",
        chapterTitle: "Quadratic Equations",
        score: 3,
        totalQuestions: 3,
        accuracyPercent: 100,
        durationSeconds: 85,
        createdAt: created,
      })
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("quiz_attempts").get()
  );

  // Quiz attempts are immutable (no score tampering!)
  await assertFails(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("quiz_attempts")
      .doc("attempt_1")
      .update({ score: 100 })
  );

  // Score exceeding totalQuestions rejected
  await assertFails(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("quiz_attempts")
      .doc("attempt_bad")
      .set({
        userId: ALICE_UID,
        classLevel: 10,
        subjectId: "c10_math",
        subjectName: "Mathematics",
        chapterId: "c10_math_ch1",
        chapterTitle: "Quadratic Equations",
        score: 5,
        totalQuestions: 3,
        accuracyPercent: 100,
        durationSeconds: 30,
        createdAt: created,
      })
  );

  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("quiz_attempts").get()
  );
});

// 5. Homework Tasks create, toggle done, delete & validation
test("HomeworkTask: owner CRUD succeeds; invalid priority & cross-user fail", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(8000);

  const hwRef = aliceDb
    .collection("users")
    .doc(ALICE_UID)
    .collection("homework")
    .doc("hw_1");

  await assertSucceeds(
    hwRef.set({
      userId: ALICE_UID,
      classLevel: 10,
      subjectName: "Science",
      title: "Solve NCERT Ex 12.2 Ohm's Law numericals",
      details: "Questions 1 to 10 in fair notebook",
      dueDateLabel: "Tomorrow",
      priority: "HIGH",
      done: false,
      createdAt: created,
      updatedAt: created,
    })
  );

  // Toggle done action
  await assertSucceeds(
    hwRef.update({
      done: true,
      updatedAt: pastDate(1000),
    })
  );

  // Invalid priority enum rejected
  await assertFails(
    hwRef.update({
      priority: "URGENT",
      updatedAt: pastDate(500),
    })
  );

  // Cross-user delete rejected
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("homework").doc("hw_1").delete()
  );

  // Owner delete succeeds
  await assertSucceeds(hwRef.delete());
});

// 6. Study Notes & Timetable CRUD
test("StudyNote and TimetableEntry: owner CRUD succeeds; bounds & cross-user isolation enforced", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(8000);

  const noteRef = aliceDb
    .collection("users")
    .doc(ALICE_UID)
    .collection("study_notes")
    .doc("note_1");

  await assertSucceeds(
    noteRef.set({
      userId: ALICE_UID,
      classLevel: 12,
      subjectName: "Physics",
      title: "Electrostatics Quick Formulas",
      content: "F = k q1 q2 / r^2; Gauss law phi = q/eps0",
      pinned: true,
      createdAt: created,
      updatedAt: created,
    })
  );

  await assertSucceeds(
    noteRef.update({
      pinned: false,
      updatedAt: pastDate(1000),
    })
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("study_notes").get()
  );
  await assertFails(
    bobDb.collection("users").doc(ALICE_UID).collection("study_notes").get()
  );

  const ttRef = aliceDb
    .collection("users")
    .doc(ALICE_UID)
    .collection("timetable")
    .doc("tt_1");

  await assertSucceeds(
    ttRef.set({
      userId: ALICE_UID,
      dayOfWeek: 1,
      subjectName: "Mathematics",
      timeSlot: "08:00 AM - 08:45 AM",
      topic: "Trigonometry Identities",
      createdAt: created,
      updatedAt: created,
    })
  );

  // Invalid dayOfWeek (outside 1..7) rejected
  await assertFails(
    aliceDb
      .collection("users")
      .doc(ALICE_UID)
      .collection("timetable")
      .doc("tt_bad")
      .set({
        userId: ALICE_UID,
        dayOfWeek: 9,
        subjectName: "Mathematics",
        timeSlot: "09:00 AM",
        topic: "",
        createdAt: created,
        updatedAt: created,
      })
  );

  await assertSucceeds(ttRef.delete());
});
