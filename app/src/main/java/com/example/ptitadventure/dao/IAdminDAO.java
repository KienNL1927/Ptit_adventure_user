package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.Staff;

public interface IAdminDAO {
    void addAdmin(Staff admin);
    Staff getAdminById(String teacherId);
}
