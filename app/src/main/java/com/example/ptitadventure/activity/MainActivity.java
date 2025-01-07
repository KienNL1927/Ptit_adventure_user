package com.example.ptitadventure.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ptitadventure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String role = getIntent().getStringExtra("role");
        if (role.equals("student")) {
            setContentView(R.layout.activity_main);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_student);

            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
            } else {
                throw new IllegalStateException("NavHostFragment not found in activity_main.xml");
            }
        } else {
            SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("role", role);
            editor.apply();

            setContentView(R.layout.activity_main_staff);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_staff);

            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_staff);

            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
            } else {
                throw new IllegalStateException("NavHostFragment not found in activity_main.xml");
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (AppBarConfiguration) null) || super.onSupportNavigateUp();
    }
}
