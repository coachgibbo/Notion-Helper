package com.example.notionhelper.model.stages;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.notionhelper.R;
import com.example.notionhelper.common.NotionPropKeys;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StageRunner {

    TextView statusText;
    ProgressBar progressBar;
    ItemFragment fragment;
    Integer numberOfStages;

    public void run(ArrayList<Stage> stages, ItemFragment fragment, ImageView responseGif) {
        // Initialize UI elements
        this.fragment = fragment;
        this.statusText = fragment.requireView().findViewById(R.id.script_status);
        this.progressBar = fragment.requireView().findViewById(R.id.script_progressbar);
        this.numberOfStages = stages.size();
        initializeUI();

        // Run Stages
        new Handler().post(() -> {
            final int[] completedStages = {0};
            final int[] failedStages = {0};

            for (int i = 0; i < stages.size(); i++) {
                Stage currentStage = stages.get(i);
                Call<JsonObject> response = currentStage.run();

                if (response == null) {
                    failedStages[0]++;
                    continue;
                }

                response.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        Log.i("STAGE", "Stage: " + currentStage.getProperty(NotionPropKeys.NAME.name()) + " completed");
                        if (response.code() != 200) {
                            failedStages[0]++;
                        } else {
                            completedStages[0]++;
                        }
                        updateUI(completedStages, failedStages, currentStage, responseGif);
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Log.i("STAGE", "Stage: " + currentStage.getProperty(NotionPropKeys.NAME.name()) + " failed " + t.getMessage());
                        failedStages[0]++;
                        updateUI(completedStages, failedStages, currentStage, responseGif);
                    }
                });
            }
        });
    }

    private void initializeUI() {
        statusText.setText("Stage: " + 0 + " of " + numberOfStages);
        progressBar.setMax(numberOfStages);
        progressBar.setProgress(0);
        progressBar.setProgressTintList(
                ColorStateList.valueOf(fragment.getResources().getColor(R.color.yellow))
        );
    }

    private void updateUI(int[] completedStages, int[] failedStages, Stage currentStage, ImageView responseGif) {
        statusText.setText("Stage: " + completedStages[0] + " of " + numberOfStages + "\n" +
                currentStage.getProperty(NotionPropKeys.NAME.name()) + " completed");
        progressBar.setProgress(completedStages[0]);

        if (completedStages[0] + failedStages[0] != numberOfStages) {
            return;
        }

        if (completedStages[0] == numberOfStages) {
            responseGif.setImageResource(R.drawable.success);
            progressBar.setProgressTintList(
                    ColorStateList.valueOf(fragment.getResources().getColor(R.color.green))
            );
        } else if (failedStages[0] == numberOfStages - completedStages[0]) {
            responseGif.setImageResource(R.drawable.failure);
            progressBar.setProgressTintList(
                    ColorStateList.valueOf(fragment.getResources().getColor(R.color.red))
            );
        }
    }

}
