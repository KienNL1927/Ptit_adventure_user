package com.example.ptitadventure.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ptitadventure.R;
import com.example.ptitadventure.model.Subtask;

public class SubtaskInstructionsDialog extends Dialog {

    private Subtask subtask;
    private OnNfcTagScannedListener listener;

    public SubtaskInstructionsDialog(@NonNull Context context, Subtask subtask) {
        super(context);
        this.subtask = subtask;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_subtask_instructions);

        TextView textInstructions = findViewById(R.id.text_instructions);
        Button buttonScanNfc = findViewById(R.id.button_scan_nfc);

        textInstructions.setText(subtask.getDescription());

        buttonScanNfc.setOnClickListener(v -> {
            // Implement NFC scanning logic here
            // For now, we'll just simulate a successful scan
            if (listener != null) {
                listener.onNfcTagScanned();
            }
            dismiss();
        });
    }

    public void setOnNfcTagScannedListener(OnNfcTagScannedListener listener) {
        this.listener = listener;
    }

    public interface OnNfcTagScannedListener {
        void onNfcTagScanned();
    }
}