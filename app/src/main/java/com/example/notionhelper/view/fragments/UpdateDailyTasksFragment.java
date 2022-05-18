package com.example.notionhelper.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notionhelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpdateDailyTasksFragment extends ItemFragment {

    Button todayButton, tomorrowButton;
    TextView statusText;

    public UpdateDailyTasksFragment() {
        super(R.layout.updatedailytasks_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        statusText = view.findViewById(R.id.script_status);
        todayButton = view.findViewById(R.id.date_today);
        tomorrowButton = view.findViewById(R.id.date_tomorrow);

        createDateButtonUI();
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> inputs = new ArrayList<>();
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

    private boolean isSelected(Button button) {
        return button.getCurrentTextColor() == getResources().getColor(R.color.white);
    }

}
