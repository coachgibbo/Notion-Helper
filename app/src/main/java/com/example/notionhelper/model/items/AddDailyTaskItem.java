package com.example.notionhelper.model.items;

import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_DATABASE;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.Item;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyTaskItem extends Item {

    public AddDailyTaskItem() {
        super(
                "Add Daily Task",
                "Add a custom to-do item to your daily task database with date set as today or tomorrow",
                ItemTypes.COMMAND.name(),
                "addDailyTask"
        );
    }

    @Override
    public void runItem(ItemFragment fragment, ImageView responseGif) {
        Log.i("AddDailyTask", "Running Item");

        Call<JsonObject> response = this.notionInterface.createPage(createBody(fragment.getInputs()));

        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                updateResponseGif(String.valueOf(response.code()), responseGif);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
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
