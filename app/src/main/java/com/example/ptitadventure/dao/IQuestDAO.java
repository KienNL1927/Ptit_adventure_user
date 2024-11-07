package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.Quest;

import java.util.List;

public interface IQuestDAO {
    public List<Quest> getMainQuest();
    public List<Quest> getSubQuest(int parent_id);

    public int getQuestCompleted(int studentID, int questID);
    public int getTotalQuest(int questID);
}
