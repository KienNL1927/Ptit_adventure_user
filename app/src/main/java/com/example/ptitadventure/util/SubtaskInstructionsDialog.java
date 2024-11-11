package com.example.ptitadventure.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;

import com.example.ptitadventure.R;
import com.example.ptitadventure.model.Quest;

public class SubtaskInstructionsDialog extends Dialog {

    private Quest subtask;
    private OnNfcScanRequestListener listener;
    private Context context;
    private TextView textInstructions;
    private Button buttonScanNfc;
    private ProgressBar progressBarNfc;
    private TextView textNfcStatus;

    public SubtaskInstructionsDialog(@NonNull Context context, Quest subtask) {
        super(context);
        this.subtask = subtask;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_subtask_instructions);

        textInstructions = findViewById(R.id.text_instructions);
        buttonScanNfc = findViewById(R.id.button_scan_nfc);
        progressBarNfc = findViewById(R.id.progress_bar_nfc);
        textNfcStatus = findViewById(R.id.text_nfc_status);

        textInstructions.setText(subtask.getDescription());

        buttonScanNfc.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNfcScanRequested();
                showNfcScanningState(true);
            }
        });
    }

    public void showNfcScanningState(boolean isScanning) {
        if (isScanning) {
            buttonScanNfc.setVisibility(View.GONE);
            progressBarNfc.setVisibility(View.VISIBLE);
            textNfcStatus.setVisibility(View.VISIBLE);
            textNfcStatus.setText("Đang chờ quét NFC...");
        } else {
            buttonScanNfc.setVisibility(View.VISIBLE);
            progressBarNfc.setVisibility(View.GONE);
            textNfcStatus.setVisibility(View.GONE);
        }
    }

    public void setOnNfcScanRequestListener(OnNfcScanRequestListener listener) {
        this.listener = listener;
    }

    public interface OnNfcScanRequestListener {
        void onNfcScanRequested();
    }
}