package com.example.notionhelper.model.items;

import android.widget.ImageView;

import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.Item;

import java.util.ArrayList;

public class UpdateDailyTasksItem extends Item {
    public UpdateDailyTasksItem() {
        super(
                "Update Daily Tasks *TO-DO*",
                "Will remove any modifications made to your daily tasks and reset the date to today",
                ItemTypes.SCRIPT.name(),
                "updateDailyTasks"
        );
    }

    @Override
    public void runItem(ArrayList<String> inputs, ImageView responseGif) {}
}
