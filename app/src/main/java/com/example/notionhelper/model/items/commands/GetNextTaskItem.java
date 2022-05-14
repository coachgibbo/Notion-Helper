package com.example.notionhelper.model.items.commands;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.notionhelper.R;
import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.items.Item;
import com.example.notionhelper.utilities.JsonBodyHelper;
import com.example.notionhelper.utilities.TaskHelper;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNextTaskItem extends Item {

    public GetNextTaskItem() {
        super(
                "Get Next Task",
                "Gets the next task by order that is not completed",
                ItemTypes.COMMAND.name(),
                "getNextTask"
        );
    }

    @Override
    public void runItem(ItemFragment fragment, ImageView responseGif) {
        Log.i("GetNextTask", "Running Item");

        Call<JsonObject> response = this.notionInterface.getPageFromDatabase(JsonBodyHelper.getPageFromDatabaseBody());

        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                TextView responseText = fragment.requireView().findViewById(R.id.textview_fragment);
                responseText.setText(TaskHelper.extractTaskName(response));

                updateResponseGif(String.valueOf(response.code()), responseGif);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("GetNextTask", "Error while calling API " + t);
            }
        });
    }

}
