package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.CheckPoint;

import java.util.List;

public interface ICheckpointDAO {
    void addCheckpoint(CheckPoint checkpoint);
    CheckPoint getCheckpointById(String checkpointId);
    List<CheckPoint> getAllCheckpoints();
    void updateCheckpoint(CheckPoint checkpoint);
}
