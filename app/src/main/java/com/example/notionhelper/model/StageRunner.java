package com.example.notionhelper.model;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.notionhelper.R;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StageRunner {
    public static void run(ArrayList<Stage> stages, ItemFragment fragment, ImageView responseGif) {
        TextView statusText = fragment.getView().findViewById(R.id.script_status);
        ProgressBar progressBar = fragment.getView().findViewById(R.id.script_progressbar);
        int numberOfStages = stages.size();

        statusText.setText("Stage: " + 0 + " of " + numberOfStages);
        progressBar.setMax(numberOfStages);
        progressBar.setProgress(0);
        progressBar.setProgressTintList(
                ColorStateList.valueOf(fragment.getResources().getColor(R.color.yellow))
        );

        new Handler().post(() -> {
            final int[] completedStages = {0};

            for (int i = 0; i < stages.size(); i++) {
                Stage currentStage = stages.get(i);
                Call<JsonObject> response = currentStage.updatePage();

                response.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        Log.i("STAGE", "Stage: " + currentStage.getName() + " completed");
                        completedStages[0]++;
                        statusText.setText("Stage: " + completedStages[0] + " of " + numberOfStages + "\n" + currentStage.getName() + " completed");
                        progressBar.setProgress(completedStages[0]);
                        if (completedStages[0] == numberOfStages) {
                            responseGif.setImageResource(R.drawable.success);
                            progressBar.setProgressTintList(
                                    ColorStateList.valueOf(fragment.getResources().getColor(R.color.green))
                            );
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Log.i("STAGE", "Stage: " + currentStage.getName() + " failed " + t.getMessage());
                    }
                });
            }
        });
    }
}
