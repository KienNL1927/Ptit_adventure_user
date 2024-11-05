package com.example.ptitadventure.activity;

import static android.os.Build.VERSION_CODES.S;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.ptitadventure.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackbar {

    @SuppressLint("RestrictedApi")
    public static void make(View view, String message, int duration, int iconResId) {
        Snackbar snackbar = Snackbar.make(view, "", duration);

        View snackView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_snackbar, null);
        TextView textView = snackView.findViewById(R.id.snackbar_text);
        ImageView iconView = snackView.findViewById(R.id.snackbar_icon);

        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        textView.setText(message);
        iconView.setImageResource(iconResId);

        layout.setPadding(0, 0, 0, 0);
        layout.addView(snackView, 0);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
        params.gravity = android.view.Gravity.TOP;
        layout.setLayoutParams(params);

        snackbar.show();
    }
}