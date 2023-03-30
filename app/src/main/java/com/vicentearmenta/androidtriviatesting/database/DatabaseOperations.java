package com.vicentearmenta.androidtriviatesting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vicentearmenta.androidtriviatesting.models.Answer;
import com.vicentearmenta.androidtriviatesting.models.Question;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private SQLiteDatabase mDatabase;

    private final DatabaseHelper mHelper;

    String TAG = "DatabaseOperations";

    public DatabaseOperations(Context context){
        mHelper = new DatabaseHelper(context);
        this.open();
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close(){
        if (mDatabase != null && mDatabase.isOpen()){
            mDatabase.close();
        }
    }

    public String insertUsername(String username){
        if (!mDatabase.isOpen()){
            this.open();
        }

        mDatabase.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_RS_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_RS_SCORE, 0);
        long lastRowID = mDatabase.insert(DatabaseHelper.TABLE_RESULT,
                null, values);

        mDatabase.setTransactionSuccessful();

        mDatabase.endTransaction();

        this.close();
        return  Long.toString(lastRowID);
    }

    public int updateScore(String userID){
        if (!mDatabase.isOpen()){
            this.open();
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_RS_SCORE,
                DatabaseHelper.COLUMN_RS_SCORE + " + 1");
        int rowsUpdated = mDatabase.update(
                DatabaseHelper.TABLE_RESULT,
                values,
                DatabaseHelper.COLUMN_RS_ID + " = ?",
                new String[]{ userID });

        this.close();

        return rowsUpdated;
    }

    public Question getNextQuestion(String questionsAlreadyAsked) {
        if (!mDatabase.isOpen()) {
            this.open();
        }

        String questionId = null;
        String questionText = null;
        String questionAnswer = null;

        Log.d(TAG, " question not already asked");
        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_QUESTION,
                new String[]{
                        DatabaseHelper.COLUMN_QT_ID,
                        DatabaseHelper.COLUMN_QT_TEXT,
                        DatabaseHelper.COLUMN_QT_ANSWER
                },
                DatabaseHelper.COLUMN_QT_ID + "NOT IN ( ? )",
                new String[]{ questionsAlreadyAsked },
                null,
                null,
                "RANDOM()",
                "1"
        );

        while(cursor.moveToNext()) {
            questionId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QT_ID));
            questionText = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QT_TEXT));
            questionAnswer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QT_ANSWER));
        }

        cursor.close();

        List<Answer> options = new ArrayList<>();

        // Opcion A - D que es la opcion correcta
        Log.d(TAG, " correct option");
        cursor = mDatabase.query(
                DatabaseHelper.TABLE_ANSWER,
                new String[]{
                        DatabaseHelper.COLUMN_AW_ID,
                        DatabaseHelper.COLUMN_AW_TEXT
                },
                DatabaseHelper.COLUMN_AW_ID + " = ? ",
                new String[]{ questionAnswer },
                null,
                null,
                null
        );

        while(cursor.moveToNext()) {
            Answer option = new Answer();
            option.setAnswerId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AW_ID)));
            option.setAnswerId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AW_TEXT)));

            options.add(option);
        }

        cursor.close();

        // opciones restantes
        Log.d(TAG, " options left");
        cursor = mDatabase.query(
                DatabaseHelper.TABLE_ANSWER,
                new String[]{
                        DatabaseHelper.COLUMN_AW_ID,
                        DatabaseHelper.COLUMN_AW_TEXT
                },
                DatabaseHelper.COLUMN_AW_ID + " NOT IN ( ? )",
                new String[]{ questionAnswer },
                null,
                null,
                "RANDOM()",
                "3"
        );

        while(cursor.moveToNext()) {
            Answer option = new Answer();
            option.setAnswerId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AW_ID)));
            option.setAnswerId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AW_TEXT)));

            options.add(option);
        }

        return new Question(
                questionId,
                questionText,
                questionAnswer,
                options.get(0),
                options.get(1),
                options.get(2),
                options.get(3)
        );
    }
}
