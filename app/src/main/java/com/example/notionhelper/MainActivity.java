package com.example.notionhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.notionhelper.databinding.ActivityMainBinding;
import com.example.notionhelper.view.ListAdapter;
import com.example.notionhelper.model.ItemFactory;
import com.example.notionhelper.model.Item;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Item> itemArrayList = ItemFactory.getAllCommands(); // Commands is default
        ListAdapter listAdapter = new ListAdapter(MainActivity.this, itemArrayList);

        createToolbarUI();
        createItemListUI(itemArrayList, listAdapter);
        createTabUI(itemArrayList, listAdapter);
    }

    private void createToolbarUI() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void createTabUI(ArrayList<Item> itemArrayList, ListAdapter listAdapter) {
        tabLayout = findViewById(R.id.include);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ItemFactory.getAllCommands(itemArrayList);
                } else if (tab.getPosition() == 1) {
                    ItemFactory.getAllScripts(itemArrayList);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override public void onTabUnselected(Tab tab) {}
            @Override public void onTabReselected(Tab tab) {}
        });
    }

    private void createItemListUI(ArrayList<Item> itemArrayList, ListAdapter listAdapter) {
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ItemActivity.class);
            intent.putExtra("title", itemArrayList.get(position).getTitle());
            intent.putExtra("description", itemArrayList.get(position).getDescription());
            intent.putExtra("type", itemArrayList.get(position).getItemType());
            intent.putExtra("id", itemArrayList.get(position).getItemId());
            startActivity(intent);
        });
    }
}