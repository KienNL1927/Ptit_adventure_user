package com.example.ptitadventure.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ptitadventure.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_ROLE = "role";

    public static final String TABLE_STUDENT = "student";
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_USER_ID = "user_id";
    public static final String STUDENT_CLASS = "class";
    public static final String STUDENT_CODE = "student_code";
    public static final String STUDENT_MAJOR = "major";

    public static final String TABLE_STAFF = "staff";
    public static final String STAFF_ID = "id";
    public static final String STAFF_USER_ID = "user_id";
    public static final String STAFF_FACULTY = "faculty";
    public static final String STAFF_CODE = "staff_code";


    public static final String TABLE_LOCATION = "location";
    public static final String LOCATION_ID = "id";
    public static final String LOCATION_FLOORS = "floors";
    public static final String LOCATION_BUILDING = "building";
    public static final String LOCATION_QUEST_ID = "quest_id";

    public static final String TABLE_QUEST = "quest";
    public static final String QUEST_ID = "id";
    public static final String QUEST_NAME = "name";
    public static final String QUEST_DESCRIPTION = "description";
    public static final String QUEST_PARENT_ID = "parent_id";
    public static final String QUEST_STATUS = "status";

    public static final String TABLE_STUDENT_QUEST = "student_quest";
    public static final String STUDENT_QUEST_ID = "id";
    public static final String STUDENT_QUEST_STUDENT_ID = "student_id";
    public static final String STUDENT_QUEST_QUEST_ID = "quest_id";
    public static final String STUDENT_QUEST_SCORE = "score";

    public static final String TABLE_QUIZ = "quiz";
    public static final String QUIZ_ID = "id";
    public static final String QUIZ_TITLE = "title";

    public static final String TABLE_QUESTION = "question";
    public static final String QUESTION_ID = "id";
    public static final String QUESTION_TEXT = "text";
    public static final String QUESTION_QUIZ_ID = "quiz_id";


    public static final String TABLE_CHOICE = "choice";
    public static final String CHOICE_ID = "id";
    public static final String CHOICE_TEXT = "text";
    public static final String CHOICE_IS_CORRECT = "is_correct";
    public static final String CHOICE_QUESTION_ID = "question_id";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_EMAIL + " TEXT, " +
            USER_PASSWORD + " TEXT, " +
            USER_PHONE + " TEXT, " +
            USER_ROLE + " TEXT)";

    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " (" +
            STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STUDENT_USER_ID + " INTEGER, " +
            STUDENT_CLASS + " TEXT, "
            + STUDENT_CODE + " TEXT, "
            + STUDENT_MAJOR + " TEXT)";

    public static final String CREATE_TABLE_STAFF = "CREATE TABLE " + TABLE_STAFF + " (" +
            STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STAFF_USER_ID + " INTEGER, " +
            STAFF_FACULTY + " TEXT, "
            + STAFF_CODE + " TEXT)";

    public static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION + " (" +
            LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LOCATION_FLOORS + " INTEGER, " +
            LOCATION_BUILDING + " TEXT," +
            LOCATION_QUEST_ID + " INTEGER)";

    public static final String CREATE_TABLE_QUEST = "CREATE TABLE " + TABLE_QUEST + " (" +
            QUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUEST_NAME + " TEXT, " +
            QUEST_DESCRIPTION + " TEXT, " +
            QUEST_PARENT_ID + " INTEGER," +
            QUEST_STATUS + " INTEGER)";

    public static final String CREATE_TABLE_STUDENT_QUEST = "CREATE TABLE " + TABLE_STUDENT_QUEST + " (" +
            STUDENT_QUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STUDENT_QUEST_STUDENT_ID + " INTEGER, " +
            STUDENT_QUEST_QUEST_ID + " INTEGER, " +
            STUDENT_QUEST_SCORE + " INTEGER)";

    public static final String CREATE_TABLE_QUIZ = "CREATE TABLE " + TABLE_QUIZ + " (" +
            QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUIZ_TITLE + " TEXT)";

    public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + " (" +
            QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUESTION_TEXT + " TEXT, " +
            QUESTION_QUIZ_ID + " INTEGER)";

    public static final String CREATE_TABLE_CHOICE = "CREATE TABLE " + TABLE_CHOICE + " (" +
            CHOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CHOICE_TEXT + " TEXT, " +
            CHOICE_IS_CORRECT + " INTEGER, " +
            CHOICE_QUESTION_ID + " INTEGER)";


    public static final String DROP_TABLE_LOCATION = "DROP TABLE IF EXISTS " + TABLE_LOCATION;
    public static final String DROP_TABLE_QUEST = "DROP TABLE IF EXISTS " + TABLE_QUEST;
    public static final String DROP_TABLE_CHOICE = "DROP TABLE IF EXISTS " + TABLE_CHOICE;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_STAFF);
        db.execSQL(CREATE_TABLE_QUEST);
        db.execSQL(CREATE_TABLE_QUIZ);
        db.execSQL(CREATE_TABLE_STUDENT_QUEST);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_CHOICE);
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // DATA
    private void insertSampleData(SQLiteDatabase db) {
        // Insert Location data

        // Insert Quest data
        // Quest tang 1
        long p1ID = insertQuest(db, "Tầng 1 A1", "Sảnh chính A1", null, 0);
        insertQuest(db, "Multimedia", "Tìm ra phòng multimedia", (int) p1ID, 0);
        insertQuest(db, "Naver? Cái nôi AI", "Tìm hiểu phòng NaverAI", (int) p1ID, 0);
        insertQuest(db, "Phòng giáo vụ", "Tìm ra phòng giáo vụ", (int) p1ID, 0);
        insertQuest(db, "Thang máy", "Tìm ra thang máy", (int) p1ID, 0);
        // Quest tang 2
        long p2ID = insertQuest(db, "Tầng 2 A1", "Tìm kiếm thông tin trong thư viện PTIT", null, 0);
        insertQuest(db, "Phòng đọc", "Tìm hiểu về phòng đọc", (int) p2ID, 0);
        insertQuest(db, "Phòng học", "Tìm hiểu về phòng học", (int) p2ID, 0);
        insertQuest(db, "Phòng lấy bằng", "Tìm hiểu về phòng lấy bằng", (int) p2ID, 0);

        // Quest tang 3
        long p3ID = insertQuest(db, "Tầng 3 A1", "Khám phá các phòng thí nghiệm của trường", null, 0);
        insertQuest(db, "Phòng thí nghiệm CNTT", "Tìm hiểu về phòng thí nghiệm CNTT", (int) p3ID, 0);
        insertQuest(db, "Phòng thí nghiệm Điện tử", "Tìm hiểu về phòng thí nghiệm Điện tử", (int) p3ID, 0);

        // Quest tang 4
        long p4ID = insertQuest(db, "Tầng 4 A1", "Tìm hiểu về các câu lạc bộ và hoạt động ngoại khóa", null, 0);
        insertQuest(db, "Câu lạc bộ Tin học", "Tìm hiểu về câu lạc bộ Tin học", (int) p4ID, 0);
        insertQuest(db, "Câu lạc bộ Tiếng Anh", "Tìm hiểu về câu lạc bộ Tiếng Anh", (int) p4ID, 0);

        // Quest tang 5
        long p5ID = insertQuest(db, "Tầng 5 A1", "CIE", null, 0);
        insertQuest(db, "CIE", "Tìm hiểu về CIE", (int) p5ID, 0);
        insertQuest(db, "Phòng học mobile", "Tìm hiểu về phòng học mobile", (int) p5ID, 0);

        insertLocation(db, 1, "A1", (int) p1ID);
        insertLocation(db, 2, "A1", (int) p2ID);
        insertLocation(db, 3, "A1", (int) p3ID);
        insertLocation(db, 4, "A1", (int) p4ID);
        insertLocation(db, 5, "A1", (int) p5ID);


        // Insert Quiz data
        long quizId1 = insertQuiz(db, "Lịch sử PTIT");
        long quizId2 = insertQuiz(db, "Thư viện PTIT");
        long quizId3 = insertQuiz(db, "Phòng thí nghiệm PTIT");
        long quizId4 = insertQuiz(db, "Hoạt động ngoại khóa PTIT");
        long quizId5 = insertQuiz(db, "Ẩm thực PTIT");

        // Insert Question data
        long questionId1 = insertQuestion(db, "PTIT được thành lập vào năm nào?", quizId1);
        long questionId2 = insertQuestion(db, "Thư viện PTIT có bao nhiêu tầng?", quizId2);
        long questionId3 = insertQuestion(db, "PTIT có bao nhiêu phòng thí nghiệm?", quizId3);
        long questionId4 = insertQuestion(db, "Câu lạc bộ nào là lâu đời nhất ở PTIT?", quizId4);
        long questionId5 = insertQuestion(db, "Món ăn nào được yêu thích nhất ở căng tin PTIT?", quizId5);

        // Insert Choice data
        insertChoice(db, "1997", 1, questionId1);
        insertChoice(db, "2000", 0, questionId1);
        insertChoice(db, "1953", 0, questionId1);
        insertChoice(db, "1980", 0, questionId1);

        insertChoice(db, "2 tầng", 1, questionId2);
        insertChoice(db, "3 tầng", 0, questionId2);
        insertChoice(db, "4 tầng", 0, questionId2);
        insertChoice(db, "1 tầng", 0, questionId2);

        insertChoice(db, "10", 1, questionId3);
        insertChoice(db, "5", 0, questionId3);
        insertChoice(db, "15", 0, questionId3);
        insertChoice(db, "20", 0, questionId3);

        insertChoice(db, "Câu lạc bộ Tin học", 1, questionId4);
        insertChoice(db, "Câu lạc bộ Tiếng Anh", 0, questionId4);
        insertChoice(db, "Câu lạc bộ Guitar", 0, questionId4);
        insertChoice(db, "Câu lạc bộ Võ thuật", 0, questionId4);

        insertChoice(db, "Phở", 1, questionId5);
        insertChoice(db, "Cơm rang", 0, questionId5);
        insertChoice(db, "Bún chả", 0, questionId5);
        insertChoice(db, "Bánh mì", 0, questionId5);

        // insert student sample data
        insertStudent(db);
    }

    private void insertLocation(SQLiteDatabase db, int floors, String building, int questId) {
        ContentValues values = new ContentValues();
        values.put(LOCATION_QUEST_ID, questId);
        values.put(LOCATION_FLOORS, floors);
        values.put(LOCATION_BUILDING, building);
        db.insert(TABLE_LOCATION, null, values);
    }

    private long insertQuest(SQLiteDatabase db, String name, String description, Integer parentId, int status) {
        ContentValues values = new ContentValues();
        values.put(QUEST_NAME, name);
        values.put(QUEST_DESCRIPTION, description);
        if (parentId != null) {
            values.put(QUEST_PARENT_ID, parentId);
        }
        values.put(QUEST_STATUS, status);
        long id = db.insert(TABLE_QUEST, null, values);
        return id;
    }

    private long insertQuiz(SQLiteDatabase db, String title) {
        ContentValues values = new ContentValues();
        values.put(QUIZ_TITLE, title);
        return db.insert(TABLE_QUIZ, null, values);
    }

    private long insertQuestion(SQLiteDatabase db, String text, long quizId) {
        ContentValues values = new ContentValues();
        values.put(QUESTION_TEXT, text);
        values.put(QUESTION_QUIZ_ID, quizId);
        return db.insert(TABLE_QUESTION, null, values);
    }

    private void insertChoice(SQLiteDatabase db, String text, int isCorrect, long questionId) {
        ContentValues values = new ContentValues();
        values.put(CHOICE_TEXT, text);
        values.put(CHOICE_IS_CORRECT, isCorrect);
        values.put(CHOICE_QUESTION_ID, questionId);
        db.insert(TABLE_CHOICE, null, values);
    }

    private long insertUser(SQLiteDatabase db, String name, String email, String password, String phone, String role) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        values.put(USER_PHONE, phone);
        values.put(USER_ROLE, role);
        return db.insert(TABLE_USER, null, values);
    }

    private void insertStudent(SQLiteDatabase db) {
        // insert user first
        long id = insertUser(db, "Bui Duy Khanh", "khanhbd.b21vt246@stu.ptit.edu.vn", "123456", "0987654321", "student");
        // insert student
        ContentValues values = new ContentValues();
        values.put(STUDENT_USER_ID, id);
        values.put(STUDENT_CODE, "B21DCVT246");
        values.put(STUDENT_CLASS, "E21CNPM4");
        values.put(STUDENT_MAJOR, "CNTT");
        db.insert(TABLE_STUDENT, null, values);
    }
}
