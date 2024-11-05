package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.User;

public interface IUserDAO {
    User getUserByUsername(String username);
    void updateUser(User user);
    void changePassword(String username, String newPassword);
}


