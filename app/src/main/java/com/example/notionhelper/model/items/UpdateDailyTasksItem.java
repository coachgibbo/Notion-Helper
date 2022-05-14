package com.example.notionhelper.model.items;

import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_2;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_3;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_4;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_5;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_6;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_7;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_8;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_TEMPLATE_9;

import android.util.Log;
import android.widget.ImageView;

import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.Item;
import com.example.notionhelper.model.Stage;
import com.example.notionhelper.model.StageRunner;
import com.example.notionhelper.utilities.JsonBodyHelper;
import com.example.notionhelper.view.fragments.ItemFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateDailyTasksItem extends Item {

    public UpdateDailyTasksItem() {
        super(
                "Update Daily Tasks",
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
                buildStage(inputs, "Update Task 1 Properties", DAILY_TASK_TEMPLATE),
                buildStage(inputs, "Update Task 2 Properties", DAILY_TASK_TEMPLATE_2),
                buildStage(inputs, "Update Task 3 Properties", DAILY_TASK_TEMPLATE_3),
                buildStage(inputs, "Update Task 4 Properties", DAILY_TASK_TEMPLATE_4),
                buildStage(inputs, "Update Task 5 Properties", DAILY_TASK_TEMPLATE_5),
                buildStage(inputs, "Update Task 6 Properties", DAILY_TASK_TEMPLATE_6),
                buildStage(inputs, "Update Task 7 Properties", DAILY_TASK_TEMPLATE_7),
                buildStage(inputs, "Update Task 8 Properties", DAILY_TASK_TEMPLATE_8),
                buildStage(inputs, "Update Task 9 Properties", DAILY_TASK_TEMPLATE_9))
        );
    }

    private Stage buildStage(ArrayList<String> inputs, String name, String id) {
        return new Stage.Builder()
                .name(name)
                .pageId(id)
                .method("updatePage")
                .jsonBody(JsonBodyHelper.updatePageBody(inputs))
                .build();
    }

}
