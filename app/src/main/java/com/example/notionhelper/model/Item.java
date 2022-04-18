package com.example.notionhelper.model;

import android.widget.ImageView;

import com.example.notionhelper.R;

import java.util.ArrayList;

abstract public class Item {
    private final String title;
    private final String description;
    private final String itemType;
    private final String itemId;

    public Item(String title, String description, String type, String itemId) {
        this.title = title;
        this.description = description;
        this.itemType = type;
        this.itemId = itemId;
    }

    abstract public void runItem(ArrayList<String> inputs, ImageView responseGif);

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemId() {
        return itemId;
    }

    protected void updateResponseGif(String response, ImageView responseGif) {
        if (response.equals("200")) {
            responseGif.setImageResource(R.drawable.success);
        } else {
            responseGif.setImageResource(R.drawable.failure);
        }
    }
}
