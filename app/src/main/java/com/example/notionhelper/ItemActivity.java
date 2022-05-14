package com.example.notionhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.notionhelper.databinding.ActivityItemBinding;
import com.example.notionhelper.model.ItemFactory;
import com.example.notionhelper.view.fragments.AddDailyTaskFragment;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.example.notionhelper.view.fragments.UpdateDailyTasksFragment;

public class ItemActivity extends AppCompatActivity {

    ActivityItemBinding binding;
    ItemFragment fragment;
    Button runButton;
    ImageView responseGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        createMetadataUI(intent);
        createFragment(intent);
        createRunButtonUI(intent);
    }

    private void createMetadataUI(Intent intent) {
        if (intent != null) {
            binding.titleBody.setText(intent.getStringExtra("title"));
            binding.descriptionBody.setText(intent.getStringExtra("description"));
        }
    }

    private void createFragment(Intent intent) {
        String id = intent.getStringExtra("id");

        if (id.equals("addDailyTask")) {
            fragment = new AddDailyTaskFragment();
        } else if (id.equals("updateDailyTasks")) {
            fragment = new UpdateDailyTasksFragment();
        } else {
            fragment = new ItemFragment(R.layout.textview_fragment);
        }

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.item_fragment, fragment, null)
                .commit();
    }

    private void createRunButtonUI(Intent intent) {
        runButton = findViewById(R.id.run);
        responseGif = findViewById(R.id.response_gif);

        runButton.setOnClickListener(view -> {
            Glide.with(ItemActivity.this)
                    .load(R.drawable.loading)
                    .into(responseGif);

            ItemFactory.getItem(intent.getStringExtra("id"))
                    .runItem(fragment, responseGif);
        });
    }

}
