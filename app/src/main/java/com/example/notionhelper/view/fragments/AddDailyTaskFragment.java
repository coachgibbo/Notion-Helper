package com.example.notionhelper.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notionhelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddDailyTaskFragment extends ItemFragment {

    private Button todayButton, tomorrowButton;
    private EditText taskName;

    public AddDailyTaskFragment() {
        super(R.layout.adddailytask_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        todayButton = view.findViewById(R.id.date_today);
        tomorrowButton = view.findViewById(R.id.date_tomorrow);
        taskName = view.findViewById(R.id.edittext_taskname);
        taskName.requestFocus();

        createDateButtonUI();
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(taskName.getText().toString());
        inputs.add(buildDate());

        return inputs;
    }

    private void createDateButtonUI() {
        todayButton.setOnClickListener(view -> {
            todayButton.setBackgroundColor(getResources().getColor(R.color.blue));
            todayButton.setTextColor(getResources().getColor(R.color.white));

            tomorrowButton.setBackgroundColor(getResources().getColor(R.color.white));
            tomorrowButton.setTextColor(getResources().getColor(R.color.blue));
        });

        tomorrowButton.setOnClickListener(view -> {
            tomorrowButton.setBackgroundColor(getResources().getColor(R.color.blue));
            tomorrowButton.setTextColor(getResources().getColor(R.color.white));

            todayButton.setBackgroundColor(getResources().getColor(R.color.white));
            todayButton.setTextColor(getResources().getColor(R.color.blue));
        });
    }

    // Cannot use java.time for this as it requires Android API 26> (Currently 21>)
    private String buildDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();

        if (isSelected(tomorrowButton)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return formatter.format(calendar.getTime());
    }

    // Probably should implement this in a less duct-taped way
    private boolean isSelected(Button button) {
        return button.getCurrentTextColor() == getResources().getColor(R.color.white);
    }
}
