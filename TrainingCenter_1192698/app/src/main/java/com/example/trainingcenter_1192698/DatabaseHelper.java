package com.example.trainingcenter_1192698;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TrainingCenter";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ACCOUNT = "account";
    private static final String ACCOUNT_COLUMN_EMAIL = "email";
    private static final String ACCOUNT_COLUMN_PASSWORD = "password";
    private static final String ACCOUNT_COLUMN_FIRST_NAME = "first_name";
    private static final String ACCOUNT_COLUMN_LAST_NAME = "last_name";
    private static final String ACCOUNT_COLUMN_TYPE = "type"; // Admin, Trainee, Instructor

    private static final String TABLE_TRAINEE = "trainee";
    private static final String TRAINEE_COLUMN_ID = "id";
    private static final String TRAINEE_COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String TRAINEE_COLUMN_ADDRESS = "address";

    private static final String TABLE_INSTRUCTOR = "instructor";
    private static final String INSTRUCTOR_COLUMN_ID = "id";
    private static final String INSTRUCTOR_COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String INSTRUCTOR_COLUMN_ADDRESS = "address";
    private static final String INSTRUCTOR_COLUMN_SPECIALIZATION = "specialization";
    private static final String INSTRUCTOR_COLUMN_DEGREE = "degree";
    private static final String INSTRUCTOR_COLUMN_COURSES = "courses"; // Added field for courses


    private static final String TABLE_COURSE = "course";
    private static final String COURSE_COLUMN_ID = "id";
    private static final String COURSE_COLUMN_TITLE = "title";
    private static final String COURSE_COLUMN_TOPICS = "topics";
    private static final String COURSE_COLUMN_PREREQUISITES = "prerequisites";
    private static final String COURSE_COLUMN_PHOTO = "photo";

    private static final String TABLE_COURSES_AVAILABLE = "courses_available";
    private static final String COURSES_AVAILABLE_COLUMN_ID = "id";
    private static final String COURSES_AVAILABLE_COLUMN_COURSE_ID = "course_id";
    private static final String COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME = "instructor_name";
    private static final String COURSES_AVAILABLE_COLUMN_DEADLINE = "deadline";
    private static final String COURSES_AVAILABLE_COLUMN_START_DATE = "start_date";
    private static final String COURSES_AVAILABLE_COLUMN_SCHEDULE = "schedule";
    private static final String COURSES_AVAILABLE_COLUMN_VENUE = "venue";

    private static final String TABLE_COURSE_APPLICATION = "course_application";
    private static final String COURSE_APPLICATION_COLUMN_ID = "id";
    private static final String COURSE_APPLICATION_COLUMN_COURSE_ID = "course_id";
    private static final String COURSE_APPLICATION_COLUMN_STUDENT_ID = "student_id";
    private static final String COURSE_APPLICATION_COLUMN_STATUS = "status"; // Status: 0 = pending, 1 = approved, 2 = rejected

    private static final String TABLE_COURSE_REGISTRATIONS = "course_registrations";
    private static final String COURSE_REGISTRATIONS_COLUMN_ID = "id";
    private static final String COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID = "trainee_id";
    private static final String COURSE_REGISTRATIONS_COLUMN_COURSE_ID = "course_id";
    private static final String COURSE_REGISTRATIONS_COLUMN_TIMESTAMP = "timestamp";


    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String NOTIFICATIONS_COLUMN_TRAINEE_ID = "trainee_id";
    private static final String NOTIFICATIONS_COLUMN_NOTIFICATION = "notification";
    private static final String NOTIFICATIONS_COLUMN_TIMESTAMP = "timestamp";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountTable = "CREATE TABLE " + TABLE_ACCOUNT + "(" +
                ACCOUNT_COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                ACCOUNT_COLUMN_PASSWORD + " TEXT," +
                ACCOUNT_COLUMN_FIRST_NAME + " TEXT," +
                ACCOUNT_COLUMN_LAST_NAME + " TEXT," +
                ACCOUNT_COLUMN_TYPE + " TEXT)";
        db.execSQL(createAccountTable);

        String createTraineeTable = "CREATE TABLE " + TABLE_TRAINEE + "(" +
                TRAINEE_COLUMN_ID + " TEXT PRIMARY KEY," +
                TRAINEE_COLUMN_MOBILE_NUMBER + " TEXT," +
                TRAINEE_COLUMN_ADDRESS + " TEXT," +
                "FOREIGN KEY (" + TRAINEE_COLUMN_ID + ") REFERENCES " +
                TABLE_ACCOUNT + "(" + ACCOUNT_COLUMN_EMAIL + "))";
        db.execSQL(createTraineeTable);

        String createInstructorTable = "CREATE TABLE " + TABLE_INSTRUCTOR + "(" +
                INSTRUCTOR_COLUMN_ID + " TEXT PRIMARY KEY," +
                INSTRUCTOR_COLUMN_MOBILE_NUMBER + " TEXT," +
                INSTRUCTOR_COLUMN_ADDRESS + " TEXT," +
                INSTRUCTOR_COLUMN_SPECIALIZATION + " TEXT," +
                INSTRUCTOR_COLUMN_DEGREE + " TEXT," +
                INSTRUCTOR_COLUMN_COURSES + " TEXT," +
                "FOREIGN KEY (" + INSTRUCTOR_COLUMN_ID + ") REFERENCES " +
                TABLE_ACCOUNT + "(" + ACCOUNT_COLUMN_EMAIL + "))";
        db.execSQL(createInstructorTable);

        String createCourseTable = "CREATE TABLE " + TABLE_COURSE + "(" +
                COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSE_COLUMN_TITLE + " TEXT," +
                COURSE_COLUMN_TOPICS + " TEXT," +
                COURSE_COLUMN_PREREQUISITES + " TEXT," +
                COURSE_COLUMN_PHOTO + " BLOB)";
        db.execSQL(createCourseTable);

        String createCoursesAvailableTable = "CREATE TABLE " + TABLE_COURSES_AVAILABLE + "(" +
                COURSES_AVAILABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSES_AVAILABLE_COLUMN_COURSE_ID + " INTEGER," +
                COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME + " TEXT," +
                COURSES_AVAILABLE_COLUMN_DEADLINE + " TEXT," +
                COURSES_AVAILABLE_COLUMN_START_DATE + " TEXT," +
                COURSES_AVAILABLE_COLUMN_SCHEDULE + " TEXT," +
                COURSES_AVAILABLE_COLUMN_VENUE + " TEXT," +
                "FOREIGN KEY (" + COURSES_AVAILABLE_COLUMN_COURSE_ID + ") REFERENCES " +
                TABLE_COURSE + "(" + COURSE_COLUMN_ID + "))";
        db.execSQL(createCoursesAvailableTable);

        String createCourseApplicationTable = "CREATE TABLE " + TABLE_COURSE_APPLICATION + "(" +
                COURSE_APPLICATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSE_APPLICATION_COLUMN_COURSE_ID + " INTEGER," +
                COURSE_APPLICATION_COLUMN_STUDENT_ID + " INTEGER," +
                COURSE_APPLICATION_COLUMN_STATUS + " INTEGER DEFAULT 0," +
                "FOREIGN KEY (" + COURSE_APPLICATION_COLUMN_COURSE_ID + ") REFERENCES " +
                TABLE_COURSE + "(" + COURSE_COLUMN_ID + ")," +
                "FOREIGN KEY (" + COURSE_APPLICATION_COLUMN_STUDENT_ID + ") REFERENCES " +
                TABLE_TRAINEE + "(" + TRAINEE_COLUMN_ID + "))";
        db.execSQL(createCourseApplicationTable);

        String createCourseRegistrationsTable = "CREATE TABLE " + TABLE_COURSE_REGISTRATIONS + "(" +
                COURSE_REGISTRATIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID + " TEXT," +
                COURSE_REGISTRATIONS_COLUMN_COURSE_ID + " INTEGER," +
                COURSE_REGISTRATIONS_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (" + COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID + ") REFERENCES " +
                TABLE_TRAINEE + "(" + TRAINEE_COLUMN_ID + ")," +
                "FOREIGN KEY (" + COURSE_REGISTRATIONS_COLUMN_COURSE_ID + ") REFERENCES " +
                TABLE_COURSE + "(" + COURSE_COLUMN_ID + "))";
        db.execSQL(createCourseRegistrationsTable);

        String createNotificationsTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + "(" +
                NOTIFICATIONS_COLUMN_TRAINEE_ID + " TEXT PRIMARY KEY," +
                NOTIFICATIONS_COLUMN_NOTIFICATION + " TEXT," +
                NOTIFICATIONS_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createNotificationsTable);

    }

    public void insertCourse(String title, String topics, String prerequisites, byte[] photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_COLUMN_TITLE, title);
        values.put(COURSE_COLUMN_TOPICS, topics);
        values.put(COURSE_COLUMN_PREREQUISITES, prerequisites);
        values.put(COURSE_COLUMN_PHOTO, photo);
        db.insert(TABLE_COURSE, null, values);
        db.close();
    }

    public void updateCourse(String courseId, String newTitle, String newTopics, String newPrerequisites) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (courseExists(db, courseId)) {
            ContentValues values = new ContentValues();
            values.put("title", newTitle);
            values.put("topics", newTopics);
            values.put("prerequisites", newPrerequisites);

            db.update("course", values, "id = ?", new String[]{courseId});
            db.close();
        } else {
            Log.e("DatabaseHelper", "Course with ID " + courseId + " does not exist");
        }
    }

    public void insertCourseApplication(String courseId, String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_APPLICATION_COLUMN_COURSE_ID, courseId);
        values.put(COURSE_APPLICATION_COLUMN_STUDENT_ID, studentId);
        db.insert(TABLE_COURSE_APPLICATION, null, values);
        db.close();
    }

    public boolean courseExists(SQLiteDatabase db, String courseId) {
        Cursor cursor = db.rawQuery("SELECT 1 FROM course WHERE id = ?", new String[]{courseId});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void removeCourse(String courseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (courseExists(db, courseId)) {
            db.delete(TABLE_COURSE, COURSE_COLUMN_ID + " = ?", new String[]{courseId});

            db.delete(TABLE_COURSES_AVAILABLE, COURSES_AVAILABLE_COLUMN_COURSE_ID + " = ?", new String[]{courseId});
            db.close();
        } else {
            Log.e("DatabaseHelper", "Course with ID " + courseId + " does not exist");
        }
    }

    public void makeCourseAvailable(String courseId, String instructor, String deadline, String startDate, String schedule, String venue) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (courseExists(db, courseId)) {
            ContentValues values = new ContentValues();
            values.put(COURSES_AVAILABLE_COLUMN_COURSE_ID, courseId);
            values.put(COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME, instructor);
            values.put(COURSES_AVAILABLE_COLUMN_DEADLINE, deadline);
            values.put(COURSES_AVAILABLE_COLUMN_START_DATE, startDate);
            values.put(COURSES_AVAILABLE_COLUMN_SCHEDULE, schedule);
            values.put(COURSES_AVAILABLE_COLUMN_VENUE, venue);

            db.insert(TABLE_COURSES_AVAILABLE, null, values);

            ArrayList<String> trainees=getAllTraineeUserIDs();
            for(String trainee:trainees)
                sendNotification(trainee,"Course ID:"+courseId+" is now available!");

            db.close();
        } else {
            Log.e("DatabaseHelper", "Course with ID " + courseId + " does not exist");
            db.close();
        }
    }

    public void insertAdminUser(String email, String firstName, String lastName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ACCOUNT_COLUMN_EMAIL, email);
        values.put(ACCOUNT_COLUMN_FIRST_NAME, firstName);
        values.put(ACCOUNT_COLUMN_LAST_NAME, lastName);
        values.put(ACCOUNT_COLUMN_PASSWORD, password);
        values.put(ACCOUNT_COLUMN_TYPE, "admin");

        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public void insertTraineeUser(String email, String firstName, String lastName, String password, String mobileNumber, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues accountValues = new ContentValues();
        ContentValues traineeValues = new ContentValues();

        accountValues.put(ACCOUNT_COLUMN_EMAIL, email);
        accountValues.put(ACCOUNT_COLUMN_FIRST_NAME, firstName);
        accountValues.put(ACCOUNT_COLUMN_LAST_NAME, lastName);
        accountValues.put(ACCOUNT_COLUMN_PASSWORD, password);
        accountValues.put(ACCOUNT_COLUMN_TYPE, "trainee");
        db.insert(TABLE_ACCOUNT, null, accountValues);

        traineeValues.put(TRAINEE_COLUMN_ID, email);
        traineeValues.put(TRAINEE_COLUMN_MOBILE_NUMBER, mobileNumber);
        traineeValues.put(TRAINEE_COLUMN_ADDRESS, address);
        db.insert(TABLE_TRAINEE, null, traineeValues);

        db.close();
    }

    public void insertInstructorUser(String email, String firstName, String lastName, String password, String mobileNumber, String address, String specialization, String degree, String courses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues accountValues = new ContentValues();
        ContentValues instructorValues = new ContentValues();

        accountValues.put(ACCOUNT_COLUMN_EMAIL, email);
        accountValues.put(ACCOUNT_COLUMN_FIRST_NAME, firstName);
        accountValues.put(ACCOUNT_COLUMN_LAST_NAME, lastName);
        accountValues.put(ACCOUNT_COLUMN_PASSWORD, password);
        accountValues.put(ACCOUNT_COLUMN_TYPE, "instructor");
        db.insert(TABLE_ACCOUNT, null, accountValues);

        instructorValues.put(INSTRUCTOR_COLUMN_ID, email);
        instructorValues.put(INSTRUCTOR_COLUMN_MOBILE_NUMBER, mobileNumber);
        instructorValues.put(INSTRUCTOR_COLUMN_ADDRESS, address);
        instructorValues.put(INSTRUCTOR_COLUMN_SPECIALIZATION, specialization);
        instructorValues.put(INSTRUCTOR_COLUMN_DEGREE, degree);
        instructorValues.put(INSTRUCTOR_COLUMN_COURSES, courses);
        db.insert(TABLE_INSTRUCTOR, null, instructorValues);

        db.close();
    }

    public String searchCourseByName(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "No course with this name is available now";

        String query = "SELECT * FROM " + TABLE_COURSE + " c " +
                "INNER JOIN " + TABLE_COURSES_AVAILABLE + " ca ON c." + COURSE_COLUMN_ID + " = ca." + COURSES_AVAILABLE_COLUMN_COURSE_ID +
                " WHERE c." + COURSE_COLUMN_TITLE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{courseName});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex(COURSE_COLUMN_ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TITLE));
            @SuppressLint("Range") String topics = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TOPICS));
            @SuppressLint("Range") String prerequisites = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_PREREQUISITES));
            @SuppressLint("Range") int coursesAvailableId = cursor.getInt(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_ID));
            @SuppressLint("Range") String instructorId = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME));
            @SuppressLint("Range") String deadline = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_DEADLINE));
            @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_START_DATE));
            @SuppressLint("Range") String schedule = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_SCHEDULE));
            @SuppressLint("Range") String venue = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_VENUE));

            result = "Course ID: " + courseId + "\n" +
                    "Title: " + title + "\n" +
                    "Topics: " + topics + "\n" +
                    "Prerequisites: " + prerequisites + "\n" +
                    "Courses Available ID: " + coursesAvailableId + "\n" +
                    "Instructor: " + instructorId + "\n" +
                    "Deadline: " + deadline + "\n" +
                    "Start Date: " + startDate + "\n" +
                    "Schedule: " + schedule + "\n" +
                    "Venue: " + venue;
        }

        cursor.close();
        db.close();

        return result;
    }


    @SuppressLint("Range")
    public String[] checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] res=new String[2];

        String query = "SELECT type, email FROM account WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            res[0] = cursor.getString(cursor.getColumnIndex("type"));
            res[1] = cursor.getString(cursor.getColumnIndex("email"));
        }

        cursor.close();
        db.close();

        return res;
    }

    public Cursor getAllCourseApplications() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_COURSE_APPLICATION, null);
    }

    @SuppressLint("Range")
    public String getCoursePrerequisites(String courseId) {
        String prerequisites = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COURSE_COLUMN_PREREQUISITES + " FROM " + TABLE_COURSE + " WHERE " + COURSE_COLUMN_ID + " = ?", new String[]{courseId});

        if (cursor.moveToFirst()) {
            prerequisites = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_PREREQUISITES));
        }

        cursor.close();
        db.close();

        return prerequisites;
    }

    public void processCourseApplication(int applicationId, String traineeID, int courseId, boolean accepted) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_COURSE_APPLICATION, COURSE_APPLICATION_COLUMN_ID + " = ?", new String[]{String.valueOf(applicationId)});

        if (accepted) {
            ContentValues values = new ContentValues();
            values.put(COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID, traineeID);
            values.put(COURSE_REGISTRATIONS_COLUMN_COURSE_ID, courseId);
            db.insert(TABLE_COURSE_REGISTRATIONS, null, values);
            sendNotification(traineeID,"Accepted into course ID:"+courseId);
            sendNotification(traineeID,"Your Course ("+courseId+") starts on: "+getCourseStartingDate(courseId));
        }else{
            sendNotification(traineeID,"Rejected from course ID:"+courseId);
        }

        db.close();
    }

    @SuppressLint("Range")
    public String getCourseStartingDate(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String startingDate = null;

        String query = "SELECT " + COURSES_AVAILABLE_COLUMN_START_DATE +
                " FROM " + TABLE_COURSES_AVAILABLE +
                " WHERE " + COURSES_AVAILABLE_COLUMN_COURSE_ID + " = " + courseId;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            startingDate = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_START_DATE));
        }

        cursor.close();
        return startingDate;
    }


    public ArrayList<String> getStudentsByCourseId(String courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> students = new ArrayList<>();

        String query = "SELECT t." + TRAINEE_COLUMN_ID + ", a." + ACCOUNT_COLUMN_FIRST_NAME + ", a." + ACCOUNT_COLUMN_LAST_NAME +
                " FROM " + TABLE_COURSE_REGISTRATIONS + " cr " +
                " INNER JOIN " + TABLE_TRAINEE + " t ON cr." + COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID + " = t." + TRAINEE_COLUMN_ID +
                " INNER JOIN " + TABLE_ACCOUNT + " a ON t." + TRAINEE_COLUMN_ID + " = a." + ACCOUNT_COLUMN_EMAIL +
                " WHERE cr." + COURSE_REGISTRATIONS_COLUMN_COURSE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{courseId});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex(TRAINEE_COLUMN_ID));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));
                String fullName = firstName + " " + lastName;
                students.add(fullName + " (" + studentId + ")");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return students;
    }

    public ArrayList<String> getTraineeNotifications(String traineeId) {
        ArrayList<String> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + NOTIFICATIONS_COLUMN_NOTIFICATION + " FROM " + TABLE_NOTIFICATIONS +
                " WHERE " + NOTIFICATIONS_COLUMN_TRAINEE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{traineeId});

        if (cursor.moveToFirst()) {
            int notificationIndex = cursor.getColumnIndex(NOTIFICATIONS_COLUMN_NOTIFICATION);

            do {
                String notificationString = cursor.getString(notificationIndex);

                String[] notificationLines = notificationString.split("\\n");

                Collections.addAll(notifications, notificationLines);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notifications;
    }


    public ArrayList<String> getAllUsersInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> usersList = new ArrayList<>();

        String query = "SELECT " + ACCOUNT_COLUMN_FIRST_NAME + ", " +
                ACCOUNT_COLUMN_LAST_NAME + ", " +
                ACCOUNT_COLUMN_TYPE + ", " +
                ACCOUNT_COLUMN_EMAIL +
                " FROM " + TABLE_ACCOUNT +
                " WHERE " + ACCOUNT_COLUMN_TYPE + " = 'trainee' OR " +
                ACCOUNT_COLUMN_TYPE + " = 'instructor'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_TYPE));
                @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_EMAIL));

                String userInfo = firstName + " " + lastName + " (" + type + ") - ID:" + userId;
                usersList.add(userInfo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return usersList;
    }

    public String getUserInformation(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userInfo = "";

        String userQuery = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + ACCOUNT_COLUMN_EMAIL + " = ?";
        Cursor userCursor = db.rawQuery(userQuery, new String[]{userEmail});
        if (userCursor.moveToFirst()) {
            @SuppressLint("Range") String email = userCursor.getString(userCursor.getColumnIndex(ACCOUNT_COLUMN_EMAIL));
            @SuppressLint("Range") String firstName = userCursor.getString(userCursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = userCursor.getString(userCursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));
            @SuppressLint("Range") String userType = userCursor.getString(userCursor.getColumnIndex(ACCOUNT_COLUMN_TYPE));

            userInfo += firstName+"'s Information:\n";
            userInfo += "Email: " + email + "\n";
            userInfo += "First Name: " + firstName + "\n";
            userInfo += "Last Name: " + lastName + "\n";
            userInfo += "User Type: " + userType + "\n";

            if (userType.equals("trainee")) {
                String traineeQuery = "SELECT * FROM " + TABLE_TRAINEE + " WHERE " + TRAINEE_COLUMN_ID + " = ?";
                Cursor traineeCursor = db.rawQuery(traineeQuery, new String[]{email});
                if (traineeCursor.moveToFirst()) {
                    @SuppressLint("Range") String mobileNumber = traineeCursor.getString(traineeCursor.getColumnIndex(TRAINEE_COLUMN_MOBILE_NUMBER));
                    @SuppressLint("Range") String address = traineeCursor.getString(traineeCursor.getColumnIndex(TRAINEE_COLUMN_ADDRESS));

                    userInfo += "Mobile Number: " + mobileNumber + "\n";
                    userInfo += "Address: " + address + "\n";
                }
                traineeCursor.close();
            } else if (userType.equals("instructor")) {
                String instructorQuery = "SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " + INSTRUCTOR_COLUMN_ID + " = ?";
                Cursor instructorCursor = db.rawQuery(instructorQuery, new String[]{email});
                if (instructorCursor.moveToFirst()) {
                    @SuppressLint("Range") String mobileNumber = instructorCursor.getString(instructorCursor.getColumnIndex(INSTRUCTOR_COLUMN_MOBILE_NUMBER));
                    @SuppressLint("Range") String address = instructorCursor.getString(instructorCursor.getColumnIndex(INSTRUCTOR_COLUMN_ADDRESS));
                    @SuppressLint("Range") String specialization = instructorCursor.getString(instructorCursor.getColumnIndex(INSTRUCTOR_COLUMN_SPECIALIZATION));
                    @SuppressLint("Range") String degree = instructorCursor.getString(instructorCursor.getColumnIndex(INSTRUCTOR_COLUMN_DEGREE));
                    @SuppressLint("Range") String courses = instructorCursor.getString(instructorCursor.getColumnIndex(INSTRUCTOR_COLUMN_COURSES));

                    userInfo += "Mobile Number: " + mobileNumber + "\n";
                    userInfo += "Address: " + address + "\n";
                    userInfo += "Specialization: " + specialization + "\n";
                    userInfo += "Degree: " + degree + "\n";
                    userInfo += "Courses: " + courses + "\n";
                }
                instructorCursor.close();
            }
        }
        userCursor.close();
        db.close();

        return userInfo;
    }

    public ArrayList<String> getAllTraineeUserIDs() {
        ArrayList<String> traineeUserIDs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TRAINEE_COLUMN_ID + " FROM " + TABLE_TRAINEE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String traineeUserID = cursor.getString(cursor.getColumnIndex(TRAINEE_COLUMN_ID));
                traineeUserIDs.add(traineeUserID);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return traineeUserIDs;
    }

    public ArrayList<String> getTraineeInformation(String traineeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> traineeInfo = new ArrayList<>();

        String selectQuery = "SELECT " + ACCOUNT_COLUMN_FIRST_NAME + ", " +
                ACCOUNT_COLUMN_LAST_NAME + ", " +
                TRAINEE_COLUMN_MOBILE_NUMBER + ", " +
                TRAINEE_COLUMN_ADDRESS +
                " FROM " + TABLE_ACCOUNT + " INNER JOIN " + TABLE_TRAINEE +
                " ON " + TABLE_ACCOUNT + "." + ACCOUNT_COLUMN_EMAIL + " = " +
                TABLE_TRAINEE + "." + TRAINEE_COLUMN_ID +
                " WHERE " + TABLE_ACCOUNT + "." + ACCOUNT_COLUMN_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{traineeId});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(TRAINEE_COLUMN_MOBILE_NUMBER));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(TRAINEE_COLUMN_ADDRESS));

            traineeInfo.add(firstName);
            traineeInfo.add(lastName);
            traineeInfo.add(phoneNumber);
            traineeInfo.add(address);
        }

        cursor.close();
        db.close();

        return traineeInfo;
    }


    @SuppressLint("Range")
    public void sendNotification(String traineeId, String notification) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS +
                " WHERE " + NOTIFICATIONS_COLUMN_TRAINEE_ID + " = ?", new String[]{traineeId});
        String existingNotifications = "";
        if (cursor.moveToFirst()) {
            existingNotifications = cursor.getString(cursor.getColumnIndex(NOTIFICATIONS_COLUMN_NOTIFICATION));
        }
        cursor.close();

        String concatenatedNotifications = existingNotifications + "\n" + notification;

        ContentValues values = new ContentValues();
        values.put(NOTIFICATIONS_COLUMN_TRAINEE_ID, traineeId);
        values.put(NOTIFICATIONS_COLUMN_NOTIFICATION, concatenatedNotifications);
        db.replace(TABLE_NOTIFICATIONS, null, values);

        db.close();
    }

    public String getRegisteredCoursesForTrainee(String traineeId) {
        StringBuilder registeredCourses = new StringBuilder();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_COURSE + "." + COURSE_COLUMN_TITLE + ", " +
                TABLE_COURSE + "." + COURSE_COLUMN_TOPICS + ", " +
                TABLE_COURSE + "." + COURSE_COLUMN_PREREQUISITES + ", " +
                TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME + ", " +
                TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_VENUE + ", " +
                TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_START_DATE + ", " +
                TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_SCHEDULE +
                " FROM " + TABLE_COURSE +
                " INNER JOIN " + TABLE_COURSE_REGISTRATIONS +
                " ON " + TABLE_COURSE + "." + COURSE_COLUMN_ID +
                " = " + TABLE_COURSE_REGISTRATIONS + "." + COURSE_REGISTRATIONS_COLUMN_COURSE_ID +
                " INNER JOIN " + TABLE_COURSES_AVAILABLE +
                " ON " + TABLE_COURSE + "." + COURSE_COLUMN_ID +
                " = " + TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_COURSE_ID +
                " WHERE " + TABLE_COURSE_REGISTRATIONS + "." + COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID +
                " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{traineeId});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String courseTitle = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TITLE));
                @SuppressLint("Range") String courseTopics = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TOPICS));
                @SuppressLint("Range") String instructorName = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME));
                @SuppressLint("Range") String venue = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_VENUE));
                @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_START_DATE));
                @SuppressLint("Range") String schedule = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_SCHEDULE));

                registeredCourses.append("Title: ").append(courseTitle).append("\n");
                registeredCourses.append("Topics: ").append(courseTopics).append("\n");
                registeredCourses.append("Instructor: ").append(instructorName).append("\n");
                registeredCourses.append("Venue: ").append(venue).append("\n");
                registeredCourses.append("Start Date: ").append(startDate).append("\n");
                registeredCourses.append("Schedule: ").append(schedule).append("\n\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registeredCourses.toString().trim();
    }

    public void updateTraineeData(String traineeId, String newFirstName, String newLastName, String newPhoneNumber, String newAddress) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TRAINEE_COLUMN_MOBILE_NUMBER, newPhoneNumber);
        values.put(DatabaseHelper.TRAINEE_COLUMN_ADDRESS, newAddress);

        String whereClause = DatabaseHelper.TRAINEE_COLUMN_ID + " = ?";
        String[] whereArgs = { traineeId };

        db.update(DatabaseHelper.TABLE_TRAINEE, values, whereClause, whereArgs);

        ContentValues accountValues = new ContentValues();
        accountValues.put(DatabaseHelper.ACCOUNT_COLUMN_FIRST_NAME, newFirstName);
        accountValues.put(DatabaseHelper.ACCOUNT_COLUMN_LAST_NAME, newLastName);

        String accountWhereClause = DatabaseHelper.ACCOUNT_COLUMN_EMAIL + " = ?";
        String[] accountWhereArgs = { traineeId };

        db.update(DatabaseHelper.TABLE_ACCOUNT, accountValues, accountWhereClause, accountWhereArgs);

        db.close();
    }

    public String getFullNameForAccountId(String accountId) {
        String fullName = null;
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + ACCOUNT_COLUMN_FIRST_NAME + ", " + ACCOUNT_COLUMN_LAST_NAME +
                " FROM " + TABLE_ACCOUNT +
                " WHERE " + ACCOUNT_COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{accountId});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));
            fullName = firstName + " " + lastName;
        }

        cursor.close();
        db.close();

        return fullName;
    }

    public ArrayList<Integer> getCoursesTaughtByInstructor(String ID) {
        String fullName=getFullNameForAccountId(ID);
        ArrayList<Integer> courseIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COURSES_AVAILABLE_COLUMN_ID +
                " FROM " + TABLE_COURSES_AVAILABLE +
                " WHERE " + COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{fullName});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_ID));
                courseIds.add(courseId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courseIds;
    }

    public String getInstructorSchedule(String ID) {
        ArrayList<Integer> availabilityIds = getCoursesTaughtByInstructor(ID);
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = this.getReadableDatabase();

        for (int availabilityId : availabilityIds) {
            String query = "SELECT " + TABLE_COURSE + "." + COURSE_COLUMN_TITLE + ", " +
                    TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_SCHEDULE +
                    " FROM " + TABLE_COURSE +
                    " INNER JOIN " + TABLE_COURSES_AVAILABLE +
                    " ON " + TABLE_COURSE + "." + COURSE_COLUMN_ID +
                    " = " + TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_COURSE_ID +
                    " WHERE " + TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_ID + " = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(availabilityId)});

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String courseTitle = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TITLE));
                @SuppressLint("Range") String courseSchedule = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_SCHEDULE));
                result.append("Course: ").append(courseTitle).append("\nSchedule: ").append(courseSchedule).append("\n\n");
            }

            cursor.close();
        }

        db.close();

        return result.toString();
    }

    public String getInstructorTrainees(String instructorID) {
        String instructor=getFullNameForAccountId(instructorID);
        ArrayList<String> trainees=getAllTraineeUserIDs();
        StringBuilder myTrainees= new StringBuilder();

        for(String traineeId:trainees) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT " +
                    TABLE_COURSE + "." + COURSE_COLUMN_TITLE + ", " +
                    TABLE_COURSE + "." + COURSE_COLUMN_TOPICS + ", " +
                    TABLE_COURSE + "." + COURSE_COLUMN_PREREQUISITES + ", " +
                    TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME + ", " +
                    TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_VENUE + ", " +
                    TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_START_DATE + ", " +
                    TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_SCHEDULE +
                    " FROM " + TABLE_COURSE +
                    " INNER JOIN " + TABLE_COURSE_REGISTRATIONS +
                    " ON " + TABLE_COURSE + "." + COURSE_COLUMN_ID +
                    " = " + TABLE_COURSE_REGISTRATIONS + "." + COURSE_REGISTRATIONS_COLUMN_COURSE_ID +
                    " INNER JOIN " + TABLE_COURSES_AVAILABLE +
                    " ON " + TABLE_COURSE + "." + COURSE_COLUMN_ID +
                    " = " + TABLE_COURSES_AVAILABLE + "." + COURSES_AVAILABLE_COLUMN_COURSE_ID +
                    " WHERE " + TABLE_COURSE_REGISTRATIONS + "." + COURSE_REGISTRATIONS_COLUMN_TRAINEE_ID +
                    " = ?";

            Cursor cursor = db.rawQuery(query, new String[]{traineeId});

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String courseTitle = cursor.getString(cursor.getColumnIndex(COURSE_COLUMN_TITLE));
                    @SuppressLint("Range") String instructorName = cursor.getString(cursor.getColumnIndex(COURSES_AVAILABLE_COLUMN_INSTRUCTOR_NAME));

                    if (instructorName.equals(instructor)) {
                        String traineeName=getFullNameForAccountId(traineeId);
                        myTrainees.append(traineeName).append(" studies:");
                        myTrainees.append(courseTitle).append("\n");
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }

        return myTrainees.toString().trim();
    }

    public ArrayList<String> getInstructorData(String instructorEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> instructorData = new ArrayList<>();

        String[] accountColumns = {
                ACCOUNT_COLUMN_FIRST_NAME,
                ACCOUNT_COLUMN_LAST_NAME
        };
        Cursor accountCursor = db.query(
                TABLE_ACCOUNT,
                accountColumns,
                ACCOUNT_COLUMN_EMAIL + " = ?",
                new String[]{instructorEmail},
                null,
                null,
                null
        );

        if (accountCursor.moveToFirst()) {
            @SuppressLint("Range") String firstName = accountCursor.getString(accountCursor.getColumnIndex(ACCOUNT_COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = accountCursor.getString(accountCursor.getColumnIndex(ACCOUNT_COLUMN_LAST_NAME));

            instructorData.add(firstName);
            instructorData.add(lastName);
        }

        accountCursor.close();

        String[] instructorColumns = {
                INSTRUCTOR_COLUMN_MOBILE_NUMBER,
                INSTRUCTOR_COLUMN_ADDRESS,
                INSTRUCTOR_COLUMN_SPECIALIZATION,
                INSTRUCTOR_COLUMN_DEGREE,
                INSTRUCTOR_COLUMN_COURSES
        };
        Cursor instructorCursor = db.query(
                TABLE_INSTRUCTOR,
                instructorColumns,
                INSTRUCTOR_COLUMN_ID + " = ?",
                new String[]{instructorEmail},
                null,
                null,
                null
        );

        if (instructorCursor.moveToFirst()) {
            for (int i = 0; i < instructorCursor.getColumnCount(); i++) {
                instructorData.add(instructorCursor.getString(i));
            }
        }

        instructorCursor.close();
        db.close();

        return instructorData;
    }

    public void updateInstructorInformation(String instructorId, String newFirstName, String newLastName, String newPhone, String newAddress, String newSpecialization, String newDegree, String newCourses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues accountValues = new ContentValues();
        ContentValues instructorValues = new ContentValues();

        accountValues.put(ACCOUNT_COLUMN_FIRST_NAME, newFirstName);
        accountValues.put(ACCOUNT_COLUMN_LAST_NAME, newLastName);
        db.update(TABLE_ACCOUNT, accountValues, ACCOUNT_COLUMN_EMAIL + "=?", new String[]{instructorId});

        instructorValues.put(INSTRUCTOR_COLUMN_MOBILE_NUMBER, newPhone);
        instructorValues.put(INSTRUCTOR_COLUMN_ADDRESS, newAddress);
        instructorValues.put(INSTRUCTOR_COLUMN_SPECIALIZATION, newSpecialization);
        instructorValues.put(INSTRUCTOR_COLUMN_DEGREE, newDegree);
        instructorValues.put(INSTRUCTOR_COLUMN_COURSES, newCourses);
        db.update(TABLE_INSTRUCTOR, instructorValues, INSTRUCTOR_COLUMN_ID + "=?", new String[]{instructorId});

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES_AVAILABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
        onCreate(db);
    }

}
