package com.example.notionhelper.model.items.commands;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.notionhelper.R;
import com.example.notionhelper.common.ItemTypes;
import com.example.notionhelper.model.items.Item;
import com.example.notionhelper.utilities.JsonBodyHelper;
import com.example.notionhelper.view.fragments.ItemFragment;
import com.google.gson.JsonObject;

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
        InputMethodManager inputManager = (InputMethodManager) fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(fragment.requireView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        ArrayList<String> inputs = fragment.getInputs();
        Call<JsonObject> response = this.notionInterface.createPage(JsonBodyHelper.createPageBody(inputs));
        EditText textInput = fragment.requireView().findViewById(R.id.edittext_taskname);
        textInput.setHint("Previous: " + inputs.get(0));
        textInput.setHintTextColor(fragment.getResources().getColor(R.color.grey, fragment.requireContext().getTheme()));

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
