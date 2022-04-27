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
    public void runItem(ItemFragment fragment, ImageView responseGif) {
        Log.i("UpdateDailyTasks", "Running Item");

        ArrayList<Stage> stages = buildStages(fragment);

        StageRunner.run(stages, fragment, responseGif);
    }

    private ArrayList<Stage> buildStages(ItemFragment fragment) {
        ArrayList<String> inputs = fragment.getInputs();

        return new ArrayList<>(Arrays.asList(
                new Stage.Builder().name("Update Task 1 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE).build(),
                new Stage.Builder().name("Update Task 2 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_2).build(),
                new Stage.Builder().name("Update Task 3 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_3).build(),
                new Stage.Builder().name("Update Task 4 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_4).build(),
                new Stage.Builder().name("Update Task 5 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_5).build(),
                new Stage.Builder().name("Update Task 6 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_6).build(),
                new Stage.Builder().name("Update Task 7 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_7).build(),
                new Stage.Builder().name("Update Task 8 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_8).build(),
                new Stage.Builder().name("Update Task 9 Properties").jsonBody(createBody(inputs)).pageId(DAILY_TASK_TEMPLATE_9).build()
                ));
    }

    private JsonObject createBody(ArrayList<String> inputs) {
        return new JsonParser().parse("{    \n" +
                "    \"properties\": {\n" +
                "        \"Date\": {\n" +
                "            \"date\": {\n" +
                "                \"start\": \"" + inputs.get(0) + "\",\n" +
                "                \"end\": null,\n" +
                "                \"time_zone\": null\n" +
                "            }\n" +
                "        },\n" +
                "        \"Status\": {\n" +
                "            \"type\": \"select\",\n" +
                "            \"select\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Not started\",\n" +
                "                \"color\": \"red\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}").getAsJsonObject();
    }
}
