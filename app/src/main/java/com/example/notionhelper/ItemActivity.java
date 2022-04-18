package com.example.notionhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.notionhelper.databinding.ActivityItemBinding;
import com.example.notionhelper.model.ItemFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ItemActivity extends AppCompatActivity {

    ActivityItemBinding binding;
    Button runButton, todayButton, tomorrowButton;
    EditText taskName;
    ImageView responseGif;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        bindUIElements();
        createMetadataUI(intent);
        createDateButtonUI();
        createRunButtonUI(intent);
    }

    private void bindUIElements() {
        todayButton = findViewById(R.id.date_today);
        tomorrowButton = findViewById(R.id.date_tomorrow);
        runButton = findViewById(R.id.run);
        taskName = findViewById(R.id.edittext_taskname);
        responseGif = findViewById(R.id.response_gif);
    }

    private void createMetadataUI(Intent intent) {
        if (intent != null) {
            binding.titleBody.setText(intent.getStringExtra("title"));
            binding.descriptionBody.setText(intent.getStringExtra("description"));
            type = intent.getStringExtra("type"); // Implement variable layout based on this
        }
    }

    private void createRunButtonUI(Intent intent) {
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add(taskName.getText().toString());
                inputs.add(buildDate());

                Glide.with(ItemActivity.this)
                        .load(R.drawable.loading)
                        .into(responseGif);

                ItemFactory.getItem(intent.getStringExtra("id"))
                        .runItem(inputs, responseGif);
            }
        });
    }

    private void createDateButtonUI() {
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayButton.setBackgroundColor(getResources().getColor(R.color.blue));
                todayButton.setTextColor(getResources().getColor(R.color.white));

                tomorrowButton.setBackgroundColor(getResources().getColor(R.color.white));
                tomorrowButton.setTextColor(getResources().getColor(R.color.blue));
            }
        });

        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomorrowButton.setBackgroundColor(getResources().getColor(R.color.blue));
                tomorrowButton.setTextColor(getResources().getColor(R.color.white));

                todayButton.setBackgroundColor(getResources().getColor(R.color.white));
                todayButton.setTextColor(getResources().getColor(R.color.blue));
            }
        });
    }

    // Probably should implement this in a less duct-taped way
    private boolean isSelected(Button button) {
        return button.getCurrentTextColor() == getResources().getColor(R.color.white);
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

}
