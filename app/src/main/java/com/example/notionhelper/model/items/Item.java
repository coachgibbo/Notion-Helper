package com.example.notionhelper.model.items;

import android.widget.ImageView;

import com.example.notionhelper.R;
import com.example.notionhelper.infrastructure.notion.NotionClient;
import com.example.notionhelper.infrastructure.notion.NotionInterface;
import com.example.notionhelper.view.fragments.ItemFragment;

abstract public class Item {
    private final String title;
    private final String description;
    private final String itemType;
    private final String itemId;

    public final NotionInterface notionInterface;

    public Item(String title, String description, String type, String itemId) {
        this.title = title;
        this.description = description;
        this.itemType = type;
        this.itemId = itemId;

        notionInterface = NotionClient.getNotionInterface();
    }

    abstract public void runItem(ItemFragment fragment, ImageView responseGif);

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
