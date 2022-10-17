package com.example.notionhelper.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notionhelper.R;

import org.javatuples.Triplet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddDailyTaskFragment extends ItemFragment {

    private Button todayButton, tomorrowButton, nsButton, compButton;
    private EditText taskName;

    public AddDailyTaskFragment() {
        super(R.layout.adddailytask_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        todayButton = view.findViewById(R.id.date_today);
        tomorrowButton = view.findViewById(R.id.date_tomorrow);

        nsButton = view.findViewById(R.id.status_ns);
        compButton = view.findViewById(R.id.status_comp);

        taskName = view.findViewById(R.id.edittext_taskname);
        taskName.requestFocus();

        createButtonUI(todayButton, tomorrowButton);
        createButtonUI(nsButton, compButton);
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(taskName.getText().toString());
        inputs.add(buildDate());

        Triplet<String, String, String> taskStatus = buildStatus();
        inputs.add(taskStatus.getValue0());
        inputs.add(taskStatus.getValue1());
        inputs.add(taskStatus.getValue2());

        taskName.setText("");

        return inputs;
    }

    private void createButtonUI(Button button1, Button button2) {
        button1.setOnClickListener(view -> {
            button1.setBackgroundColor(getResources().getColor(R.color.blue));
            button1.setTextColor(getResources().getColor(R.color.white));

            button2.setBackgroundColor(getResources().getColor(R.color.white));
            button2.setTextColor(getResources().getColor(R.color.blue));
        });

        button2.setOnClickListener(view -> {
            button2.setBackgroundColor(getResources().getColor(R.color.blue));
            button2.setTextColor(getResources().getColor(R.color.white));

            button1.setBackgroundColor(getResources().getColor(R.color.white));
            button1.setTextColor(getResources().getColor(R.color.blue));
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

    private Triplet<String, String, String> buildStatus() {
        Triplet<String, String, String> resultTuple;

        if (isSelected(nsButton)) {
            resultTuple = Triplet.with("1", "Not started", "red");
        } else {
            resultTuple = Triplet.with("3", "Completed", "green");
        }
        return resultTuple;
    }

    private boolean isSelected(Button button) {
        return button.getCurrentTextColor() == getResources().getColor(R.color.white);
    }

}
