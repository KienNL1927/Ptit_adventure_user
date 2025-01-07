package com.example.ptitadventure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.ptitadventure.DTO.AllStudentDTO;
import com.example.ptitadventure.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<AllStudentDTO> {
    private List<AllStudentDTO> studentList; // Original full student list
    private List<AllStudentDTO> displayedStudentList; // List used for displaying filtered data
    private Context context; // Context for adapter

    // Constructor for the adapter
    public StudentAdapter(Context context, List<AllStudentDTO> studentList) {
        super(context, 0, studentList);
        this.context = context;
        this.studentList = new ArrayList<>(studentList); // Make a copy of the list
        this.displayedStudentList = new ArrayList<>(studentList); // Initialize the displayed list
    }

    @Override
    public int getCount() {
        return displayedStudentList.size(); // Return size of displayed list
    }

    @Override
    public AllStudentDTO getItem(int position) {
        return displayedStudentList.get(position); // Get item from displayed list
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the view is null, if so, inflate it
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_other, parent, false);
        }

        // Get the student at the specified position
        AllStudentDTO student = getItem(position);

        // Find the TextView for displaying the student's name
        TextView username = convertView.findViewById(R.id.text_user_name);

        // Set the student's name in the TextView
        if (student != null && student.getName() != null) {
            username.setText(student.getName());
        } else {
            username.setText("Unknown Student");
        }

        // Return the modified view
        return convertView;
    }

    // Method to update the displayed list and refresh the view
    public void updateList(List<AllStudentDTO> newList) {
        displayedStudentList.clear(); // Clear the displayed list
        displayedStudentList.addAll(newList); // Add all items from the new list
        notifyDataSetChanged(); // Notify the adapter that data has changed
    }

    public void updateFullList(List<AllStudentDTO> newStudentList) {
        studentList.clear(); // Clear the original list
        studentList.addAll(newStudentList); // Add all items from the new list
        resetList(); // Reset the displayed list to the updated full list
    }

    // Method to reset the displayed list to the original full list
    public void resetList() {
        displayedStudentList.clear(); // Clear current displayed list
        displayedStudentList.addAll(studentList); // Restore original list
        notifyDataSetChanged(); // Notify adapter to refresh the view
        Log.d("StudentAdapter", "Resetting List: " + displayedStudentList.size());
    }
}
