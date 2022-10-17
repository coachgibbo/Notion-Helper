package com.example.notionhelper.model.items.commands;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.items.Item;
import com.example.notionhelper.utilities.JsonBodyHelper;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;

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
        InputMethodManager inputManager = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(fragment.getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        Call<JsonObject> response = this.notionInterface.createPage(JsonBodyHelper.createPageBody(fragment.getInputs()));

        response.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                updateResponseGif(String.valueOf(response.code()), responseGif);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("AddDailyTask", "Error while calling API " + t);
            }
        });
    }

}
