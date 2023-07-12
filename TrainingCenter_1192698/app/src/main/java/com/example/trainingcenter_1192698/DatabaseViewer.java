package com.example.trainingcenter_1192698;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseViewer {
    private static final String TAG = "DatabaseViewer";
    private DatabaseHelper dbHelper;

    public DatabaseViewer(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void displayAllTables() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get all table names
        Cursor cursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String tableName = cursor.getString(0);
                Log.d(TAG, "Table Name: " + tableName);
                displayTableData(db, tableName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    private void displayTableData(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        int columnCount = cursor.getColumnCount();

        Log.d(TAG, "Table Data: " + tableName);

        // Print column names
        StringBuilder columnNamesBuilder = new StringBuilder();
        for (int i = 0; i < columnCount; i++) {
            columnNamesBuilder.append(cursor.getColumnName(i)).append("\t\t");
        }
        Log.d(TAG, columnNamesBuilder.toString());

        // Print data rows
        while (cursor.moveToNext()) {
            StringBuilder rowDataBuilder = new StringBuilder();
            for (int i = 0; i < columnCount; i++) {
                rowDataBuilder.append(cursor.getString(i)).append("\t\t");
            }
            Log.d(TAG, rowDataBuilder.toString());
        }

        cursor.close();
    }
}
