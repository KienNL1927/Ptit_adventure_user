package com.example.ptitadventure.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ptitadventure.model.Quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestDAO implements com.example.ptitadventure.dao.IQuestDAO {

    private DBHelper dbHelper;

    public QuestDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    @Override
    public List<Quest> getMainQuest() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM quest WHERE parent_id IS NULL";

        Cursor cursor = db.rawQuery(query, null);
        List<Quest> quests = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1;
            int parent_id = cursor.getInt(cursor.getColumnIndexOrThrow("parent_id"));
            Quest quest = new Quest(id, name, description, status, parent_id);
            /*String q2 = "SELECT * FROM location" +
                    "INNER JOIN LOCATION ON quest.id = LOCATION.quest_id" +
                    "WHERE quest.id = " + id;
            Cursor cursor2 = db.rawQuery(q2, null);
            while (cursor2.moveToNext()) {
                int location_id = cursor2.getInt(cursor2.getColumnIndexOrThrow("location_id"));
                String location_name = cursor2.getString(cursor2.getColumnIndexOrThrow("location_name"));
                String location_description = cursor2.getString(cursor2.getColumnIndexOrThrow("location_description"));
                String location_image = cursor2.getString(cursor2.getColumnIndexOrThrow("location_image"));
            }*/
            quests.add(quest);
        }
        return quests;
    }

    @Override
    public List<Quest> getSubQuest(int parent_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM quest WHERE parent_id = " + parent_id;
        Cursor cursor = db.rawQuery(query, null);
        List<Quest> quests = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1;
            Quest quest = new Quest(id, name, description, status, parent_id);
            quests.add(quest);
        }
        return quests;
    }

    @Override
    public int getQuestCompleted(int id, int questID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT (*) FROM quest " +
                "INNER JOIN student_quest ON quest.id = student_quest.quest_id " +
                "WHERE " + "quest.parent_id = " + questID;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    @Override
    public int getTotalQuest(int questID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT (*) FROM quest WHERE parent_id = " + questID;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}
