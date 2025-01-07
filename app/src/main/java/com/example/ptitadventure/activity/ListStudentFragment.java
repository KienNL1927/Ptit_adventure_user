package com.example.ptitadventure.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ptitadventure.DTO.AllStudentDTO;
import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.StudentAdapter;
import com.example.ptitadventure.api.ApiUserService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.UserRepo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStudentFragment extends Fragment {
    private EditText searchBar;
    private ListView listView;
    private StudentAdapter adapter;
    private List<AllStudentDTO> studentList = new ArrayList<>();
    private UserRepo userRepo;
    private String studentRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_student, container, false);
        studentRole = getActivity().getIntent().getStringExtra("role");

        // Initialize search bar and list view
        searchBar = view.findViewById(R.id.search_bar);
        listView = view.findViewById(R.id.list_view);

        // Check if user is staff
        if ("staff".equals(studentRole)) {
            // Initialize userRepo and set up adapter
            userRepo = new UserRepo(Client.getClient().create(ApiUserService.class));
            adapter = new StudentAdapter(requireContext(), studentList);
            listView.setAdapter(adapter);
            fetchStudentList(); // Fetch student list

            // Set up text watcher for search bar
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filter(s.toString()); // Filter list based on search input
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                AllStudentDTO student = adapter.getItem(position);
                if (student != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("studentId", student.getId());
                    NavController navController = Navigation.findNavController(view1);
                    navController.navigate(R.id.action_list_student_to_profile, bundle);
                } else {
                    Log.e("ListStudentFragment", "Student is null at position: " + position);
                }
            });


        } else {
            // If user is not staff, hide list view and show snackbar message
            view.findViewById(R.id.list_view).setVisibility(View.GONE);
            showSnackbar("Bạn không có quyền truy cập danh sách sinh viên");
        }

        return view;
    }


    // Fetch student list from the server
    private void fetchStudentList() {
        userRepo.getStudentList(new Callback<List<AllStudentDTO>>() {
            @Override
            public void onResponse(Call<List<AllStudentDTO>> call, Response<List<AllStudentDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studentList.clear();
                    for (AllStudentDTO student : response.body()) {
                        if (student != null && student.getName() != null) {
                            studentList.add(student);
                        }
                    }
                    Log.d("ListStudentFragment", "Student List Fetched: " + studentList.size());
                    sortByLastName(studentList); // Sort the list by last name
                    adapter.updateFullList(studentList); // Sync adapter with the updated full list
                } else {
                    showSnackbar("Không thể tải danh sách sinh viên");
                }
            }

            @Override
            public void onFailure(Call<List<AllStudentDTO>> call, Throwable t) {
                showSnackbar("Có lỗi xảy ra khi kết nối với server");
            }
        });
    }


    // Display a snackbar message
    private void showSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    // Filter student list based on search query
    private void filter(String text) {
        if (text.isEmpty()) {
            List<AllStudentDTO> sortedList = new ArrayList<>(studentList);
            sortByLastName(sortedList);
            adapter.updateList(sortedList); // Update the adapter with the sorted list
        } else {
            // Filter the student list based on the search input
            List<AllStudentDTO> filteredList = new ArrayList<>();
            for (AllStudentDTO student : studentList) {
                if (student.getName() != null && student.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(student);
                }
            }
            sortByLastName(filteredList); // Sort the filtered list by last name
            adapter.updateList(filteredList); // Update adapter with filtered list
        }
    }

    // Method to sort the student list by last name
    private void sortByLastName(List<AllStudentDTO> list) {
        Collections.sort(list, Comparator.comparing(student -> getLastName(student.getName()), String.CASE_INSENSITIVE_ORDER));
    }

    private String getLastName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return "";
        }
        String[] parts = fullName.split(" ");
        return parts.length > 1 ? parts[parts.length - 1] : parts[0];
    }
}