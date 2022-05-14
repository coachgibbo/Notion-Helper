package com.example.notionhelper.model;

import com.example.notionhelper.model.items.AddDailyTaskItem;
import com.example.notionhelper.model.items.GetNextTaskItem;
import com.example.notionhelper.model.items.UpdateDailyTasksItem;

import java.util.ArrayList;

public class ItemFactory {

    public static Item getItem(String itemId) {
        switch(itemId) {
            case "addDailyTask":
                return new AddDailyTaskItem();

            case "updateDailyTasks":
                return new UpdateDailyTasksItem();

            case "getNextTask":
                return new GetNextTaskItem();
        }

        return new AddDailyTaskItem();
    }

    public static ArrayList<Item> getAllCommands(ArrayList<Item> itemArrayList) {
        itemArrayList.clear();
        itemArrayList.add(new AddDailyTaskItem());
        itemArrayList.add(new GetNextTaskItem());

        return itemArrayList;
    }

    public static ArrayList<Item> getAllScripts(ArrayList<Item> itemArrayList) {
        itemArrayList.clear();
        itemArrayList.add(new UpdateDailyTasksItem());

        return itemArrayList;
    }

    public static ArrayList<Item> getAllCommands() {
        ArrayList<Item> itemArrayList = new ArrayList<>();

        return getAllCommands(itemArrayList);
    }

    public static ArrayList<Item> getAllScripts() {
        ArrayList<Item> itemArrayList = new ArrayList<>();

        return getAllScripts(itemArrayList);
    }
}
