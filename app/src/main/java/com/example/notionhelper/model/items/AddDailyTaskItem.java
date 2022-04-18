package com.example.notionhelper.model.items;

import static com.example.notionhelper.common.Constants.DAILY_TASK_DATABASE;

import android.util.Log;
import android.widget.ImageView;

import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.infrastructure.NotionClient;
import com.example.notionhelper.infrastructure.config.NotionInterface;
import com.example.notionhelper.model.Item;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyTaskItem extends Item {
    private final NotionInterface notionInterface;

    public AddDailyTaskItem() {
        super(
                "Add To-Do Item",
                "Add a custom to-do item to your daily task database with date = today or tomorrow",
                ItemTypes.TASK.name(),
                "addDailyTask"
        );

        this.notionInterface = NotionClient.getNotionInterface();
    }

    @Override
    public void runItem(ArrayList<String> inputs, ImageView responseGif) {
        Log.i("AddDailyTask", "Running Item");
        addDailyTask(inputs, responseGif);
    }

    // responseGif should be replaced by a DataCallback if we need to return data
    private void addDailyTask(ArrayList<String> inputs, ImageView responseGif) {
        Call<JsonObject> response = notionInterface.createPage(createBody(inputs));

        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                updateResponseGif(String.valueOf(response.code()), responseGif);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("AddDailyTask", "Error while calling API" + t);
            }
        });
    }

    // Doing it this way since the Notion API has a complex schema for POJO's
    private JsonObject createBody(ArrayList<String> inputs) {
        return new JsonParser().parse("{\n" +
                "    \"parent\": {\n" +
                "        \"database_id\": \"" + DAILY_TASK_DATABASE + "\"\n" +
                "    },\n" +
                "    \"properties\": {\n" +
                "        \"title\": {\n" +
                "            \"title\": [\n" +
                "                {\n" +
                "                    \"text\": {\n" +
                "                        \"content\": \"" + inputs.get(0) + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"Date\": {\n" +
                "            \"date\": {\n" +
                "                \"start\": \"" + inputs.get(1) + "\",\n" +
                "                \"end\": null,\n" +
                "                \"time_zone\": null\n" +
                "            }\n" +
                "        }," +
                "        \"Status\": {\n" +
                "            \"select\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Not started\",\n" +
                "                \"color\": \"red\"\n" +
                "            }\n" +
                "        }" +
                "    }\n" +
                "}").getAsJsonObject();
    }
}
