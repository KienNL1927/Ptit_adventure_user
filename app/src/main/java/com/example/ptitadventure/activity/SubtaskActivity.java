package com.example.ptitadventure.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.SubtaskAdapter;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.dao.StudentQuestRepo;
import com.example.ptitadventure.model.Quest;
import com.example.ptitadventure.util.SubtaskInstructionsDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubtaskActivity extends AppCompatActivity implements SubtaskAdapter.OnSubtaskClickListener, QuestCompletionDialog.OnQuestCompletionListener, SubtaskInstructionsDialog.OnNfcScanRequestListener {

    private RecyclerView recyclerViewSubtasks;
    private SubtaskAdapter subtaskAdapter;
    private List<Quest> subtasks;
    private QuestDAO questDAO;
    private int studentID;
    private String questMainName;
    private int mainQuest;
    private StudentQuestRepo studentQuestRepo;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private SubtaskInstructionsDialog currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);
        questDAO = new QuestDAO(Client.getClient().create(ApiQuestService.class));
        studentQuestRepo = new StudentQuestRepo(Client.getClient().create(ApiStudentQuestService.class));

        questMainName = getIntent().getStringExtra("main_quest_name");

        recyclerViewSubtasks = findViewById(R.id.recycler_view_subtasks);
        recyclerViewSubtasks.setLayoutManager(new LinearLayoutManager(this));
        mainQuest = getIntent().getIntExtra("main_quest", 0);
        studentID = getIntent().getIntExtra("student_id", 0);

        setUpSubtasks(mainQuest, this);
        setupNfc();
    }

    private void setupNfc() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Thiết bị này không hỗ trợ NFC", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC đang tắt. Vui lòng bật NFC", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_MUTABLE;
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Lỗi khi thêm MIME type", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef,};
        techListsArray = new String[][]{new String[]{Ndef.class.getName()}};
    }

    void setUpSubtasks(int mainQuest, SubtaskAdapter.OnSubtaskClickListener listener) {
        questDAO.getSubQuest(mainQuest, new Callback<List<Quest>>() {
            @Override
            public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
                if (response.isSuccessful()) {
                    subtasks = response.body();
                    subtaskAdapter = new SubtaskAdapter(subtasks, questDAO, studentID);
                    recyclerViewSubtasks.setAdapter(subtaskAdapter);
                    subtaskAdapter.setOnSubtaskClickListener(listener);
                    subtaskAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SubtaskActivity.this, "Failed to load subtasks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Quest>> call, Throwable t) {
                Toast.makeText(SubtaskActivity.this, "Failed to load subtasks", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSubtaskClick(Quest subtask) {
        showSubtaskInstructionsDialog(subtask);
    }

    private void showSubtaskInstructionsDialog(Quest subtask) {
        currentDialog = new SubtaskInstructionsDialog(this, subtask);
        currentDialog.setOnNfcScanRequestListener(this);
        currentDialog.show();
    }

    @Override
    public void onNfcScanRequested() {
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Đặt điện thoại gần thẻ NFC", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NFC không được bật. Vui lòng bật NFC và thử lại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNfcTag(Tag tag) {
        // Xử lý thẻ NFC ở đây
        String tagId = bytesToHexString(tag.getId());
        Quest scannedSubtask = findSubtaskById(tagId);
        if (scannedSubtask != null) {
            if (currentDialog != null && currentDialog.isShowing()) {
                currentDialog.dismiss();
            }
            checkSubtasks(scannedSubtask);
        } else {
            Toast.makeText(this, "Thẻ NFC không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }

    private Quest findSubtaskById(String id) {
        for (Quest subtask : subtasks) {
            if (id.equals(String.valueOf(subtask.getId()))) {
                return subtask;
            }
        }
        return null;
    }

    private void checkSubtasks(Quest subQuest) {
        studentQuestRepo.saveProgress(studentID, subQuest.getId(), 100);
        questDAO.checkIfMainQuestIfCompleted(studentID, mainQuest, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        startQuiz();
                    } else {
                        subtaskAdapter.notifyDataSetChanged();
                    }
                } else {
                    Snackbar.make(recyclerViewSubtasks, "Failed to check subtasks", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(recyclerViewSubtasks, "Failed to check subtasks", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void startQuiz() {
        Intent quizIntent = new Intent(this, QuizActivity.class);
        quizIntent.putExtra("questID", mainQuest);
        startActivityForResult(quizIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int quizScore = data.getIntExtra("quiz_score", 0);
            int totalPoints = calculatePoints() + quizScore;
            showCompletionDialog(totalPoints);
        }
    }

    private int calculatePoints() {
        return subtasks.size() * 10;
    }

    private void showCompletionDialog(int points) {
        QuestCompletionDialog dialog = QuestCompletionDialog.newInstance(questMainName, points);
        dialog.setOnQuestCompletionListener(this);
        dialog.show(getSupportFragmentManager(), "quest_completion_dialog");
    }

    @Override
    public void onQuestCompleted(int points) {
        updateUserScore(points);
        finish();
    }

    private void updateUserScore(int points) {
        studentQuestRepo.saveProgress(studentID, mainQuest, points);
        Toast.makeText(this, "Score updated: +" + points + " points", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                handleNfcTag(tag);
            }
        }
    }
}