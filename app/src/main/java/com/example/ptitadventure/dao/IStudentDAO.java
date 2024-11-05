package com.example.ptitadventure.dao;

import com.example.ptitadventure.model.GameProgress;
import com.example.ptitadventure.model.Student;

import java.util.List;

public interface IStudentDAO {
    void addStudent(Student student);
    Student getStudentById(String studentId);
    List<Student> getAllStudents();
    void updateStudentProgress(String studentId, GameProgress progress);
}
