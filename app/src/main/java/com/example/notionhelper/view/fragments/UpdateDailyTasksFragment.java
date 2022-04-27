package com.example.notionhelper.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.notionhelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpdateDailyTasksFragment extends ItemFragment {

    TextView statusText;

    public UpdateDailyTasksFragment() {
        super(R.layout.script_fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        statusText = view.findViewById(R.id.script_status);
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(buildDate());

        return inputs;
    }

    // Cannot use java.time for this as it requires Android API 26> (Currently 21>)
    private String buildDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return formatter.format(Calendar.getInstance().getTime());
    }
}
